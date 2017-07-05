package gameview.gui;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Player;
import gamemodel.card.CharactersCard;
import gamemodel.card.VentureCard;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class PlayerBoardController2 
{
	@FXML Pane character0;
	@FXML Pane character1;
	@FXML Pane character2;
	@FXML Pane character3;
	@FXML Pane character4;
	@FXML Pane character5;
	@FXML Pane venture0;
	@FXML Pane venture1;
	@FXML Pane venture2;
	@FXML Pane venture3;
	@FXML Pane venture4;
	@FXML Pane venture5;
	
	List<Pane> characterCardPaneList=new ArrayList<>();
	List<Pane> ventureCardPaneList=new ArrayList<>();
	
	public void initialize(Object object)
	{
		characterCardPaneList.add(character0);
		characterCardPaneList.add(character1);
		characterCardPaneList.add(character2);
		characterCardPaneList.add(character3);
		characterCardPaneList.add(character4);
		characterCardPaneList.add(character5);
		ventureCardPaneList.add(venture0);
		ventureCardPaneList.add(venture1);
		ventureCardPaneList.add(venture2);
		ventureCardPaneList.add(venture3);
		ventureCardPaneList.add(venture4);
		ventureCardPaneList.add(venture5);
	}
	
	public void update(Player player) 
	{
		if(player==null) return;
		for(int c=0;c<player.getCharacters().size();c++)
		{
			CharactersCard cc=player.getCharacters().get(c);
			GUICard gc=new GUICard(cc);
			Pane pane=gc.getPane();
			pane.setLayoutX(35);
			pane.setLayoutY(58);
			pane.setScaleX(1.8);
			pane.setScaleY(1.8);
			characterCardPaneList.get(c).getChildren().add(gc.getPane());
		}
		for(int c=0;c<player.getVentures().size();c++)
		{
			VentureCard cc=player.getVentures().get(c);
			GUICard gc=new GUICard(cc);
			Pane pane=gc.getPane();
			pane.setLayoutX(20);
			pane.setLayoutY(35);
			pane.setScaleX(1.5);
			pane.setScaleY(1.5);
			ventureCardPaneList.get(c).getChildren().add(gc.getPane());
		}
	}
}
