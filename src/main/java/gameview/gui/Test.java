package gameview.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application  {
	
	

	@Override
	public void start(Stage stage) throws Exception {
		GuiCard guiCard=new GuiCard(null, 0, 0);
		Pane pane=new Pane();
		pane.getChildren().add(guiCard);
		
		stage.setTitle("FXML Welcome");
        stage.setScene(new Scene(pane, 500, 500));
        stage.show();
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }

}
