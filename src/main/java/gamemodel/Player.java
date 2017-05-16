package gamemodel;

public interface Player {

	void playRound();

	void vaticanReport();

	public Resource getResource();
	public Team getTeam();
	public void placeFamilyMember(int idSpaceAction,Color c,int servant) throws Exception;
	public void setFamilyMember(Color color,int actionPoint);
	public void subResources(Resource r);
	public void addResources(Resource r);
	void prepareForNewRound();
	void subPoint(Point point);
	void addPoint(Point point);
}
