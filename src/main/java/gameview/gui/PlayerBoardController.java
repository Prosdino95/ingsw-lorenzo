package gameview.gui;

import java.util.ArrayList;
import java.util.List;

import gamemodel.LeaderCard;
import gamemodel.Player;
import gamemodel.card.HarvesterAndBuildings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import reti.ClientRequest;

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
	@FXML Pane leader0;
	@FXML Pane leader1;
	@FXML Pane leader2;
	@FXML Pane leader3;

	List<Pane> territoryCardPaneList=new ArrayList<>();
	List<Pane> buildingCardPaneList=new ArrayList<>();
	List<Pane> leaderCardPaneList=new ArrayList<>();
	
	@FXML Pane currentRequestFlow;
	private RequestController rc;
	private GuiView gv;
	
	LeaderCardAction lcAction;

	public void initialize(RequestController rc, GuiView gv)
	{
		this.rc = rc;
		this.gv = gv;
		
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
		leaderCardPaneList.add(leader0);
		leaderCardPaneList.add(leader1);
		leaderCardPaneList.add(leader2);
		leaderCardPaneList.add(leader3);
	}
	
	public void update(Player player) 
	{
		if(player==null) return;
		for(Pane p:territoryCardPaneList)
			p.getChildren().clear();
		for(int c=0;c<player.getTerritories().size();c++)
		{
			HarvesterAndBuildings cc=player.getTerritories().get(c);
			GUICard gc=new GUICard(cc);
			Pane pane=gc.getPane();
			pane.setLayoutX(20);
			pane.setLayoutY(35);
			pane.setScaleX(1.5);
			pane.setScaleY(1.5);
			territoryCardPaneList.get(c).getChildren().add(gc.getPane());
		}
		for(Pane p:buildingCardPaneList)
			p.getChildren().clear();
		for(int c=0;c<player.getBuildings().size();c++)
		{
			HarvesterAndBuildings cc=player.getBuildings().get(c);
			GUICard gc=new GUICard(cc);
			Pane pane=gc.getPane();
			pane.setLayoutX(20);
			pane.setLayoutY(35);
			pane.setScaleX(1.5);
			pane.setScaleY(1.5);
			buildingCardPaneList.get(c).getChildren().add(gc.getPane());
		}
		
		int c;
		for(c=0;c< leaderCardPaneList.size(); c++)
		{	
			Pane lcPane = leaderCardPaneList.get(c);
			lcPane.getChildren().clear();
			
			if (c >= player.getLCList().size()) break;
			
			LeaderCard cc= player.getLCList().get(c);
			GuiLeaderCard gc = new GuiLeaderCard(cc);
			Pane pane=gc.getPane();
			lcPane.getChildren().add(gc.getPane());
		
			int d = c;
			pane.setOnMouseEntered(e -> {
				pane.toFront();
				pane.setScaleX(2.0);
				pane.setScaleY(2.0);
				if (d == 0) {
					pane.setTranslateX(50);					
				} else if (d == 3) {
					pane.setTranslateX(-50);
				}
				pane.setTranslateY(-80);
			});
			pane.setOnMouseExited(e -> {
				pane.setScaleX(1);
				pane.setScaleY(1);
				pane.setTranslateX(0);
				pane.setTranslateY(0);
				
			});
			
			pane.setOnMouseClicked(e -> {
				ClientRequest cr = this.getNextRequest(cc);
				rc.setCr(cr);
				gv.updateGui();
			});			
			showCurrentRequest();
		}

	}
	
	
	public void showCurrentRequest() {
		Text t = new Text(rc.getCr().toString());
		List<Node> children = currentRequestFlow.getChildren();
		while (!children.isEmpty()) children.remove(0);
		this.currentRequestFlow.getChildren().add(t);
	}
	
	public ClientRequest getNextRequest(LeaderCard cc) {
		ClientRequest cr;
		List<LeaderCardAction> lst = cc.getPossibleActions();
		if (lst.isEmpty()) 
			return new ClientRequest();
		Integer index = lst.indexOf(lcAction);
		Integer size = lst.size();
		Integer nextIndex = index + 1;
		if (nextIndex == size) nextIndex = 0;
		
		
		if (lcAction == null) {
			lcAction = lst.get(0);
		} else {
			lcAction = lst.get(nextIndex);
		}
		
		cr = new ClientRequest(cc, lcAction);
		return cr;
		
	}

	
}
