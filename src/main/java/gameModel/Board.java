package gameModel;

public interface Board {

	void setupRound();
	void addActionSpace(RealActionSpace a);
	RealActionSpace getActionSpaces(int id);
	
}
