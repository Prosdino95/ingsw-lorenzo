package gameview.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class TestDavide extends Application {

	
	@FXML TextFlow cardTitle;
	@Override
	public void start(Stage primaryStage) 
	{
		primaryStage.setTitle("carta");
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(TestDavide.class.getResource("/territoryCard.fxml"));
		
		try {
			Pane pane=loader.load();
			cardTitle.getChildren().add(new Text("nome"));
			Scene scene=new Scene(pane);
			 primaryStage.setScene(scene);
	            primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	 private void initialize() {
	        // Initialize the person table with the two columns.
		 cardTitle.getChildren().add(new Text("nome"));
	    }
	public static void main(String[] args) 
	{
		launch(args);
	}
}




