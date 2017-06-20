package gameview.gui;

import gamemodel.Resource;
import gamemodel.card.CardType;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.card.RealCard;
import gamemodel.effects.ResourceModify;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application  {
	
	

	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
		//stage.setFullScreen(true);
		double w=1000;
		double h=700;		
		System.out.println(""+w+h);
		//RealCard c=new HarvesterAndBuildings(0, "test", 0, null, null, null, null, new ResourceModify(new Resource(1,1,1,1)), null,CardType.TERRITORY,5);
		//GuiCard guiCard=new GuiCard(c, 0.12*w, 0.12*h*(0.24/0.128));
		Pane pane=new Pane();
		//pane.getChildren().add(guiCard);
		GUIBoard board=new GUIBoard(null,3945.0/3546.0*h,h);
		pane.getChildren().add(board);
		stage.setTitle("FXML Welcome");
        stage.setScene(new Scene(pane, w, h));
        stage.show();
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }

}
