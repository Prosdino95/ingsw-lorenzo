package gameModel;

import java.util.List;

public class TowerActionSpace extends ActionSpace {

	Tower tower;

	public TowerActionSpace(int actionCost, List<Effect> effects, Tower tower) {
		super(actionCost, effects);
		this.tower = tower;
	}
	
	public TowerActionSpace(int actionCost, Effect effects, Tower tower) {
		super(actionCost, effects);
		this.tower = tower;
	}

	public Tower getTower() {
		return tower;
	}	
}
