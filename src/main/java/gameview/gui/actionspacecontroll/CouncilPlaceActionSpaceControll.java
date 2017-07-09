package gameview.gui.actionspacecontroll;

import java.util.List;

import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.effects.IstantEffect;
import gamemodel.player.FamilyMember;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The CouncilPlaceActionSpaceControll object contains all the personalization we let players do about council place
 * action space,like the action value and its immediate effect. This class also contains the logic to show family
 * members in council place 
 * 
 */


public class CouncilPlaceActionSpaceControll implements ActionSpaceControll {

	@FXML HBox familyMembersSpace;
	@FXML TextFlow asActionPoint,effect;
	
	@Override
	public void initialize(ActionSpace as) {
		Integer actionCost=as.getActionCost();
		asActionPoint.getChildren().add(new Text(actionCost.toString()));	
		MemoryActionSpace actionSpace=(MemoryActionSpace)as;
		String effectText="";
		for(IstantEffect e:actionSpace.getEffects())
			effectText+=e.toString();
		effect.getChildren().add(new Text(effectText));	
		List<FamilyMember> familyMembers= actionSpace.getFm();
		Pane p;
		if(!familyMembers.isEmpty())
			for(FamilyMember fm:familyMembers){
				p=new Pane();
				MakeFM.makeFM(p,fm);
				familyMembersSpace.getChildren().add(p);
			}
	}
}
