package gameview.gui;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Team;
import gameview.ViewController;
import gameview.cli.OfflineException;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;

public class GuiView extends Application {
	private Stage stage;
	private ViewController viewController;
	private Model model;
	
	private int currentSceneIndex = 1;
	private List<Scene> scenes = new  ArrayList<Scene>(3);
	
	private Pane rootPane;
	private Scene boardScene;
	private BoardController boardController;
	
	private RequestController requestController;
	private boolean pressed= false;
	private TT te;
	private Player player;
	
	public static void setAll(double x,double y,double w,double h,Region r,double ww,double wh){
		r.setLayoutX(x*ww);
		r.setLayoutY(y*wh);
		r.setPrefSize(w*ww, h*wh);
	}
	
	public void timerFinished() {
		if (viewController == null) {
			System.out.println("GUIView -- Still no internet connection :("); 
			return;
		}
		while(viewController.hasMessage()) {
			ServerResponse sr = viewController.getMessage();
			// System.out.println(sr);
			switch (sr.getType()) {
			case NEW_MODEL:
				newModel(sr.getModel());
				break;
			case PLAYER_ASSIGNED:
				if (this.model != null) {
					System.out.println("GUIView -- They sent me the player but I still did not get the model...");
					break;
				}
				Team team = sr.getPlayerTeam();
				System.out.println("GUIView -- Your player got assigned, you're team: " + team);
				this.setPlayer(team);
				System.out.println("GUIView -- Now get out of this log and play!");
				break;
			case VATICAN_SUPPORT:
				break;
			case MESSAGE:
			case ERROR:
			case OK:
			case QUESTION:
			case LEADER:
				requestController.giveSR(sr);
				break;
			default:
				System.out.println("GUIView -- Should this message get here? " + sr);
				break;
			}
		}
		System.out.println("GUIView -- View controller message queue is empty!");
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
		switch(model.getState()){
		case GAME_FINISH:
			break;
		case PLAYER_PLAING:
			break;
		case SET_UP_ROUND:
			System.out.println("GUIView -- Setting up round for turn: " + model.turn);
			break;
		case VATICAN_TIME:
			// TODO
			break;
		default:
			break;
	}
}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.setStage(stage);
//		this.viewController = new ViewController();

		te = new TT(this);
		Timer timer;
		timer = new Timer();
		timer.schedule(te, 0, 1000);


		FXMLLoader loader;
		
		loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/Board2.fxml"));		
		rootPane = loader.load();
		boardController = loader.getController();
		rootPane.setOnMouseClicked(e -> {
			System.out.println("GUIView -- " + boardScene.getWidth());
			updateGui();
		});
		rootPane.setStyle("-fx-background-color: #228b22");
		Scene bs = new Scene(rootPane);
		boardScene = bs;		
		addScene(bs);

		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Request.fxml"));
		Pane requestPane;
		requestPane = loader.load();
		requestController = loader.getController();
		bs = new Scene(requestPane);
		addScene(bs);		
		
		Pane p = new AnchorPane();
		p.setStyle("-fx-background-color: #b22222");
		bs = new Scene(p);
		addScene(bs);
		
		p = new AnchorPane();
		p.setStyle("-fx-background-color: #cd6889");
		bs = new Scene(p);
		addScene(bs);
		
		for(Scene s:scenes){
			s.setOnKeyPressed(e -> {
				if (getPressed()) return;
				setPressed(true);
				System.out.println("GUIView -- You pressed key " + e.getCode());
				switch (e.getCode()) {
				case LEFT:
					sceneGoLeft();
					break;
				case RIGHT:
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
		
		Model m = new Model(4);
		m.getBoard().setupRound(1);
		boardController.initialize(m, this, requestController);
		this.model = m;
		
		requestController.initialize(null);
		
		stage.setTitle("Il magnifico");
		updateGui();
	}
	
	private boolean getPressed() {
		// TODO Auto-generated method stub
		return pressed;
	}

	private synchronized void setPressed(boolean b) {
		// TODO Auto-generated method stub
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
		updateGui();
	}

	private void sceneGoLeft() {
		Scene next = getScene(currentSceneIndex - 1);
		if (next != null) {
			currentSceneIndex -= 1;
		} else {
			currentSceneIndex = scenes.size() - 1;
		}
		updateGui();
	}
		
	
	public ServerResponse syncSend(ClientRequest cr) {
	    ServerResponse srr = viewController.syncSend(cr);
	    return srr;
	}

	public void updateModelAndGui(Model m) {
		setModel(m);
		updateGui();
	}
	
	private void updateGui() {
		boardController.update(this.model);
		System.out.println("GUIView -- You're in scene " + this.getCurrentScene());
		System.out.println("GUIView -- Index " + this.currentSceneIndex);
		if (getStage().getScene() != getCurrentScene()) {
			// Non dovrei invertire show e hide? Lo scatto e' supervisibile
			getStage().hide();
			getStage().setScene(getCurrentScene());
			getStage().show();
		}
	}

	public void setModel(Model m) {
		this.model = m; 
	}
	
	public static void main(String[] args) {
        Application.launch(GuiView.class, args);
    }

	public Model getModel() {
		return model;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}


}

class TT extends TimerTask
{
	private GuiView te;

	public TT(GuiView guiView) {
		super();
		this.te = guiView;
	}
	
	public void run(){
		te.timerFinished();
	}
}
