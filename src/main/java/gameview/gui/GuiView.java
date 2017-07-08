	package gameview.gui;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gamemodel.GameQuestion;
import gamemodel.Model;
import gamemodel.Question;
import gamemodel.player.Player;
import gamemodel.player.Team;
import gameview.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import reti.ClientRequest;
import reti.ServerResponse;

public class GuiView extends Application {
	private Stage stage;
	private ViewController viewController;
	private Model model;
	private GUIState currentState=GUIState.IDLE;
	private ClientRequest request=null;
	private int currentPaneIndex = 1;
	private List<Pane> panes = new ArrayList<Pane>();
	private Pane rootPane;
	private Pane boardPane;
	private BoardController boardController;
	private RequestController requestController;
	private PlayerBoardController pbc;
	private PlayerBoardController2 pbc2;
	private boolean pressed= false;
	private Player player;
	private Timeline task;
	private String networkChoose="socket";
	private boolean dx=false,sx=false;
	private boolean r=false,p=false;
	
	String musicFile = "src/main/resources/Medieval Music.mp3";
	Media sound = new Media(new File(musicFile).toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(sound);
	public void muteVolume()
	{
		if(mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
		{
			mediaPlayer.pause();
	
		}
			
		if(mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))
			mediaPlayer.play();
	}
	
	public static void setAll(double x,double y,double w,double h,Region r,double ww,double wh){
		r.setLayoutX(x*ww);
		r.setLayoutY(y*wh);
		r.setPrefSize(w*ww, h*wh);
	}
	
	public void eventHandler() {
		if (viewController == null) {
			return;
		}
		while(viewController.hasMessage()) {
			ServerResponse sr = viewController.getMessage();
			switch (sr.getType()) {
			case NEW_MODEL:
				newModel(sr.getModel());
				requestController.setPlayerTurn(model.getCurrentPlayer());
				updateGui();
			break;
			case PLAYER_ASSIGNED:
				if (this.model == null) {
					System.out.println("GUIView -- They sent me the player but I still did not get the model...");
					break;
				}
				Team team = sr.getPlayerTeam();
				System.out.println("GUIView -- Your player got assigned, you're team: " + team);
				this.setPlayer(team);
				requestController.generateFM();
				System.out.println("GUIView -- Now get out of this log and play!");
			break;
			case MESSAGE:
				requestController.giveSR(sr);
			break;	
			case LEADER:
				currentState=GUIState.LEADER;
				requestController.giveSR(sr);
				break;
			default:
				System.out.println("GUIView -- Should this message get here? " + sr);
				break;
			}
		}
		if(request!=null){
			ServerResponse sr=viewController.syncSend(request);
			switch (sr.getType()){
			case QUESTION:
				currentState=GUIState.QUESTION;
				requestController.giveSR(sr);
			break;	
			case ERROR:
			case OK:
				requestController.giveSR(sr);
				if(currentState==GUIState.LEADER || currentState==GUIState.VATICAN) 
					currentState=GUIState.IDLE;
				else currentState=GUIState.ACTION;
			break;	
			default:
				System.out.println("GUIView -- Should this message get here? " + sr);
				break;
			}
			request=null;
		}
	}

	private void setPlayer(Team team) {
		this.player = model.getPlayer(team);
	}
	
	Player getPlayer() {
		return this.player;
	}
	
	private void newModel(Model model){
		System.out.print("GUIView -- A new model has arrived... It says:");
		this.model = model;
		if(player!=null)
			this.player = model.getPlayer(player.getTeam());
		switch(model.getState()){
		case GAME_FINISH:
			assert(false);
			break;
		case PLAYER_PLAING:
			currentState=GUIState.ACTION;
			break;
		case SET_UP_ROUND:
			System.out.println("GUIView -- Setting up round for turn: " + model.turn);
			break;
		case VATICAN_TIME:
			currentState=GUIState.VATICAN;
			requestController.giveSR(new ServerResponse(new Question(GameQuestion.VATICAN_SUPPORT,Question.yesOrNo())));
			break;
		default:
			break;
	}
}
	@Override
	public void stop(){
	    System.out.println("Stage is closing");
	    task.stop();
	    viewController.shutdown();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		
	    String file="src/main/resources/Credits.mp4";
		MediaPlayer credit = new MediaPlayer(new Media(new File(file).toURI().toString()));
		MediaView video =new MediaView(credit);
		credit.setMute(true);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		this.setStage(stage);
		this.viewController = new ViewController(networkChoose);
		
		task = new Timeline();
		task.getKeyFrames().add(
		new KeyFrame(Duration.seconds(1), e->eventHandler()));
		task.setCycleCount(Timeline.INDEFINITE);
		task.play();
		
		rootPane = new Pane();

		FXMLLoader loader;
		
		loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/Board2.fxml"));		
		boardPane = loader.load();
		boardController = loader.getController();
		boardPane.setBackground(new Background(new BackgroundImage(new Image("/greenBackground.jpg"), null, null, null, null)));
		rootPane.getChildren().add(boardPane);
		panes.add(boardPane);
		
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Request.fxml"));
		Pane requestPane;
		requestPane = loader.load();
		requestPane.setBackground(new Background(new BackgroundImage(new Image("/wood.jpg"), null, null, null, null)));
		requestController = loader.getController();
		rootPane.getChildren().add(requestPane);
		panes.add(requestPane);

		
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/PlayerBoard.fxml"));
		Pane playerBoardPane;
		playerBoardPane = loader.load();
		playerBoardPane.setBackground(new Background(new BackgroundImage(new Image("/greyBackground.jpg"), null, null, null, null)));
		pbc = loader.getController();
		rootPane.getChildren().add(playerBoardPane);
		panes.add(playerBoardPane);


		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/PlayerBoard2.fxml"));
		Pane playerBoard2Pane;
		playerBoard2Pane = loader.load();
		playerBoard2Pane.setBackground(new Background(new BackgroundImage(new Image("/greyBackground.jpg"), null, null, null, null)));
		pbc2 = loader.getController();
		rootPane.getChildren().add(playerBoard2Pane);
		panes.add(playerBoard2Pane);
		
		boardController.initialize(this, requestController);
		requestController.initialize(this);
		pbc.initialize(requestController, this);
		pbc2.initialize(pbc);
		
		Scene scene=new Scene(rootPane);
		
		scene.setOnKeyPressed(e -> {
			if (getPressed()) return;
			setPressed(true);
			switch (e.getCode()) {
			case A:
				paneGoLeft();
				break;
			case D:
				paneGoRight();
				break;
			case V: muteVolume(); 
				break;
			case RIGHT:
			case LEFT:	
			case R:
			case P:	
				credits(e,credit,video);
				break;
			default:
				System.out.println("ciao");
				mediaPlayer.setMute(false);
				paneGoLeft();
				credit.stop();
				stage.setScene(scene);
				break;
			}
			
		});
		scene.setOnKeyReleased(e -> {
			setPressed(false);
		});
	
		stage.setScene(scene);
		stage.setTitle("Lorenzo il magnifico");
		stage.show();
		updateGui();
		
	}
	
	private void credits(KeyEvent e, MediaPlayer c, MediaView v) {
		if(r && p && dx && sx){
			mediaPlayer.setMute(true);
			Pane pa=new Pane(v);
			Scene scene=new Scene(pa);
			Scene original=stage.getScene();
			scene.setOnKeyPressed(ev-> stopCredit(c,original));
			stage.setScene(scene);
			c.play();
			c.setMute(false);
			r=p=dx=sx=false;return;
		}			
		switch(e.getCode()){
		case RIGHT:dx=true;break;
		case LEFT:sx=true;break;
		case R:r=true;break;
		case P:p=true;break;
		default:break;
		}
	}
	
	private void stopCredit(MediaPlayer c, Scene scene){
			System.out.println("ciao");
			mediaPlayer.setMute(false);
			c.stop();
			stage.setScene(scene);
	}

	public void setNetworkChoose(String networkChoose) {
		this.networkChoose = networkChoose;
	}

	private boolean getPressed() {
		return pressed;
	}

	private synchronized void setPressed(boolean b) {
		pressed = b;
	}

	private Pane getCurrentPane() {
		return panes.get(currentPaneIndex);
	}

	private Pane getScene(int index) {
		if (index >= 0 && index < panes.size())
			return panes.get(index);
		else
			return null;
	}

	private void paneGoRight() {
		Pane next = getScene(currentPaneIndex + 1);
		if (next != null) {
			currentPaneIndex += 1;
		} else {
			currentPaneIndex = 0;
		}
		updateGui();
	}

	private void paneGoLeft() {
		Pane next = getScene(currentPaneIndex - 1);
		if (next != null) {
			currentPaneIndex -= 1;
		} else {
			currentPaneIndex = panes.size() - 1;
		}
		updateGui();
	}
		
	
	public ServerResponse syncSend(ClientRequest cr) {
	    ServerResponse srr = viewController.syncSend(cr);
	    return srr;
	}

/*	public void updateModelAndGui(Model m) {
		setModel(m);
		updateGui();
	}*/
	
	void updateGui() {
		pbc.update();
		pbc2.update();
		boardController.update(this.model);
		requestController.update();
		System.out.println("GUIView -- You're in scene " + this.getCurrentPane());
		System.out.println("GUIView -- Index " + this.currentPaneIndex);
		getCurrentPane().toFront();
	}
	
	public void setModel(Model m) {
		this.model = m; 
	}
	
	public static void main(String[] args) {
        Application.launch(GuiView.class, args);
    }


	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setRequest(ClientRequest request) {
		this.request = request;
	}

	public GUIState getState() {
		return currentState;
	}
	
	public Player myPlayer() {
		if (this.player == null) return null;
		return this.player;
	}
	
	public List<Player> otherPlayers() {
		List<Player> ps = this.model.getPlayers();
		Player myPlayerInTheModel = null;
		for (Player p : ps) {
			if (p.getTeam() == this.player.getTeam())
				myPlayerInTheModel = p;
		}
		ps.remove(myPlayerInTheModel);
		return ps;
	}
}

