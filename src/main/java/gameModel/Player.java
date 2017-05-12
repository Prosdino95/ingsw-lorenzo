package gameModel;

public interface Player {

	void playRound();

	void vaticanReport();

	public Resource getResource();
	public Team getTeam();
	public void placeFamilyMemberInTower(int idSpaceAction,Color c,int servant);
	public void placeFamilyMemberInSpaceActions(int idSpaceAction,Color c,int servant);
	public void setFamilyMember(Color color,int actionPoint);
	public void subResources(Resource r);
	public void addResources(Resource r);
}
