package gameview.gui;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

import gamemodel.Board;
import gamemodel.Resource;
import gamemodel.Tower;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.RealTowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.effects.ResourceModify;
import gamemodel.effects.TestEffects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;


public class GUIBoard extends Region{
	List<Integer> idList = new ArrayList<>();
	List<Pane> cardPaneList = new ArrayList<>();
	List<Pane> asPaneList = new ArrayList<>();
	
	Pane c;
	public GUIBoard(Board b,double w,double h){
		AnchorPane pane=new AnchorPane();
		ImageView iv1 = new ImageView(new Image("Towers.jpg"));
		iv1.setPreserveRatio(true);
		iv1.setFitWidth(w);
        iv1.setFitHeight(h);
		pane.getChildren().add(iv1);
		this.getChildren().add(pane);
		
		Pane asPane;
		Pane cardPane;
		ActionSpace tas = new RealTowerActionSpace(3, 5, new TestEffects(), new Tower(), ActionSpaceType.TOWER);
		Circle ci;
		
		CardType ct;
		Integer id;
		
		for(int column=0;column<4;column++) {
			double disr=0.232;
			for(int row=3;row>=0;row--) {				
				double cardw=0.12;
				double cardh=cardw*(0.24/0.128);
				double disc=0.242;		
				cardPane =new Pane();
				GuiView.setAll(0.01+(disr*column), 0.037+(disc*row), cardw, cardh, cardPane, w, h);
				pane.getChildren().add(cardPane);

				asPane = new Pane();
				GuiView.setAll(0.01+(disr*column) + cardw, 0.037+(disc*row), cardw / 2, cardh / 2, asPane, w, h);
				pane.getChildren().add(asPane);
				asPane.setOnMouseClicked(e -> {
					Pane me = (Pane)e.getSource();
					int i = this.asPaneList.indexOf(me);
					System.out.println(this.idList.get(i));
					
				});
				
				ci = new Circle();
				ci.setMouseTransparent(true);
				ci.setRadius(50);
				ci.setLayoutX((0.01+(disr*column) + cardw)*w);
				ci.setLayoutY((0.037+(disc*row))*h);
				ci.setStyle("-fx-background-color: #0000");
				pane.getChildren().add(ci);

				id = (3 - row) + 4*column;
				
				idList.add(id);
				asPaneList.add(asPane);
				cardPaneList.add(cardPane);
				
				if (row == 2 && column == 3) c = cardPane;
				asPane.setStyle("-fx-background-color: #ffff");				
			}
		}
		
		
	
		Card rc=new HarvesterAndBuildings(0, "test", 0, null, null, null, null, null, new ResourceModify(new Resource(1,1,1,1)),CardType.TERRITORY,5);
		GUICard card=new GUICard(rc);
		c.getChildren().add(card.getPane());
	}

}
