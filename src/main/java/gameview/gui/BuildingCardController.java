package gameview.gui;

import gamemodel.card.Card;
import gamemodel.card.HarvesterAndBuildings;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class BuildingCardController 
{
	@FXML TextFlow cardName;
	@FXML TextFlow actionValue;
	@FXML TextFlow instantEffect;
	@FXML TextFlow permanentEffect;
	
	public void initialize(Card card)
	{
		Text cardName=new Text(card.getName());
		cardName.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		this.cardName.getChildren().add(cardName);                 
		
		Integer actionValue=((HarvesterAndBuildings)card).getActionCost();
		this.actionValue.getChildren().add(new Text(actionValue.toString()));
	
		if(card.getIstantEffect().size()!=0)
		{
			Text instantEffect=new Text(card.getIstantEffect().get(0).toStringGui());
			instantEffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
			this.instantEffect.getChildren().add(instantEffect);
		}
		
		if(((HarvesterAndBuildings)card).getPermanentEffects().size()!=0)
		{
			Text permanentEffect=new Text(((HarvesterAndBuildings)card).getPermanentEffects().get(0).toStringGui());
			permanentEffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
			this.permanentEffect.getChildren().add(permanentEffect);
		}
		
	}
}
