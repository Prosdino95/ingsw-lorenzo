package gamemodel;

import java.util.ArrayList;
import java.util.List;

public class Tower {
	private boolean towerFree=true;
	private CardType type;
	private List<RealPlayer> playersInTower=new ArrayList<RealPlayer>();

	public Tower() {
	}
	
	public Tower(CardType type) {
		this.type = type;
	}
	
	public boolean isFree(){
		return towerFree;
	}
	
	public void occupyTower(){
		towerFree=false;
	}
	
	public CardType getType() {
		return type;
	}
	
	public void addPlayer(FamilyMember f){
		playersInTower.add(f.getPlayer());
	}

	public boolean controlPlayer(FamilyMember f) {
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
