package gamemodel.actionSpace;

import java.io.Serializable;

import java.util.List;

import gamemodel.FamilyMember;
import gamemodel.Tower;
import gamemodel.card.Card;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PEffect;

public class TowerActionSpace extends ActionSpace implements Serializable {

	private static final long serialVersionUID = 1L;
	private Tower tower;
	private Card card;

	public Card getCard() {
		return card;
	}

	public TowerActionSpace(int id,int actionCost, List<IstantEffect> effects, Tower tower,ActionSpaceType type) {
		super(id,actionCost, effects,type);
		this.tower = tower;
	}
	
	public TowerActionSpace(int id,int actionCost, IstantEffect effects, Tower tower,ActionSpaceType type) {
		super(id,actionCost, effects,type);
		this.tower = tower;
	}

	
	public Tower getTower() {
		return tower;
	}

		
	public void attachDevelopmentCard(Card card) {
		this.card = card;
	}
	
	public String toString() {
		String str = "\n";
		str += "- Floor " + this.getId() % 4 + " in " + tower.getType() + " tower action space\n";
		str += "- Action cost -> " + this.getActionCost();
		if(!this.getEffects().isEmpty()) {
			str += "\n";
			str += "- Effects:";
			for (IstantEffect e : this.getEffects()) {
				str += "\n";
				str += "--" + e;
			}
		}
		if(!(card==null)){
			str += "\n";
			str += "- Holding card\n" + card.toString();
		}
		return str;
	}
	
	public String toGui(){
		String str = "\n";
		str += "- Floor " + this.getId() % 4 + " in " + tower.getType() + " tower action space\n";
		str += "- Action cost -> " + this.getActionCost();
		if(!this.getEffects().isEmpty()) {
			str += "\n";
			str += "- Effects:";
			for (IstantEffect e : this.getEffects()) {
				str += "\n";
				str += "--" + e;
			}
		}
		return str;
	}
			
	public void giveCard(FamilyMember f) throws GameException {
		f.getPlayer().giveCard(this.card);
		this.card=null;
	}

	@Override
	public void activateEffect(FamilyMember f) throws GameException 
	{
		if(f.getPlayer().getPEffects(PEffect.NO_BONUS).isEmpty())
			super.activateEffect(f);
	}
}
