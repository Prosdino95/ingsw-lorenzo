package gameModel;

public class FamilyMember {
	Player player;
	int actionpoint=0;
	Color color;
	
	public FamilyMember(Player player, Color color) {
		this.player = player;
		this.color = color;
	}

	public Player getPlayer() {
		return player;
	}

	public int getActionpoint() {
		return actionpoint;
	}

	public void setActionpoint(int actionpoint) {
		this.actionpoint = actionpoint;
	}
}