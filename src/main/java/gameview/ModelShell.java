package gameview;

import java.io.Serializable;
import java.util.List;

import gamemodel.Board;
import gamemodel.FamilyMember;
import gamemodel.Player;
import gamemodel.RealBoard;
import gamemodel.RealPlayer;
import gamemodel.actionSpace.ActionSpace;

public class ModelShell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;
	private Player player;

	public ModelShell(Board board2, Player player2) {
		// TODO Auto-generated constructor stub4
		board = board2;
		player = player2;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(RealBoard board) {
		this.board = board;
	}
	public Player getPlayer() {
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
