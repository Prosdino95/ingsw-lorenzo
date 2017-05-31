package gamemodel;

import java.util.List;

import gamemodel.card.Card;
import gamemodel.command.GameException;

public interface Player {

	void playRound();

	void vaticanReport();

	public Resource getResource();
	public Point getPoint();
	public Team getTeam();
	public void placeFamilyMember(int idSpaceAction,Color c,int servant) throws GameException;
	public void setFamilyMember(Color color,int actionPoint);
	public void subResources(Resource r);
	public void addResources(Resource r);
	public void prepareForNewRound();
	public void subPoint(Point point);
	public void addPoint(Point point);
	public FamilyMember getFamilyMember(Color c);
	public List<FamilyMember> getFamilyMembers();
	boolean isEnoughtPoint(Point p);
	boolean isEnoughtResource(Resource r);
	int contCard(CardType type);
	public List<Card> getBuildings();
	public List<Card> getTerritories();
	public List<Card> getVentures();
	public List<Card> getCharacters();
}
