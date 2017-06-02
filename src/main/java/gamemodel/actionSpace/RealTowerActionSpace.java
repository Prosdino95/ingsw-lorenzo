package gamemodel.actionSpace;

import java.util.List;

import gamemodel.FamilyMember;
import gamemodel.Tower;
import gamemodel.card.Card;
import gamemodel.command.GameException;
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
		str += "tower";
		str +=this.getTower();
		str += " ";
		if(this.getEffects()!=null)
			str +=this.getEffects()+"\n";	
		if(!(card==null)){	
			str += card.toString();
		}	
		str+="\n";
		return str;
	}

	@Override
	public void giveCard(FamilyMember f) throws GameException {
		f.getPlayer().giveCard(this.card);
		
	}
	
	@Override
	public void activateEffect(FamilyMember f) throws GameException 
	{
		if(f.getPlayer().getPEffects("NO_BONUS").isEmpty())
			super.activateEffect(f);
	}
}
