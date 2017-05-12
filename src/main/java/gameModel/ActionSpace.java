package gameModel;

public interface ActionSpace {

	int getId();

	int getActionCost();

	boolean isFree();

	void occupy();

	void activateEffect(FamilyMember f);

}
