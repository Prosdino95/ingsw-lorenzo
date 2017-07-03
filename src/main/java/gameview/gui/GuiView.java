package gameview.gui;


import java.util.ArrayList;
import java.util.List;

import gamemodel.Model;

import gameview.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import reti.ClientRequest;
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
	
	public static void setAll(double x,double y,double w,double h,Region r,double ww,double wh){
		r.setLayoutX(x*ww);
		r.setLayoutY(y*wh);
		r.setPrefSize(w*ww, h*wh);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.setStage(stage);
		Integer w = 1024;
		Integer h = 800;
		
	
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/Board2.fxml"));		
		rootPane = loader.load();
		boardController = loader.getController();
		//boardRoot.setPrefSize(w, h);
		rootPane.setOnMouseClicked(e -> {
			System.out.println(boardScene.getWidth());
			updateGui();
		});
		rootPane.setStyle("-fx-background-color: #228b22");
		Scene bs = new Scene(rootPane);
		boardScene = bs;
		
		addScene(bs);
		bs.setOnKeyPressed(e -> {
			System.out.println("You pressed key " + e.getCode());
			switch (e.getCode()) {
			case LEFT:
				sceneGoLeft();
				break;
			case RIGHT:
				sceneGoRight();
				break;
			default:
//				System.out.println("Don't know what to do with " + e.getCode());
			}
		});

		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Request.fxml"));
		Pane requestPane;
		requestPane = loader.load();
		requestController = loader.getController();
		bs = new Scene(requestPane);
		addScene(bs);		
		bs.setOnKeyPressed(e -> {
			System.out.println("You pressed key " + e.getCode());
			switch (e.getCode()) {
			case LEFT:
				sceneGoLeft();
				break;
			case RIGHT:
				sceneGoRight();
				break;
			default:
//				System.out.println("Don't know what to do with " + e.getCode());
			}
		});

		
		Pane p = new AnchorPane();
		p.setStyle("-fx-background-color: #b22222");
		bs = new Scene(p);
		addScene(bs);
		bs.setOnKeyPressed(e -> {
			System.out.println("You pressed key " + e.getCode());
			switch (e.getCode()) {
			case LEFT:
				sceneGoLeft();
				break;
			case RIGHT:
				sceneGoRight();
				break;
			default:
				break;
			}
		});

		
		p = new AnchorPane();
		p.setStyle("-fx-background-color: #cd6889");
		bs = new Scene(p);
		addScene(bs);
		bs.setOnKeyPressed(e -> {
			System.out.println("You pressed key " + e.getCode());
			switch (e.getCode()) {
			case LEFT:
				sceneGoLeft();
				break;
			case RIGHT:
				sceneGoRight();
				break;
			}
		});
		

		Model m = new Model(4);
		m.getBoard().setupRound(1);
		boardController.initialize(m, this, requestController);
		this.model = m;
		
		requestController.initialize(null);
		
		stage.setTitle("Il magnifico");
		updateGui();
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
		System.out.println("You're in scene " + this.getCurrentScene());
		System.out.println("Index " + this.currentSceneIndex);
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
