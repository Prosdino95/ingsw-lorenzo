package gameview;

import java.io.Serializable;
import java.util.List;

import gamemodel.FamilyMember;
import gamemodel.RealBoard;
import gamemodel.RealPlayer;
import gamemodel.actionSpace.ActionSpace;

public class ModelShell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RealBoard board;
	private RealPlayer player;

	public RealBoard getBoard() {
		return board;
	}
	public void setBoard(RealBoard board) {
		this.board = board;
	}
	public RealPlayer getPlayer() {
		return player;
	}
	public void setPlayer(RealPlayer player) {
		this.player = player;
	}

	public List<ActionSpace> getActionSpaces() {
		return board.getActionSpaces();
	}
	
	public List<FamilyMember> getFamilyMembers() {
		return player.getFamilyMembers();
	}
}
