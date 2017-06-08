package gamemodel.actionSpace;

import java.io.Serializable;


import java.util.ArrayList;

import java.util.List;

import gamemodel.FamilyMember;
import gamemodel.Player;
import gamemodel.effects.IstantEffect;

public class MemoryActionSpace extends RealActionSpace implements ActionSpace,Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Player> players=new ArrayList<Player>();

	public MemoryActionSpace(int id,int actionCost, IstantEffect effect, ActionSpaceType type) {
		super(id,actionCost, effect, type);
	}
	
	public MemoryActionSpace(int id,int actionCost, List<IstantEffect> effect, ActionSpaceType type) {
		super(id,actionCost, effect, type);
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public boolean controlPlayer(FamilyMember f) {
		for(Player p:players)
			if(p.equals(f.getPlayer()))
				return false;
		return true;
	}

	public void addPlayer(FamilyMember f){
		players.add(f.getPlayer());
	}

	/*@Override
	public String toString() {
		return "MemoryActionSpace [players=" + players + ", getPlayers()=" + getPlayers() + ", getEffects()="
				+ getEffects() + "]";
	}*/

	public String toString() {
		String str = "";
		str += this.getId();
		str += "-> ";
		str += this.getType();
		str+=" ";
		if(this.getEffects()!=null)
			str +=this.getEffects();
		if(!this.players.isEmpty()){
			str+=", players:[";
			for(Player p:players)
			str +=p.getTeam()+" ";
			str+="] ";
		}	
		str+="\n";
		return str;
	}
	
	
}
