package gameview;




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainView extends Application {
	
	MainViewController controller;
	
	@Override
	public void start(Stage stage) throws Exception {	
		
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/MainView.fxml"));		
		Pane root = loader.load();
		controller=loader.getController();
		controller.initialize(stage);
		root.getStylesheets().add(getClass().getResource("/radio.css").toExternalForm());
		Scene scene=new Scene(root);
		stage.setScene(scene);		
		
		stage.show();
	}

	public static void main(String[] args) {
		 Application.launch(MainView.class, args);

	}
	
	@Override
	public void stop(){
		controller.stop();
	}
	
	

}
