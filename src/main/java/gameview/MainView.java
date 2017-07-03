package gameview;




import java.io.IOException;
import java.rmi.NotBoundException;

import gameview.cli.CLIView;
import gameview.gui.GuiView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainView extends Application {
	
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/MainView.fxml"));		
		Pane root = loader.load();
		MainViewController controller=loader.getController();
		controller.initialize(stage);
		root.getStylesheets().add(getClass().getResource("/radio.css").toExternalForm());
		stage.setScene(new Scene(root));
		stage.show();
	}

	public static void main(String[] args) {
		 Application.launch(MainView.class, args);

	}
	
	

}
