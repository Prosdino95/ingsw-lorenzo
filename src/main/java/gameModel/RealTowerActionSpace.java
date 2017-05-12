package gameModel;

import java.util.List;

public class RealTowerActionSpace extends RealActionSpace implements TowerActionSpace {

	Tower tower;

	public RealTowerActionSpace(int actionCost, List<Effect> effects, Tower tower) {
		super(actionCost, effects);
		this.tower = tower;
	}
	
	public RealTowerActionSpace(int actionCost, Effect effects, Tower tower) {
		super(actionCost, effects);
		this.tower = tower;
	}

	@Override
	public Tower getTower() {
		return tower;
	}

	@Override	
	public void attachDevelopmentCard(Card card) {
		// TODO Auto-generated method stub
		
	}	
}
