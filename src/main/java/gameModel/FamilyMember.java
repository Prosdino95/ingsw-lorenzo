package gameModel;

public class FamilyMember {
	private RealPlayer player;
	private int actionPoint=0;
	private Color color;
	
	public FamilyMember(RealPlayer player, Color color) {
		this.player = player;
		this.color = color;
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