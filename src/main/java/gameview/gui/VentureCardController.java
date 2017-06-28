package gameview.gui;

import gamemodel.card.Card;
import gamemodel.card.VentureCard;
import gamemodel.effects.PointModify;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class VentureCardController 
{
	@FXML TextFlow cardName;
	@FXML TextFlow victoryPoints;
	@FXML TextFlow instantEffect;
	
	public void initialize(Card card)
	{
		Text cardName=new Text(card.getName());
		cardName.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		this.cardName.getChildren().add(cardName);	
		
		if(card.getIstantEffect().size()!=0)
		{
			Text instantEffect=new Text(card.getIstantEffect().get(0).toString());
			instantEffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
			this.instantEffect.getChildren().add(instantEffect);
		}
		
		if(((VentureCard)card).getPermanentEffects().size()>0)
		{
			Integer victoryPoint=((PointModify)(((VentureCard)card).getPermanentEffects().get(0))).getPoints().getVictory();
			this.victoryPoints.getChildren().add(new Text(victoryPoint.toString()));
		}		
	}
}





