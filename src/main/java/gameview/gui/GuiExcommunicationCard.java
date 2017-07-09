package gameview.gui;

import java.io.IOException;
import gamemodel.card.Excommunication;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * The GuiExcommunicationCard object contains the logic the let us to create a graphical excommunication card from 
 * our model card
 * 
 *
 */

public class GuiExcommunicationCard 
{
	Excommunication card;
	Pane pane;
	FXMLLoader loader;
	
	public GuiExcommunicationCard(Excommunication card)
	{
		loader=new FXMLLoader();
        this.card=card;
        pane = imageGen(loader,pane);
    }

	public Pane getPane() 
	{
		return this.pane;
	}
	
	private Pane imageGen(FXMLLoader loader,Pane pane) 
	{
		loader.setLocation(getClass().getResource("/excommunicationCard.fxml"));
		try {
			pane=loader.load();
		} catch (IOException e) {
			
			System.out.println("file fxml not found");
		}
		ExcommunicationCardController excommunicationCardController=loader.getController();
		excommunicationCardController.initialize(card);
		return pane;
	}
}
