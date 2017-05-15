package gameModel;

import java.util.List;

public class RealTowerActionSpace extends RealActionSpace implements TowerActionSpace {

	private Tower tower;
	private Card card;

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
		this.card = card;
	}
	
	public String toString() {
		String str = "";
		str += this.getId();
		str += "-> ";
		str += card.toString();
		return str;
	}
}
