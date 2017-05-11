package gameModel;

import java.util.*;

public class ActionSpace {
	private final int id;
	private static int identifier=0;
	private boolean free=true;
	private final int actionCost;
	private List<Effect> effects=new ArrayList<>();

	public ActionSpace(int actionCost, List<Effect> effects) {
		this.id=identifier;
		identifier++;
		this.actionCost = actionCost;
		this.effects = effects;
	}

	public ActionSpace(int actionCost, Effect effect) {
		this.id=identifier;
		identifier++;
		this.actionCost = actionCost;
		this.effects.add(effect);
	}
	
	public void ActivateEffect(FamilyMember f){
		System.out.println("attivato effetto di Spazio azione n "+id);
		for(Effect e:effects)
			e.activate(f.getPlayer());
	}
	
	public void occupy(){
		free=false;
	}

	public boolean isFree() {
		return free;
	}

	public int getActionCost() {
		return actionCost;
	}

	public int getId() {
		return id;
	}
}
