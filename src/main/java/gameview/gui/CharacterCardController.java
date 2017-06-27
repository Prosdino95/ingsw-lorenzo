package gameview.gui;

import gamemodel.card.Card;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CharacterCardController 
{
	@FXML TextFlow cardName;
	@FXML TextFlow cardPrice;
	
	public void initialize(Card card)
	{
		cardName.getChildren().add(new Text(card.getName())); 
		Integer cardPrice=card.getResourcePrice().getGold();
		String str;
		if(card.getResourcePrice().getGold()>1)
			str=" coins";
		else
			str=" coin";
		this.cardPrice.getChildren().add(new Text(cardPrice.toString()+str));
	}
	
	/*public void initialize(int i)
	{
		cardName.getChildren().add((new Text("nome carta")));
		cardPrice.getChildren().add((new Text("2 ori")));
	}*/
}