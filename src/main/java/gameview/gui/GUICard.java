package gameview.gui;




import java.io.IOException;

import gamemodel.card.Card;
import gamemodel.card.CardType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class GUICard
{
	Card card;
	Pane pane;
	FXMLLoader loader;
	
	public GUICard(Card card)
	{
		loader=new FXMLLoader();
        this.card=card;
        pane = imageGen(card.getType(),loader,pane);
    }

	public Pane getPane() 
	{
		return this.pane;
	}

	private Pane imageGen(CardType type,FXMLLoader loader,Pane pane) 
	{
		try {
			switch(type)
				{
					case BUILDING:
					{
						loader.setLocation(getClass().getResource("/buildingCard.fxml"));
						pane=loader.load();
						BuildingCardController buildingCardController=loader.getController();
						buildingCardController.initialize(card);
					} break;
					case CHARACTER:
					{
						loader.setLocation(getClass().getResource("/characterCard.fxml"));
						pane=loader.load();
						CharacterCardController characterCardController=loader.getController();
						characterCardController.initialize(card);
					} break;
					case TERRITORY:
					{
						loader.setLocation(getClass().getResource("/territoryCard.fxml"));
						pane=loader.load();
						TerritoryCardController territoryCardController=loader.getController();
						territoryCardController.initialize(card);			
					} break;
					case VENTURE:
					{
						loader.setLocation(getClass().getResource("/ventureCard.fxml"));
						pane=loader.load();
						VentureCardController ventureCardController=loader.getController();
						ventureCardController.initialize(card);
					} break;
					default:
						break;
				} 
			}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return pane;
	}
}
