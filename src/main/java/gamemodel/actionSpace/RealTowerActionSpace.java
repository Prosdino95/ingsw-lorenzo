package gamemodel.actionSpace;

import java.io.Serializable;

import java.util.List;

import gamemodel.FamilyMember;
import gamemodel.Tower;
import gamemodel.card.Card;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;

public class RealTowerActionSpace extends RealActionSpace implements TowerActionSpace,Serializable {

	private static final long serialVersionUID = 1L;
	private Tower tower;
	private Card card;

	public Card getCard() {
		return card;
	}

	public RealTowerActionSpace(int actionCost, List<IstantEffect> effects, Tower tower,ActionSpaceType type) {
		super(actionCost, effects,type);
		this.tower = tower;
	}
	
	public RealTowerActionSpace(int actionCost, IstantEffect effects, Tower tower,ActionSpaceType type) {
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
		str += this.getTower();
		str += " ";
		if(this.getEffects()!=null)
			str +=this.getEffects() +"\n";
		if(!(card==null)){	
			str += card.toString();
		}	
		return str;
	}
		
	@Override
	public void giveCard(FamilyMember f) throws GameException {
		f.getPlayer().giveCard(this.card);
		
	}
	
	/*@Override
	public String toString() {
		return "RealTowerActionSpace [tower=" + tower + ", card=" + card + "]";
	}*/

	@Override
	public void activateEffect(FamilyMember f) throws GameException 
	{
		if(f.getPlayer().getPEffects("NO_BONUS").isEmpty())
			super.activateEffect(f);
	}
}
