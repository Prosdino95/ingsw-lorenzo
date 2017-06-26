package gameview.gui;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Model;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.RealActionSpace;
import gamemodel.actionSpace.RealTowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.RealCard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class BoardController {
	List<Integer> asIdList;
	List<Pane> cardPaneList;
	List<Pane> asPaneList;
	private Model model;
	private GuiView guiView;
//	private Pane dialogPane;
	
	public void update(Model model) {
		for (Integer id : asIdList) {
//			System.out.println(id);
			RealActionSpace as = model.getBoard().getActionSpace(id);
			if (as == null) {
				continue;
			}
			if (as instanceof RealTowerActionSpace) {
				Card card = ((RealTowerActionSpace) as).getCard();
				GUICard gcard = new GUICard(card, cardPaneList.get(id).getPrefWidth(), cardPaneList.get(id).getPrefHeight());
				Pane cardPane = cardPaneList.get(id);
				if (!cardPane.getChildren().isEmpty()) cardPane.getChildren().remove(0);
				cardPane.getChildren().add(gcard);
			}
		}

	}
	
	public void initialize(Model m, GuiView v) throws IOException {
		
		 asIdList = new ArrayList<>();
		 cardPaneList = new ArrayList<>();
		 asPaneList = new ArrayList<>();
		 
//		 dialogPane.
		
		this.model = m;
		this.guiView = v;
		
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
		 
		 Pane asRoot;
		 NormalActionSpaceControll ascontroller;
		 //List<ActionSpace> asList=m.getBoard().getActionSpaces();
		 FXMLLoader loader;
		 
		 
		 for (Integer id : asIdList) {
			 	loader=new FXMLLoader();
			 	loader.setLocation(getClass().getResource("/NormalActionSpace.fxml"));	
			 	asRoot = loader.load();
			 	ascontroller=loader.getController();
			 	Pane cp = cardPaneList.get(id);
			 	Pane asp=asPaneList.get(id);
			 	ActionSpace actionSpace=m.getBoard().getActionSpace(id);
			 	ascontroller.initialize(actionSpace);
			 	asp.getChildren().add(asRoot);
			 	cp.setOnMouseClicked(e -> {
			 		RealActionSpace as = guiView.getModel().getBoard().getActionSpace(id);
			 		if (as instanceof RealTowerActionSpace) {
			 			((RealTowerActionSpace) as).attachDevelopmentCard(null);
			 		}
//				 System.out.println("You clicked on " + id);
				 this.guiView.updateModelAndGui(this.model);
			 });
		 }
	}
		

	@FXML Pane asProduzione;
	@FXML Pane asMercato1;
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

}
