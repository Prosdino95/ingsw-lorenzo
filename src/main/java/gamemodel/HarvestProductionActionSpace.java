package gamemodel;

import java.util.ArrayList;
import java.util.List;

public class HarvestProductionActionSpace extends RealActionSpace implements ActionSpace {
	
	private List<RealPlayer> players=new ArrayList<RealPlayer>();

	public HarvestProductionActionSpace(int actionCost, Effect effect, ActionSpaceType type) {
		super(actionCost, effect, type);
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
}
