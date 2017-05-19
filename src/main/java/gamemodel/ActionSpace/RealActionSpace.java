package gamemodel.ActionSpace;

import java.util.*;

import gamemodel.Effect;
import gamemodel.FamilyMember;

public class RealActionSpace implements ActionSpace {
	private final int id;
	private static int identifier=0;
	private boolean free=true;
	private final int actionCost;
	private List<Effect> effects=new ArrayList<>();
	private ActionSpaceType type;

	public RealActionSpace(int actionCost, List<Effect> effects,ActionSpaceType type) {
		this.id=identifier;
		identifier++;
		this.actionCost = actionCost;
		this.effects = effects;
		this.type = type;
	}

	public RealActionSpace(int actionCost, Effect effect, ActionSpaceType type) {
		this.id=identifier;
		identifier++;
		this.actionCost = actionCost;
		this.effects.add(effect);
		this.type = type;
	}

	@Override
	public void activateEffect(FamilyMember f){
		for(Effect e:effects)
			e.activate(f.getPlayer());
	}
	
	@Override
	public void occupy(){
		free=false;
	}

	@Override
	public boolean isFree() {
		return free;
	}

	@Override
	public int getActionCost() {
		return actionCost;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setType(ActionSpaceType type) {
		this.type = type;
	}

	@Override
	public ActionSpaceType getType() {
		return type;
	}
}
