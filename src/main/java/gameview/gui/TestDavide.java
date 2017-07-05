package gameview.gui;

import java.io.IOException;

import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Team;
import gamemodel.card.Card;
import gamemodel.command.GameException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class TestDavide extends Application {

	
	@Override
	public void start(Stage primaryStage) throws IOException, GameException 
	{
		primaryStage.setTitle("carta");
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/PlayerBoard2.fxml"));
		Pane pane=loader.load();
		PlayerBoardController2 tcc=loader.getController();
		Model m=new Model(2);
		Player p = m.getPlayer(Team.RED);
//		tcc.initialize();
		tcc.update(p);
		Scene scene=new Scene(pane);
		primaryStage.setScene(scene);
	    primaryStage.show();		
	}
	
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}









/*package gameview.gui;




import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.CharactersCard;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.card.RealCard;
import gamemodel.card.VentureCard;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


public class GUICard extends Region{
	
	Image image;
	Card card;
	String actionCost = "";
	Pane effectPane;
	String tooltip = "";

	public GUICard(Card card,double w,double h) {
		AnchorPane pane=new AnchorPane();
		ImageView iv = new ImageView();
        iv.setFitWidth(w);
        iv.setFitHeight(h);
        pane.getChildren().add(iv);
		effectPane=new Pane();

        TextFlow cName=new TextFlow();
        cName.setTextAlignment(TextAlignment.CENTER);
        TextFlow cost=new TextFlow();
        TextFlow activateEffect=new TextFlow(new Text("G +1"));
        effectPane.getChildren().add(activateEffect);

        if (card != null) {
        	this.card=card;
        	image = imageGen(card.getType());
        	iv.setImage(image); 
        	cName = new TextFlow(new Text(card.getName()));
        	cost=new TextFlow(new Text(actionCost));
        }
        GuiView.setAll(0.13, 4.0/200, 96.0/134, 11.0/200, cName,w,h);
        cost.setPrefSize(13.0/134*w, 17.0/200*h);
        cost.setLayoutX(28.0/134*w);
        cost.setLayoutY(165.0/200*h);
        activateEffect.setPrefSize(64.0/134*w, 39.0/200*h);
        //activateEffect.setMaxSize(64.0/134*w, 39.0/200*h);
        effectPane.setPrefSize(64.0/134*w, 38.0/200*h);
        effectPane.setLayoutX(45.0/134*w);
        effectPane.setLayoutY(142.0/200*h);
        pane.getChildren().add(cName);
        pane.getChildren().add(cost);
        pane.getChildren().add(effectPane);
        this.getChildren().add(pane);
       // Tooltip tt=new Tooltip();
       // Tooltip.install(effectPane, tt);
        Tooltip.install(effectPane,new Tooltip(tooltip));
	}

	private Image imageGen(CardType type) {
		switch(type){
		case BUILDING:{
			HarvesterAndBuildings HAndB=(HarvesterAndBuildings)card;
			actionCost=HAndB.getActionCost().toString();
			tooltip=HAndB.getActivateEffect().toString();
			return new Image("buildingCard.png");
			
		}
		case CHARACTER:{
			
		}
			break;
		case TERRITORY:{
			HarvesterAndBuildings HAndB=(HarvesterAndBuildings)card;
			actionCost=HAndB.getActionCost().toString();
			tooltip=HAndB.getActivateEffect().toString();
			return new Image("territoryCard.png");
		}
		case VENTURE:{
		
		}
			break;
		default:
			break;
		
		}
		
		return null;
	}

}*/