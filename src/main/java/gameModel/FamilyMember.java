package gameModel;

public class FamilyMember {
	RealPlayer player;
	int actionpoint=0;
	Color color;
	
	public FamilyMember(RealPlayer player, Color color) {
		this.player = player;
		this.color = color;
	}

	public RealPlayer getPlayer() {
		return player;
	}

	public int getActionpoint() {
		return actionpoint;
	}

	public void setActionpoint(int actionpoint) {
		this.actionpoint = actionpoint;
	}
}