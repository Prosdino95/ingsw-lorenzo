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
	public void prepareForNewRound();
	public void subPoint(Point point);
	public void addPoint(Point point);
	public FamilyMember getFamilyMember(Color c);
}
