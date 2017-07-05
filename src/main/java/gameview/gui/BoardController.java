package gameview.gui;




import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Color;
import gamemodel.Model;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.RealActionSpace;
import gamemodel.actionSpace.RealTowerActionSpace;
import gamemodel.card.Card;
import gameview.gui.actionspacecontroll.ActionSpaceControll;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;

public class BoardController {
	List<Integer> asIdList;
	List<Pane> cardPaneList;
	List<Pane> asPaneList;
	private GuiView guiView;
	private boolean big = false;
//	private Pane dialogPane;
	//private RequestController requestController;
	
	public void update(Model model) {
		if(model==null) return;
		blackDice.getChildren().clear();
		whiteDice.getChildren().clear();
		orangeDice.getChildren().clear();
		orangeDice.getChildren().add(new Text(model.getBoard().getDice().getValue(Color.ORANGE).toString()));
		whiteDice.getChildren().add(new Text(model.getBoard().getDice().getValue(Color.WHITE).toString()));
		Text text=new Text(model.getBoard().getDice().getValue(Color.BLACK).toString());
		text.setFill(javafx.scene.paint.Color.WHITE);
		blackDice.getChildren().add(text);
			
		for (Integer id : asIdList) {
			RealActionSpace as = model.getBoard().getActionSpace(id);
			Pane asp=asPaneList.get(id);
			try {
				asp.getChildren().add(makeAS(as));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (as == null) {
				continue;
			}
			if (as instanceof RealTowerActionSpace) {
				Card card = ((RealTowerActionSpace) as).getCard();
				Pane cardPane = cardPaneList.get(id);
				if (!cardPane.getChildren().isEmpty()) 
					cardPane.getChildren().remove(0);
				if(card!=null){
					GUICard gcard = new GUICard(card);
					cardPane.getChildren().add(gcard.getPane());	
				}	
				
			}
		}

	}
	
	public boolean isBig() {
		return big;
	}

	synchronized public void setBig(boolean big) {
		this.big = big;
	}

	
	public void initialize(GuiView v, RequestController requestController) throws IOException {
		
		 asIdList = new ArrayList<>();
		 cardPaneList = new ArrayList<>();
		 asPaneList = new ArrayList<>();
		 
//		 dialogPane.
		
		this.guiView = v;
		//this.requestController = requestController;
		
		 asIdList.add(0);
		 cardPaneList.add(cartaTorre0);
		 asPaneList.add(asTorre0);
		 asIdList.add(1);
		 cardPaneList.add(cartaTorre1);
		 asPaneList.add(asTorre1);
		 asIdList.add(2);
		 cardPaneList.add(cartaTorre2);
		 asPaneList.add(asTorre2);
		 asIdList.add(3);
		 cardPaneList.add(cartaTorre3);
		 asPaneList.add(asTorre3);
		 asIdList.add(4);
		 cardPaneList.add(cartaTorre4);
		 asPaneList.add(asTorre4);
		 asIdList.add(5);
		 cardPaneList.add(cartaTorre5);
		 asPaneList.add(asTorre5);
		 asIdList.add(6);
		 cardPaneList.add(cartaTorre6);
		 asPaneList.add(asTorre6);
		 asIdList.add(7);
		 cardPaneList.add(cartaTorre7);
		 asPaneList.add(asTorre7);
		 asIdList.add(8);
		 cardPaneList.add(cartaTorre8);
		 asPaneList.add(asTorre8);
		 asIdList.add(9);
		 cardPaneList.add(cartaTorre9);
		 asPaneList.add(asTorre9);
		 asIdList.add(10);
		 cardPaneList.add(cartaTorre10);
		 asPaneList.add(asTorre10);
		 asIdList.add(11);
		 cardPaneList.add(cartaTorre11);
		 asPaneList.add(asTorre11);
		 asIdList.add(12);
		 cardPaneList.add(cartaTorre12);
		 asPaneList.add(asTorre12);
		 asIdList.add(13);
		 cardPaneList.add(cartaTorre13);
		 asPaneList.add(asTorre13);
		 asIdList.add(14);
		 cardPaneList.add(cartaTorre14);
		 asPaneList.add(asTorre14);
		 asIdList.add(15);
		 cardPaneList.add(cartaTorre15);
		 asPaneList.add(asTorre15);
		 asIdList.add(16);
		 asPaneList.add(asHAndp1);
		 asIdList.add(17);
		 asPaneList.add(asHAndp2);
		 asIdList.add(18);
		 asPaneList.add(asMarket1);
		 asIdList.add(19);
		 asPaneList.add(asMarket2);
		 asIdList.add(20);
		 asPaneList.add(asMarket3);
		 asIdList.add(21);
		 asPaneList.add(asMarket4);
		 asIdList.add(22);
		 asPaneList.add(councilPlace);
		 
		
		 
		 
		 for (Integer id : asIdList) {
			 	if(id<16)
			 	{
			 		Pane cp = cardPaneList.get(id);
			 		cp.setOnMouseEntered(e-> {
						if (!isBig()) {
							cp.toFront();
							cp.setScaleX(1.7);
							cp.setScaleY(1.7);
							cp.setTranslateX(25);
							if (id % 4 == 0)
								cp.setTranslateY(-50);
							else
								cp.setTranslateY(25);
						}
						setBig(true);
					});
					cp.setOnMouseExited(e-> {
						if (isBig()) {
							cp.setScaleX(1);
							cp.setScaleY(1);
							cp.setTranslateX(0);
							cp.setTranslateY(0);
						}
						setBig(false);
					});
			 		
			 	}
			 	
			 	Pane asp = asPaneList.get(id);
			 	asp.setOnMouseClicked(e -> {
			 		requestController.setActionSpace(id);
			 	});
			 		
		}
		 
	}
	
	public void request(Event e){
		
		System.out.println(asPaneList.indexOf(e.getSource()));
		Popup p=new Popup();
		Pane pa=new Pane();
		Button b=new Button();
		b.setOnAction(ev->p.hide());
		pa.setPrefWidth(100);
		pa.setPrefHeight(100);
		pa.getChildren().add(b);		
		p.getContent().add(pa);
		p.show(guiView.getStage());
	}
	
	
	private Pane makeAS(ActionSpace actionSpace) throws IOException{
		Pane asRoot=new Pane();
		FXMLLoader loader;
		ActionSpaceControll controll;
		loader=new FXMLLoader();
		switch(actionSpace.getType()){
		case COUNCIL_PALACE:
			loader.setLocation(getClass().getResource("/CouncilPlaceActionSpace.fxml"));			
			break;
		case PRODUCTION:
			loader.setLocation(getClass().getResource("/HAndPActionSpace.fxml"));
			break;
		case HARVEST:
			loader.setLocation(getClass().getResource("/HAndPActionSpace.fxml"));
			break;
		case TOWER:
		case MARKET:			
			loader.setLocation(getClass().getResource("/NormalActionSpace.fxml"));		
			break;	
		}
		asRoot = loader.load();
		controll=loader.getController();
		controll.initialize(actionSpace);
	 	return asRoot;		
	}
	
	@FXML Pane asHAndp1;
	@FXML Pane asHAndp2;
	@FXML Pane councilPlace;
	@FXML Pane cartaTorre0;
	@FXML Pane cartaTorre1;
	@FXML Pane cartaTorre2;
	@FXML Pane cartaTorre3;
	@FXML Pane cartaTorre4;
	@FXML Pane cartaTorre5;
	@FXML Pane cartaTorre6;
	@FXML Pane cartaTorre7;
	@FXML Pane cartaTorre8;
	@FXML Pane cartaTorre9;
	@FXML Pane cartaTorre10;
	@FXML Pane cartaTorre11;
	@FXML Pane cartaTorre12;
	@FXML Pane cartaTorre13;
	@FXML Pane cartaTorre14;
	@FXML Pane cartaTorre15;
	@FXML TextFlow blackDice,whiteDice,orangeDice;
	@FXML Pane asTorre0;
	@FXML Pane asTorre1;
	@FXML Pane asTorre2;
	@FXML Pane asTorre3;
	@FXML Pane asTorre4;
	@FXML Pane asTorre5;
	@FXML Pane asTorre6;
	@FXML Pane asTorre7;
	@FXML Pane asTorre8;
	@FXML Pane asTorre9;
	@FXML Pane asTorre10;
	@FXML Pane asTorre11;
	@FXML Pane asTorre12;
	@FXML Pane asTorre13;
	@FXML Pane asTorre14;
	@FXML Pane asTorre15;
	@FXML Pane asMarket1;
	@FXML Pane asMarket2;
	@FXML Pane asMarket3;
	@FXML Pane asMarket4;

}

