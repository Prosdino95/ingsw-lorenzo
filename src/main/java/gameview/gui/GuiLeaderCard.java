package gameview.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import gamemodel.LeaderCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class GuiLeaderCard 
{
	LeaderCard leaderCard;
	Pane pane;
	FXMLLoader loader;
	
	
	public GuiLeaderCard(LeaderCard leaderCard)
	{
		loader=new FXMLLoader();
        this.leaderCard=leaderCard;
        pane = imageGen(loader,pane);
    }
	
	public Pane getPane() 
	{
		return this.pane;
	}
	
	private Pane imageGen(FXMLLoader loader,Pane pane) 
	{
		loader.setLocation(getClass().getResource("/leaderCard.fxml"));
		try {
			pane=loader.load();
		} catch (IOException e) {
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
		LeaderCardController leaderCardController=loader.getController();
		leaderCardController.initialize(leaderCard);
		return pane;
	}
}
