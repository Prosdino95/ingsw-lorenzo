package gamemodel;

import java.util.List;

import gamemodel.card.Card;
import gamemodel.command.GameException;
import gamemodel.permanenteffect.PermanentEffect;

public interface Player {

	void playRound();

	void vaticanReport();

	public Resource getResource();
	public Point getPoint();
	public Team getTeam();
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
	public boolean controlResourceAndPay(Card card);
	void placeFamilyMember(Action action) throws GameException;
	public List<PermanentEffect> getPEffects(String tag);
}
