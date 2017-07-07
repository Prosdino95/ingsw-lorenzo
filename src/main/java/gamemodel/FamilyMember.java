package gamemodel;

import java.io.Serializable;

public class FamilyMember implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return player.getTeam() + "'s " + color + " family member";
	}

	private Player player;
	private int actionPoint=0;
	private Color color;
	private boolean used=false;
	
	public FamilyMember(Player player, Color color) {
		this.player = player;
		this.color = color;
	}

	public FamilyMember(Player player, Color color, Integer strength) {
		this.player = player;
		this.color = color;
		actionPoint = strength;
	}
	

	public FamilyMember clone() {
		FamilyMember fm = new FamilyMember(player, color);
		fm.setActionpoint(actionPoint);
		fm.setUse(this.used);
		return fm;
	}
	
	public void setUse(Boolean used){
		this.used=used;
	}
	
	public void use(){
		this.used=true;
	}


	public boolean isUsed(){
		return used;
	}
	
	public void prepareForNewRound(){
		this.used=false;
	}

	public Player getPlayer() {
		return player;
	}

	public int getActionpoint() {
		return actionPoint;
	}

	public void setActionpoint(int actionpoint) {
		this.actionPoint = actionpoint;
	}

	public Color getColor() {
		return color;
	}
	

}