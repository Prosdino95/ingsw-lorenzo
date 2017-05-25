package gamemodel;

import java.util.List;

import gamemodel.ActionSpace.ActionSpace;
import gamemodel.ActionSpace.RealActionSpace;

public interface Board {

	void setupRound();
	void addActionSpace(RealActionSpace a);
	RealActionSpace getActionSpace(int id);
	void addPlayer(Player player);
	String toString();
	List<ActionSpace> getActionSpaces();
	
}
