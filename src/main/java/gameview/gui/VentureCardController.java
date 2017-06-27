package gameview.gui;

import gamemodel.Point;
import gamemodel.card.Card;
import gamemodel.card.VentureCard;
import gamemodel.effects.PointModify;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class VentureCardController 
{
	@FXML TextFlow cardName;
	@FXML TextFlow victoryPoints;
	
	public void initialize(Card card)
	{
		cardName.getChildren().add(new Text(card.getName()));
		Integer victoryPoints=((PointModify)(((VentureCard)card).getActivateEffects().get(0))).getPoints().getVictory();
		this.victoryPoints.getChildren().add(new Text(victoryPoints.toString()));
	}
	
	/*public void initialize(int i)
	{
		cardName.getChildren().add((new Text("nome carta")));
		victoryPoints.getChildren().add(new Text("2"));
		
	}*/
}





