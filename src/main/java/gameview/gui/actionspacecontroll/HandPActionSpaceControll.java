package gameview.gui.actionspacecontroll;

import java.util.List;

import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.player.FamilyMember;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The HandPActionSpaceControll object contains all the personalization we let players do about harvester and
 * building action spaces,like the action value. This class also contains the logic to show family
 * members in harvester and building action spaces
 * 
 */




public class HandPActionSpaceControll implements ActionSpaceControll {
	@FXML TextFlow asActionPoint;
	@FXML TextFlow asActionPoint2;
	@FXML HBox bigSpace;
	@FXML Pane littleSpace;
	private Pane familyMember;
	
	@Override
	public void initialize(ActionSpace as){
		MemoryActionSpace actionSpace=(MemoryActionSpace)as;
		List<FamilyMember> familyMembers= actionSpace.getFm();
		Integer actionCost=as.getActionCost();
		this.asActionPoint.getChildren().add(new Text(actionCost.toString()));
		this.asActionPoint2.getChildren().add(new Text(actionCost.toString()));
		if(!familyMembers.isEmpty())
			for(FamilyMember fm:familyMembers)
				if(littleSpace.getChildren().size()<=1){
					familyMember=new Pane();
					familyMember.setLayoutX(42);
					familyMember.setLayoutY(33.5);
					MakeFM.makeFM(familyMember,fm);
					littleSpace.getChildren().add(familyMember);	
				}
					
				else{
						familyMember=new Pane();
						MakeFM.makeFM(familyMember,fm);
						bigSpace.getChildren().add(familyMember);
				}
	}
}
