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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class GuiView extends Application {
	
	public static void setAll(double x,double y,double w,double h,Region r,double ww,double wh){
		r.setLayoutX(x*ww);
		r.setLayoutY(y*wh);
		r.setPrefSize(w*ww, h*wh);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/PlayerBoard.fxml"));		
		AnchorPane root=loader.load();
		PlayerBoardController c=loader.getController();
		RealCard card=new HarvesterAndBuildings(0, "test", 0, null, null, null, null, null, new ResourceModify(new Resource(1,1,1,1)),CardType.TERRITORY,5);
		GuiCard guicard=new GuiCard(card,0,0);
		guicard.setPrefSize(100, 200);
		c.metti(guicard);
		stage.setTitle("FXML Welcome");
	    stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
	    stage.show();
	}
	
	public static void main(String[] args) {
        Application.launch(GuiView.class, args);
    }

}
