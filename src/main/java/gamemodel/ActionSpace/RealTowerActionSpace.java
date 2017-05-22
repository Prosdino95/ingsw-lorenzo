package gamemodel.ActionSpace;

import java.util.List;

import gamemodel.Card;
import gamemodel.FamilyMember;
import gamemodel.Tower;
import gamemodel.effects.Effect;

public class RealTowerActionSpace extends RealActionSpace implements TowerActionSpace {

	private Tower tower;
	private Card card;

	public Card getCard() {
		return card;
	}

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

	@Override
	public void giveCard(FamilyMember f) {
		// TODO Auto-generated method stub
		
	}
}
