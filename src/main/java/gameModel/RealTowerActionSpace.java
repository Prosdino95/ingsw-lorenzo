package gameModel;

import java.util.List;

public class RealTowerActionSpace extends RealActionSpace implements TowerActionSpace {

	private Tower tower;

	public RealTowerActionSpace(int actionCost, List<Effect> effects, Tower tower,ActionSpaceType type) {
		super(actionCost, effects,type);
		this.tower = tower;
	}
	
	public RealTowerActionSpace(int actionCost, Effect effects, Tower tower,ActionSpaceType type) {
		super(actionCost, effects,type);
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
