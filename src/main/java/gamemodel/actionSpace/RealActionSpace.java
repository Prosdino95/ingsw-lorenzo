package gamemodel.actionSpace;

import java.util.*;

import gamemodel.Action;
import gamemodel.FamilyMember;
import gamemodel.command.GameException;
import gamemodel.effects.*;
import gamemodel.permanenteffect.*;

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
	
	public boolean isAccessible(Action action)
	{
		for(PermanentEffect pEffect : action.getPlayer().getPEffects("NO_ACTION_SPACE"))
			if(((NoActionSpace) pEffect).getAType()==this.type)
				return false;
		
		if(!action.getPlayer().getPEffects("NO_MATTER_IF_OCCUPIED").isEmpty())
			return true;
		
		if(this.free==false)
			return false;
		
		return true;
	}

	@Override
	public void activateEffect(FamilyMember f) throws GameException{
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


	public String toString() {
		String str = "";
		str += this.getId();
		str += "-> ";
		str += this.getType();
		str+=" ";
		if(this.getEffects()!=null)
			str +=this.getEffects();
		str+=", ";
		if(this.free)
			str+="free";
		else
			str+="occupy";
		str+="\n";
		return str;
	}
	
	
}
