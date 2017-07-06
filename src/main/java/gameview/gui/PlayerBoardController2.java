package gameview.gui;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Player;
import gamemodel.card.CharactersCard;
import gamemodel.card.VentureCard;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
	@FXML Pane servants,gold,faith,stone,victory,military,wood;
	
	List<Pane> characterCardPaneList=new ArrayList<>();
	List<Pane> ventureCardPaneList=new ArrayList<>();
	List<Pane> resPoint=new ArrayList<>();
	
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
		resPoint.add(servants);
		resPoint.add(stone);
		resPoint.add(wood);
		resPoint.add(gold);
		resPoint.add(victory);
		resPoint.add(military);
		resPoint.add(faith);
	}
	
	public void update(Player player) 
	{
		if(player==null) return;
		for(int c=0;c<player.getCharacters().size();c++)
		{
			CharactersCard cc=player.getCharacters().get(c);
			GUICard gc=new GUICard(cc);
			Pane pane=gc.getPane();
			pane.setLayoutX(20);
			pane.setLayoutY(35);
			pane.setScaleX(1.5);
			pane.setScaleY(1.5);
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
		for(Pane p:this.resPoint)
			p.getChildren().clear();
		Font f=new Font(30);
		Text gold=new Text(""+player.getResource().getGold());
		gold.setFont(f);
		Text stone=new Text(""+player.getResource().getStone());
		stone.setFont(f);
		Text servants=new Text(""+player.getResource().getServant());
		servants.setFont(f);
		Text wood=new Text(""+player.getResource().getWood());
		wood.setFont(f);
		Text victory=new Text(""+player.getPoint().getVictory());
		victory.setFont(f);
		Text faith=new Text(""+player.getPoint().getFaith());
		faith.setFont(f);
		Text military=new Text(""+player.getPoint().getMilitary());
		military.setFont(f);
		
		this.gold.getChildren().add(new TextFlow(gold));
		this.stone.getChildren().add(new TextFlow(stone));
		this.servants.getChildren().add(new TextFlow(servants));
		this.wood.getChildren().add(new TextFlow(wood));
		this.faith.getChildren().add(new TextFlow(faith));
		this.victory.getChildren().add(new TextFlow(victory));
		this.military.getChildren().add(new TextFlow(military));		
	}
}
