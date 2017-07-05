package gamemodel.actionSpace;

import java.io.Serializable;
import java.util.*;

import gamemodel.Action;
import gamemodel.FamilyMember;
import gamemodel.command.GameException;
import gamemodel.effects.*;
import gamemodel.permanenteffect.*;

public class ActionSpace implements Serializable {

	private static final long serialVersionUID = 1L;
	private final int id;
	private boolean free=true;
	private final int actionCost;
	private List<IstantEffect> effects=new ArrayList<>();
	private ActionSpaceType type;
	private FamilyMember familyMember;
	
	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}

	public ActionSpace(int id,int actionCost, List<IstantEffect> effects,ActionSpaceType type) {
		this.id=id;
		this.actionCost = actionCost;
		this.effects = effects;
		this.type = type;
	}

	public ActionSpace(int id,int actionCost, IstantEffect effect, ActionSpaceType type) {
		this.id=id;
		this.actionCost = actionCost;
		this.effects.add(effect);
		this.type = type;
	}
	
	public boolean isAccessible(Action action)
	{
		for(PermanentEffect pEffect : action.getPlayer().getPEffects(PEffect.NO_ACTION_SPACE))
			if(((NoActionSpace) pEffect).getAType()==this.type)
				return false;
		
		if(!action.getPlayer().getPEffects(PEffect.NO_MATTER_IF_OCCUPIED).isEmpty())
			return true;
		
		if(this.free==false)
			return false;
		
		return true;
	}

	
	public void activateEffect(FamilyMember f) throws GameException{
		if(effects!=null)
		for(IstantEffect e:effects)
			e.activate(f.getPlayer());
	}
	
	
	public void rollbackEffect(FamilyMember f){
		if(effects!=null)
		for(IstantEffect e:effects)
			((EffectRollBack) e).rollBack(f.getPlayer());
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
	

	public List<IstantEffect> getEffects() {
		return effects;
	}

	
	public void setType(ActionSpaceType type) {
		this.type = type;
	}

	
	public ActionSpaceType getType() {
		return type;
	}

	public FamilyMember getFamilyMember() {
		return familyMember;
	}

	public String toString() {
		String str = "";
		str += this.getId();
		str += "-> ";
		str += this.getType();
		str+=" ";
		if(this.getEffects().isEmpty())
			str +=this.getEffects();
		str+=", ";
		if(this.free)
			str+="free";
		else
			str+="occupy";
		str+="\n";
		return str;
	}

	
	public void prepareForNewRound() {
		this.free=true;
		familyMember=null;		
	}
	
	
}
