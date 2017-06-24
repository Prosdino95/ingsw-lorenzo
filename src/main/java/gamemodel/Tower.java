package gamemodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.card.CardType;
import gamemodel.permanenteffect.PEffect;

public class Tower implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean towerFree=true;
	private CardType type;
	private List<Player> playersInTower=new ArrayList<Player>();

	public Tower() {
	}
	
	public Tower(CardType type) {
		this.type = type;
	}
	
	public boolean isFree(FamilyMember f)
	{
		if(towerFree)
			return true;
		if(f.getPlayer().getPEffects(PEffect.NO_NEED_TO_PAY_3_COINS).isEmpty())
		{
			if(f.getPlayer().isEnoughtResource(new Resource(3,0,0,0)))
			{
				f.getPlayer().subResources(new Resource(3,0,0,0));
				return true;
			}
			else
				return false;		
		}
		return true;
	}
	
	public void occupyTower(){
		towerFree=false;
	}
	
	public CardType getType() {
		return type;
	}
	
	public void addPlayer(FamilyMember f){
		if(f.getColor()!=Color.UNCOLORED)
			playersInTower.add(f.getPlayer());
	}

	public boolean controlPlayer(FamilyMember f) {
		if(f.getColor()==Color.UNCOLORED)
			return true;
		for(Player p:playersInTower)
			if(p.equals(f.getPlayer()))
				return false;
		return true;
	}
	
	public void prepareForNewRound(){
		playersInTower.clear();
		towerFree=true;
	}

	@Override
	public String toString() {
		return " "+type;
	}
	
	
}
