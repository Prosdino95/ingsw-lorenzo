package gameModel;

public class FamilyMembers {
	Player player;
	int actionpoint;
	Color color;
	
	public FamilyMembers(Player player, int actionpoint, Color color) {
		this.player = player;
		this.actionpoint = actionpoint;
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