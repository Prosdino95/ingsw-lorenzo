package gameview.gui.actionspacecontroll;

import gamemodel.FamilyMember;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MakeFM {
	
	public static void makeFM(Pane p,FamilyMember fm){
			Circle colorFM,colorP;
			colorFM =new Circle(7);
			colorP=new Circle(20);		
			colorFM.setFill(familyMemberColor(fm));
			colorP.setFill(palyerColor(fm));
			p.getChildren().add(colorP);
			p.getChildren().add(colorFM);			
	}
	
	public static void makeFM(Pane p,FamilyMember fm,int big,int small){
		Circle colorFM,colorP;
		colorFM =new Circle(small);
		colorP=new Circle(big);		
		colorFM.setFill(familyMemberColor(fm));
		colorP.setFill(palyerColor(fm));
		p.getChildren().add(colorP);
		p.getChildren().add(colorFM);			
}
	
	private static Color palyerColor(FamilyMember fm){
		switch(fm.getPlayer().getTeam()){
		case BLUE: return Color.CORNFLOWERBLUE;
		case GREEN: return Color.DARKSEAGREEN;
		case RED:	return Color.INDIANRED;
		case YELLOW: return Color.YELLOW;
		default: return Color.FUCHSIA;
		}
	}
	
	private static Color familyMemberColor(FamilyMember fm){
		switch(fm.getColor()){
		case BLACK: return Color.BLACK;
		case ORANGE: return Color.CORAL;
		case UNCOLORED: return Color.LIGHTGREY;
		case WHITE: return Color.WHITE;
		default: return Color.FUCHSIA;		
		}
	}
	

}
