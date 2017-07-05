package gameview.gui;

import gamemodel.card.Card;
import gamemodel.card.CharactersCard;
import gamemodel.effects.IstantEffect;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CharacterCardController 
{
	@FXML TextFlow cardName;
	@FXML TextFlow cardPrice;
	@FXML TextFlow instantEffect;
	@FXML TextFlow permanentEffect;
	
	public void initialize(Card card)
	{
		Text textCardName=new Text(card.getName());
		textCardName.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		cardName.getChildren().add(textCardName);
		
		Integer cardPrice=card.getResourcePrice().getGold();
		Text textCardPrice=new Text(cardPrice.toString()+" c");
		textCardPrice.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 10));
		this.cardPrice.getChildren().add(textCardPrice);

		if(!card.getIstantEffect().isEmpty())
		{
			for(IstantEffect e:card.getIstantEffect()){
				Text instantEffect=new Text(e.toString()+"\n");
				instantEffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
				this.instantEffect.getChildren().add(instantEffect);
			}
		}

		if(((CharactersCard)card).getPermanentEffects()!=null)
		{
			Text permanentEffect=new Text(((CharactersCard)card).getPermanentEffects().toString());
			permanentEffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
			this.permanentEffect.getChildren().add(permanentEffect);
		}
		
		
		
		
	}
}