package gameview;




import java.io.File;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainView extends Application {
	
	MainViewController controller;
	
	
	
	
	
	@Override
	public void start(Stage stage) throws Exception {

		
		
		
		
		
		
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/MainView.fxml"));		
		Pane root = loader.load();
		
		
		
		//root.setBackground(new Background(new BackgroundImage(new Image("/lorenzo.jpg"), null, null, null, null)));
		controller=loader.getController();
		controller.initialize(stage);
		root.getStylesheets().add(getClass().getResource("/radio.css").toExternalForm());
		Scene scene=new Scene(root);
		stage.setScene(scene);
		
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) 
            {
                switch (event.getCode()) 
                {
                    case V:    controller.muteButtonAction(); break;
          
				default:
					break;
                }
            }
        });
		
		
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
