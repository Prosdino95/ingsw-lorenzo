package gameview.gui;

import gamemodel.actionSpace.ActionSpace;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NormalActionSpaceControll {
	
	@FXML TextFlow actionCost;
	@FXML TextFlow effect;
	
	public void initialize(ActionSpace as){
		//TODO effect
		Integer actionCost=as.getActionCost();
		this.actionCost.getChildren().add(new Text(actionCost.toString()));
		//this.effect.getChildren().add(new Text(as.getEffect()));
	}

}
