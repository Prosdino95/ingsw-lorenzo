package gameview.gui;

import gamemodel.card.Card;
import gamemodel.card.HarvesterAndBuildings;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class BuildingCardController 
{
	@FXML TextFlow cardName;
	@FXML TextFlow actionValue;
	
	public void initialize(Card card)
	{
		cardName.getChildren().add(new Text(card.getName()));                 
		Integer actionValue=((HarvesterAndBuildings)card).getActionCost();
		this.actionValue.getChildren().add(new Text(actionValue.toString()));
	}
	
	/*public void initialize(int i)
	{
		cardName.getChildren().add((new Text("nome carta")));
		actionValue.getChildren().add((new Text("5")));
	}*/
}