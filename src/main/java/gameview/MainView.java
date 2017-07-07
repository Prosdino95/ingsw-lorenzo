package gameview;




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainView extends Application {
	
	MainViewController controller;
	
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/MainView.fxml"));		
		Pane root = loader.load();
		root.setBackground(new Background(new BackgroundImage(new Image("/test.jpg"), null, null, null, null)));
		controller=loader.getController();
		controller.initialize(stage);
		root.getStylesheets().add(getClass().getResource("/radio.css").toExternalForm());
		stage.setScene(new Scene(root));
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
