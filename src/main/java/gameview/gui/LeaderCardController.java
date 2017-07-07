package gameview.gui;

import gamemodel.LeaderCard;
import gamemodel.LeaderState;
import javafx.fxml.FXML;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	@FXML ImageView card;
	
	public void initialize(LeaderCard leaderCard)
	{
		Text cardName=new Text(leaderCard.getName());
		changeImmage(leaderCard);
		if(leaderCard.getState()==LeaderState.USED_OPR) return;
		cardName.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		this.cardName.getChildren().add(cardName);                 
		Text cardRequirement=new Text(leaderCard.getRequirement().toString());
		cardRequirement.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		this.cardRequirement.getChildren().add(cardRequirement);
		
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

	private void changeImmage(LeaderCard leaderCard) {
		switch(leaderCard.getState()){
		case NOT_PLAYED:
			break;
		case PLAYED:
			card.setEffect(new Glow(0.7));
			break;
		case USED_OPR:
			card.setImage(new Image("/leaderCardBack.jpg"));
			break;
		default:
			break;	
		}		
	}
}
