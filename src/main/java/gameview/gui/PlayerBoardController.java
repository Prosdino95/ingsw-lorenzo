package gameview.gui;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Player;
import gamemodel.card.CharactersCard;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class PlayerBoardController {
	@FXML private Pane char0;
	@FXML Pane char1;
	List<Pane> cardPaneList=new ArrayList<>();
	
	
	public void initialize()
	{
		cardPaneList.add(char0);
		cardPaneList.add(char1);
		
		
	}
	
	public void update(Player player) 
	{
		for(int c=0;c<player.getCharacters().size();c++)
		{
			CharactersCard cc=player.getCharacters().get(c);
			GUICard gc=new GUICard(cc);
			Pane pane=gc.getPane();
			
			pane.setLayoutX(35);
			pane.setLayoutY(58);
			pane.setScaleX(1.8);
			pane.setScaleY(1.8);
			cardPaneList.get(c).getChildren().add(gc.getPane());
		}
			
    }

}
