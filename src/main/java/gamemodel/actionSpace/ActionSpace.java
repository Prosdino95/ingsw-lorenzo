package gamemodel.actionSpace;

import java.io.Serializable;
import java.util.*;

import gamemodel.Action;
import gamemodel.FamilyMember;
import gamemodel.command.GameException;
import gamemodel.effects.*;
import gamemodel.permanenteffect.*;

public class RealActionSpace implements ActionSpace,Serializable {

	private static final long serialVersionUID = 1L;
	private final int id;
	private boolean free=true;
	private final int actionCost;
	private List<IstantEffect> effects=new ArrayList<>();
	private ActionSpaceType type;
	protected FamilyMember familyMember;

	public RealActionSpace(int id,int actionCost, List<IstantEffect> effects,ActionSpaceType type) {
		this.id=id;
		this.actionCost = actionCost;
		this.effects = effects;
		this.type = type;
	}

	public RealActionSpace(int id,int actionCost, IstantEffect effect, ActionSpaceType type) {
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

	@Override
	public void activateEffect(FamilyMember f) throws GameException{
		familyMember=f;
		if(effects!=null)
		for(IstantEffect e:effects)
			e.activate(f.getPlayer());
	}
	
	@Override
	public void rollbackEffect(FamilyMember f){
		if(effects!=null)
		for(IstantEffect e:effects)
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
	

	public List<IstantEffect> getEffects() {
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
	
	

	/*@Override
	public String toString() {
		return "RealActionSpace [id=" + id + ", free=" + free + ", actionCost=" + actionCost + ", type=" + type
				+ ", getEffects()=" + getEffects() + "]";
	}*/

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

	@Override
	public void prepareForNewRound() {
		this.free=true;
		
	}
	
	
}
