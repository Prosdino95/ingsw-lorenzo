package gameview.gui.actionspacecontroll;

import gamemodel.FamilyMember;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class MakeFM {
	
	static void makeFM(Pane p,FamilyMember fm){
			Circle colorFM,colorP;
			colorFM =new Circle(7);
			colorP=new Circle(20);		
			colorFM.setFill(familyMemberColor(fm));
			colorP.setFill(palyerColor(fm));
			p.getChildren().add(colorP);
			p.getChildren().add(colorFM);			
	}
	
	private static Color palyerColor(FamilyMember fm){
		switch(fm.getPlayer().getTeam()){
		case BLUE: return Color.BLUE;
		case GREEN: return Color.GREEN;
		case RED:	return Color.RED;
		case YELLOW: return Color.YELLOW;
		default: return Color.FUCHSIA;
		}
	}
	
	private static Color familyMemberColor(FamilyMember fm){
		switch(fm.getColor()){
		case BLACK: return Color.BLACK;
		case ORANGE: return Color.ORANGE;
		case UNCOLORED: return Color.GRAY;
		case WHITE: return Color.WHITE;
		default: return Color.FUCHSIA;		
		}
	}
	

}
