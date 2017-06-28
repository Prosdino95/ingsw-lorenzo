package gameview.gui.actionspacecontroll;

import java.util.List;

import gamemodel.Color;
import gamemodel.FamilyMember;
import gamemodel.Player;
import gamemodel.Team;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.MemoryActionSpace;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CouncilPlaceActionSpaceControll implements ActionSpaceControll {

	@FXML HBox familyMembersSpace;
	@FXML TextFlow asActionPoint;
	
	@Override
	public void initialize(ActionSpace as) {
		Integer actionCost=as.getActionCost();
		this.asActionPoint.getChildren().add(new Text(actionCost.toString()));
		MemoryActionSpace actionSpace=(MemoryActionSpace)as;
		List<FamilyMember> familyMembers= actionSpace.getFm();
		//test(familyMembers);
		Pane p;
		if(!familyMembers.isEmpty())
			for(FamilyMember fm:familyMembers){
				p=new Pane();
				MakeFM.makeFM(p,fm);
				familyMembersSpace.getChildren().add(p);
			}	
	}
	
/*
	private void test(List<FamilyMember> f){
		FamilyMember f1=new FamilyMember(new Player(null, null, Team.BLUE), Color.WHITE);
		FamilyMember f2=new FamilyMember(new Player(null, null, Team.GREEN), Color.BLACK);
		FamilyMember f3=new FamilyMember(new Player(null, null, Team.RED), Color.ORANGE);
		f.add(f1);
		f.add(f2);
		f.add(f3);	
	}*/

}
