package gameview.gui.actionspacecontroll;


import gamemodel.FamilyMember;
import gamemodel.actionSpace.ActionSpace;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NormalActionSpaceControll implements ActionSpaceControll {
	
	@FXML TextFlow actionCost;
	@FXML TextFlow effect;
	@FXML Pane familyMember;
	FamilyMember fm;
	
	@Override
	public void initialize(ActionSpace as){
		//TODO effect
		fm=as.getFamilyMember();
		//fm=new FamilyMember(new Player(null, null, Team.RED), Color.UNCOLORED);
		if(fm!=null && familyMember.getChildren().size()<=1){
			Pane p=new Pane();
			p.setLayoutX(33);
			p.setLayoutY(33);
			MakeFM.makeFM(p, fm);
			familyMember.getChildren().add(p);
		}
		Integer actionCost=as.getActionCost();
		this.actionCost.getChildren().add(new Text(actionCost.toString()));
		//this.effect.getChildren().add(new Text(as.getEffect()));
	}

}
