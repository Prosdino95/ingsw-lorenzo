package gameview.gui;

import gamemodel.Board;
import gamemodel.Resource;
import gamemodel.card.CardType;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.card.RealCard;
import gamemodel.effects.ResourceModify;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class GUIBoard extends Region{
	
	
	public GUIBoard(Board b,double w,double h){
		AnchorPane pane=new AnchorPane();
		ImageView iv1 = new ImageView(new Image("Towers.jpeg"));
		iv1.setPreserveRatio(true);
		iv1.setFitWidth(w);
        iv1.setFitHeight(h);
		pane.getChildren().add(iv1);
		this.getChildren().add(pane);
		for(int j=0;j<4;j++){
			double disr=0.232;
			for(int i=0;i<4;i++){
				double cardw=0.12;
				double cardh=cardw*(0.24/0.128);
				double disc=0.242;
				RealCard c=new HarvesterAndBuildings(0, "test", 0, null, null, null, null, null, new ResourceModify(new Resource(1,1,1,1)),CardType.TERRITORY,5);
				GuiCard card=new GuiCard(c,cardw*w,cardh*h);
				GuiView.setAll(0.01+(disr*j), 0.037+(disc*i), cardw, cardh, card, w, h);
				pane.getChildren().add(card);
				
			}
		}	
	
	}

}
