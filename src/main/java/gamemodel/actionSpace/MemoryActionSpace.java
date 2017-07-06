package gamemodel.actionSpace;

import java.io.Serializable;


import java.util.ArrayList;

import java.util.List;

import gamemodel.Color;
import gamemodel.FamilyMember;
import gamemodel.Player;
import gamemodel.effects.IstantEffect;

public class MemoryActionSpace extends ActionSpace implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Player> players=new ArrayList<>();
	private List<FamilyMember> fm=new ArrayList<>();

	public MemoryActionSpace(int id,int actionCost, IstantEffect effect, ActionSpaceType type) {
		super(id,actionCost, effect, type);
	}
	
	public MemoryActionSpace(int id,int actionCost, List<IstantEffect> effect, ActionSpaceType type) {
		super(id,actionCost, effect, type);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public List<FamilyMember> getFm() {
		return fm;
	}

	public boolean controlPlayer(FamilyMember f) {
		if(f.getColor()==Color.UNCOLORED)
			return true;
		for(Player p:players)
			if(p.equals(f.getPlayer()))
				return false;
		return true;
	}

	public void addPlayer(FamilyMember f){
		fm.add(f);
		if(f.getColor()!=Color.UNCOLORED || this.getType()==ActionSpaceType.COUNCIL_PALACE)
			players.add(f.getPlayer());			
	}
	
	@Override
	public void prepareForNewRound(){
		super.prepareForNewRound();
		this.fm.clear();
		this.players.clear();
	}

	@Override
	public String toString() {
		String str = "\n";

		str += "-" + this.getType() + " action space";
		if(!this.getEffects().isEmpty()) {
			str += "\n";
			str += "- Effects: \n";
			for (IstantEffect e : this.getEffects()) {
				str += "-- " + e + "\n";
			}
		}
		if(!this.fm.isEmpty()) {
			str+="- Occupied by " + this.fm;
		}
		str += "\n";

		return str;
	}
	
	
}
