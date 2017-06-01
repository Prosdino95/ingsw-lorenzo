package gamemodel.ActionSpace;

import java.util.*;


import gamemodel.FamilyMember;
import gamemodel.effects.*;

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
	public void rollbackEffect(FamilyMember f){
		for(Effect e:effects)
			((EffectRollBack) e).rollBack(f.getPlayer());
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
	

	public List<Effect> getEffects() {
		return effects;
	}

	@Override
	public void setType(ActionSpaceType type) {
		this.type = type;
	}

	@Override
	public ActionSpaceType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "RealActionSpace [id=" + id + ", free=" + free + ", actionCost=" + actionCost + ", type=" + type
				+ ", getEffects()=" + getEffects() + "]";
	}


//	public String toString() {
//		String str = "";
//		str += this.getId();
//		str += "-> ";
//		str += this.getType();
//		str+=" ";
//		if(this.getEffects()!=null)
//			str +=this.getEffects();
//		str+=", ";
//		if(this.free)
//			str+="free";
//		else
//			str+="occupy";
//		str+="\n";
//		return str;
//	}
	
	
}
