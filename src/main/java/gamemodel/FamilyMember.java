package gamemodel;

public class FamilyMember {
	private RealPlayer player;
	private int actionPoint=0;
	private Color color;
	private boolean used=false;
	
	public FamilyMember(RealPlayer player, Color color) {
		this.player = player;
		this.color = color;
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

	public RealPlayer getPlayer() {
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