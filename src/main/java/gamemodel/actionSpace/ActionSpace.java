package gamemodel.actionSpace;

import gamemodel.Action;
import gamemodel.FamilyMember;
import gamemodel.command.GameException;

public interface ActionSpace {

	int getId();

	int getActionCost();

	boolean isFree();

	void occupy();

	void activateEffect(FamilyMember f) throws GameException;

	void setType(ActionSpaceType type);
	
	ActionSpaceType getType();

	void rollbackEffect(FamilyMember f);
	
	public boolean isAccessible(Action a);
}
