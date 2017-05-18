package gamemodel;

import gamemodel.ActionSpace.RealActionSpace;

public interface Board {

	void setupRound();
	void addActionSpace(RealActionSpace a);
	RealActionSpace getActionSpaces(int id);
	void addPlayer(Player player);
	String toString();
	
}
