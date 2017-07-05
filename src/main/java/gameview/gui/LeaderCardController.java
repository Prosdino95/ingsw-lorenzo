package gameview.gui;

import gamemodel.LeaderCard;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LeaderCardController 
{
	@FXML TextFlow effect;
	@FXML TextFlow cardName;
	@FXML TextFlow cardRequirement;
	
	public void initialize(LeaderCard leaderCard)
	{
		Text cardName=new Text(leaderCard.getName());
		cardName.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		this.cardName.getChildren().add(cardName);                 
		
		Text cardRequirement=new Text(leaderCard.getRequirement().toString());
		cardRequirement.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		this.cardRequirement.getChildren().add(cardName);
		
		if(leaderCard.getOncePerRound().size()!=0)
		{
			Text effect=new Text(leaderCard.getOncePerRound().get(0).toString());
			effect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
			this.effect.getChildren().add(effect);
		}
		if(leaderCard.getPe()!=null)
		{
			Text effect=new Text(leaderCard.getPe().toString());
			effect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
			this.effect.getChildren().add(effect);
		}
	}
}
