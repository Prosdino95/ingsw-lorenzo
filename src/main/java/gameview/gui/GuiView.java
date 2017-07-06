package gameview.gui;


import java.util.ArrayList;
import java.util.List;

import gamemodel.GameQuestion;
import gamemodel.Board;
import gamemodel.LeaderCard;
import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Question;
import gamemodel.Point;
import gamemodel.Requirement;
import gamemodel.Resource;
import gamemodel.Team;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.PointModify;
import gameview.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import reti.ClientRequest;
import reti.ServerResponse;

public class GuiView extends Application {
	private Stage stage;
	private ViewController viewController;
	private Model model;
	private GUIState currentState=GUIState.IDLE;
	private int currentSceneIndex = 1;
	private List<Scene> scenes = new  ArrayList<Scene>(3);
	private ClientRequest request=null;

	private Pane rootPane;
	private Scene boardScene;
	private BoardController boardController;
	
	private RequestController requestController;
	private PlayerBoardController pbc;
	private PlayerBoardController2 pbc2;
	private boolean pressed= false;
	private Player player;
	private Timeline task;

	
	public static void setAll(double x,double y,double w,double h,Region r,double ww,double wh){
		r.setLayoutX(x*ww);
		r.setLayoutY(y*wh);
		r.setPrefSize(w*ww, h*wh);
	}
	
	public void eventHandler() {
		if (viewController == null) {
			//System.out.println("GUIView -- Still no internet connection :("); 
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
				currentState=GUIState.QUESTION;
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
				currentState=GUIState.ACTION;
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
		this.setStage(stage);
		this.viewController = new ViewController();
		

		task = new Timeline();
		task.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1), e->eventHandler()));
		task.setCycleCount(Timeline.INDEFINITE);
		task.play();

		FXMLLoader loader;
		
		loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/Board2.fxml"));		
		rootPane = loader.load();
		boardController = loader.getController();
		rootPane.setOnMouseClicked(e -> {
			System.out.println("GUIView -- " + boardScene.getWidth());
			//updateGui();
		});
		rootPane.setStyle("-fx-background-color: #228b22");
		Scene bs = new Scene(rootPane);
		boardScene = bs;		
		addScene(bs);
		
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Request.fxml"));
		Pane requestPane;
		requestPane = loader.load();
		requestPane.setBackground(new Background(new BackgroundImage(new Image("/wood.jpg"), null, null, null, null)));
		requestController = loader.getController();
		bs = new Scene(requestPane);
		addScene(bs);		
		
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/PlayerBoard.fxml"));
		Pane requestPane2;
		requestPane2 = loader.load();
		requestPane2.setBackground(new Background(new BackgroundImage(new Image("/wood.jpg"), null, null, null, null)));
		pbc = loader.getController();
		bs = new Scene(requestPane2);
		addScene(bs);	
		
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/PlayerBoard2.fxml"));
		Pane requestPane3;
		requestPane3 = loader.load();
		requestPane3.setBackground(new Background(new BackgroundImage(new Image("/wood.jpg"), null, null, null, null)));
		pbc2 = loader.getController();
		bs = new Scene(requestPane3);
		addScene(bs);	
	
		
		for(Scene s:scenes){
			s.setOnKeyPressed(e -> {
				System.out.println("GUIView -- You pressed key " + e.getCode());
				if (getPressed()) return;
				setPressed(true);
				System.out.println("GUIView -- You pressed key " + e.getCode());
				switch (e.getCode()) {
				case A:
					sceneGoLeft();
					break;
				case D:
					sceneGoRight();
					break;
				default:
//					System.out.println("Don't know what to do with " + e.getCode());
				}
				
			});
			s.setOnKeyReleased(e -> {
				setPressed(false);
			});
		}
		
	/*	Model m = new Model(4);
		m.setupRound();
		Board b = m.getBoard();*/
		
		boardController.initialize(this, requestController);
		requestController.initialize(this);
		pbc.initialize(requestController, this);
		pbc2.initialize(null);

	/*	
		this.model = m;
		this.player = m.getPlayer(Team.RED);
		player.giveCard(b.ventureCards.get(0));
		Requirement req = new Requirement(new Resource(0, 0, 1, 0));
		List<IstantEffect> ie = new ArrayList<>();
		ie.add(new PointModify(new Point(0, 1, 0)));
		LeaderCard girolamo = new LeaderCard(0, "Girolamo Savonarola", req, ie);
		player.giveLeaderCard(girolamo);
		girolamo = new LeaderCard(0, "Girolamo Savonarola", req, ie);
		player.giveLeaderCard(girolamo);
		girolamo = new LeaderCard(0, "Girolamo Savonarola", req, ie);
		player.giveLeaderCard(girolamo);
		girolamo = new LeaderCard(0, "Girolamo Savonarola", req, ie);
		player.giveLeaderCard(girolamo);
*/
		
		
		stage.setTitle("Il magnifico");
		stage.show();
		updateGui();
	}
	
	private boolean getPressed() {
		return pressed;
	}

	private synchronized void setPressed(boolean b) {
		pressed = b;
	}

	private Scene getCurrentScene() {
		return scenes.get(currentSceneIndex);
	}

	private Scene getScene(int index) {
		if (index >= 0 && index < scenes.size())
			return scenes.get(index);
		else
			return null;
	}

	private void addScene(Scene scene) {
		scenes.add(scene);
	}

	private void sceneGoRight() {
		Scene next = getScene(currentSceneIndex + 1);
		if (next != null) {
			currentSceneIndex += 1;
		} else {
			currentSceneIndex = 0;
		}
		changeScene();
		//updateGui();
	}

	private void sceneGoLeft() {
		Scene next = getScene(currentSceneIndex - 1);
		if (next != null) {
			currentSceneIndex -= 1;
		} else {
			currentSceneIndex = scenes.size() - 1;
		}
		changeScene();
		//updateGui();
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
		pbc.update(player);
		pbc2.update(player);
		boardController.update(this.model);
		requestController.update();
		System.out.println("GUIView -- You're in scene " + this.getCurrentScene());
		System.out.println("GUIView -- Index " + this.currentSceneIndex);
		if (getStage().getScene() != getCurrentScene()) {
			// Non dovrei invertire show e hide? Lo scatto e' supervisibile
			//getStage().hide();
			getStage().setScene(getCurrentScene());
			//getStage().show();
		}
	}
	
	void changeScene(){
		if (getStage().getScene() != getCurrentScene())
			getStage().setScene(getCurrentScene());
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
}

