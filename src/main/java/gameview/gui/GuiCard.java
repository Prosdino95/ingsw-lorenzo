package gameview.gui;


import gamemodel.Resource;
import gamemodel.card.CardType;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.card.RealCard;
import gamemodel.effects.ResourceModify;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


public class GuiCard extends Region{
	
	Image image;
	RealCard card;
	String actionCost;
	
	

	public GuiCard(RealCard card,double w,double h) throws Exception {
		
		this.card=card;
		AnchorPane pane=new AnchorPane();
		image = imageGen(card.getType());
		ImageView iv1 = new ImageView();
		Pane effectPane=new Pane();
		iv1.setImage(image); 
        iv1.setFitWidth(w);
        iv1.setFitHeight(h);
        pane.getChildren().add(iv1);
        TextFlow cName=new TextFlow(new Text(card.getName()));
        TextFlow cost=new TextFlow(new Text(actionCost));
        TextFlow activateEffect=new TextFlow(new Text(new ResourceModify(new Resource(1,1,1,1)).toString()));
        effectPane.getChildren().add(activateEffect);
        cName.setTextAlignment(TextAlignment.CENTER);
        GuiView.setAll(0.13, 16.0/200, 96.0/134, 11.0/200, cName,w,h);
        cost.setPrefSize(13.0/134*w, 17.0/200*h);
        cost.setLayoutX(28.0/134*w);
        cost.setLayoutY(165.0/200*h);
        activateEffect.setPrefSize(64.0/134*w, 39.0/200*h);
        effectPane.setPrefSize(64.0/134*w, 38.0/200*h);
        effectPane.setLayoutX(45.0/134*w);
        effectPane.setLayoutY(142.0/200*h);
        pane.getChildren().add(cName);
        pane.getChildren().add(cost);
        pane.getChildren().add(effectPane);
        this.getChildren().add(pane);
	}

	private Image imageGen(CardType type) {
		switch(type){
		case BUILDING:
			break;
		case CHARACTER:
			break;
		case TERRITORY:{
			HarvesterAndBuildings HAndB=(HarvesterAndBuildings)card;
			actionCost=HAndB.getActionCost().toString();
		}
			break;
		case VENTURE:
			break;
		default:
			break;
		
		}
		
		return null;
	}

}
