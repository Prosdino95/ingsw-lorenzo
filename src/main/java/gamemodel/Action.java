package gamemodel;

import java.io.Serializable;

import gamemodel.actionSpace.ActionSpace;

/**
 * The Action object is a structure keeping track of a place family member
 * action: which player is doing the action, which family member gets placed,
 * the target action space and the number of servants.
 * 
 */
public class Action implements Serializable {
	
	private static final long serialVersionUID = 1L;
	Player player;
	ActionSpace spaceAction;
	FamilyMember fm;
	int servants;
	
	public Action(){
		super();
	}

	public Action(Player player, ActionSpace spaceAction, FamilyMember fm, int servants) {
		super();
		this.player = player;
		this.spaceAction = spaceAction;
		this.fm = fm;
		this.servants = servants;
	}
	public int getServants() {
		return servants;
	}
	public void setServants(int servants) {
		this.servants = servants;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public ActionSpace getActionSpace() {
		return spaceAction;
	}
	public void setSpaceAction(ActionSpace spaceAction) {
		this.spaceAction = spaceAction;
	}
	public FamilyMember getFm() {
		return fm;
	}
	public void setFm(FamilyMember fm) {
		this.fm = fm;
	}
	
	
}
