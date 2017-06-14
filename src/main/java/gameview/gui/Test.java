package gameview.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application  {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/Card.fxml"));
		
		stage.setTitle("FXML Welcome");
        stage.setScene(new Scene(root, 134, 200));
        stage.show();
	}
	
	public static void main(String[] args) {
        Application.launch(Test.class, args);
    }

}
