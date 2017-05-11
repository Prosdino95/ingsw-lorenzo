package gameModel;

import java.util.*;

public class ActionSpace {
	private final int id=0;
	private static int identifier=0;
	private boolean free;
	private int actionCost;
	private List<Effect> effects;

	public ActionSpace(boolean free, int actionCost, List<Effect> effects) {
		this.id=identifier;
		identifier++;
		this.free = free;
		this.actionCost = actionCost;
		this.effects = effects;
	}
	
	public ActionSpace(boolean free, int actionCost, Effect effects) {
		this.free = free;
		this.actionCost = actionCost;
		//this.effects = effects;
	}
	
	public void ActivateEffect(FamilyMembers f){
		for(Effect e:effects)
			e.activate(f.getPlayer());
	}
	
	
	

}
