package gamemodel;

import java.io.Serializable;
import java.util.List;

import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.RealActionSpace;

public interface Board extends Serializable{

	void setupRound();
	void addActionSpace(RealActionSpace a);
	RealActionSpace getActionSpace(int id);
	String toString();
	List<ActionSpace> getActionSpaces();
	Dice getDice();
	
}
