package gamemodel;

import gamemodel.command.GameException;

public interface Player {

	void playRound();

	void vaticanReport();

	public Resource getResource();
	public Team getTeam();
	public void placeFamilyMember(int idSpaceAction,Color c,int servant) throws GameException;
	public void setFamilyMember(Color color,int actionPoint);
	public void subResources(Resource r);
	public void addResources(Resource r);
	public void prepareForNewRound();
	public void subPoint(Point point);
	public void addPoint(Point point);
	public FamilyMember getFamilyMember(Color c);
	boolean isEnoughtPoint(Point p);
	boolean isEnoughtResource(Resource r);
	int contCard(CardType type);
}
