package gameview.gui;

import gamemodel.card.Excommunication;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The ExcommunicationCardController object contains all the personalization we let players do about excommunication 
 * card,like its effect 
 *
 */


public class ExcommunicationCardController 
{
	@FXML TextFlow effect;
	
	public void initialize(Excommunication card)
	{
		Text texteffect=new Text(card.getPermanentEffect().toString());
		texteffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 11));
		effect.getChildren().add(texteffect);
	}
}
