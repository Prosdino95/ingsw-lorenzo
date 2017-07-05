package gamemodel.actionSpace;

import java.io.Serializable;
import java.util.List;

import gamemodel.Action;
import gamemodel.FamilyMember;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;

public interface ActionSpace extends Serializable{

	int getId();

	int getActionCost();

	boolean isFree();

	void occupy();

	void activateEffect(FamilyMember f) throws GameException;

	void setType(ActionSpaceType type);
	
	ActionSpaceType getType();

	void rollbackEffect(FamilyMember f);
	
	public boolean isAccessible(Action a);

	void prepareForNewRound();

	FamilyMember getFamilyMember();

	List<IstantEffect> getEffects();
}
