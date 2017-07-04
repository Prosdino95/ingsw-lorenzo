package gameview.gui;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Player;
import gamemodel.card.HarvesterAndBuildings;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class PlayerBoardController 
{
	@FXML Pane territory0;
	@FXML Pane territory1;
	@FXML Pane territory2;
	@FXML Pane territory3;
	@FXML Pane territory4;
	@FXML Pane territory5;
	@FXML Pane building0;
	@FXML Pane building1;
	@FXML Pane building2;
	@FXML Pane building3;
	@FXML Pane building4;
	@FXML Pane building5;
	
	List<Pane> territoryCardPaneList=new ArrayList<>();
	List<Pane> buildingCardPaneList=new ArrayList<>();

	public void initialize()
	{
		territoryCardPaneList.add(territory0);
		territoryCardPaneList.add(territory1);
		territoryCardPaneList.add(territory2);
		territoryCardPaneList.add(territory3);
		territoryCardPaneList.add(territory4);
		territoryCardPaneList.add(territory5);
		buildingCardPaneList.add(building0);
		buildingCardPaneList.add(building1);
		buildingCardPaneList.add(building2);
		buildingCardPaneList.add(building3);
		buildingCardPaneList.add(building4);
		buildingCardPaneList.add(building5);
	}
	
	public void update(Player player) 
	{
		for(int c=0;c<player.getTerritories().size();c++)
		{
			HarvesterAndBuildings cc=player.getTerritories().get(c);
			GUICard gc=new GUICard(cc);
			Pane pane=gc.getPane();
			pane.setLayoutX(35);
			pane.setLayoutY(58);
			pane.setScaleX(1.8);
			pane.setScaleY(1.8);
			territoryCardPaneList.get(c).getChildren().add(gc.getPane());
		}
		for(int c=0;c<player.getBuildings().size();c++)
		{
			HarvesterAndBuildings cc=player.getBuildings().get(c);
			GUICard gc=new GUICard(cc);
			Pane pane=gc.getPane();
			pane.setLayoutX(35);
			pane.setLayoutY(58);
			pane.setScaleX(1.8);
			pane.setScaleY(1.8);
			buildingCardPaneList.get(c).getChildren().add(gc.getPane());
		}
	}
	
}
