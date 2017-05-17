package gamemodel;

import java.util.HashMap;
import java.util.Map;
import gamemodel.command.*;


public class RealPlayer implements Player {
	private Resource resource;
	private Map<Color,FamilyMember> familyMembers;
	private Command command;
	private Board board;
	private Team team;
	
	public RealPlayer(Resource resource, Board board,Team team) {
		this.team=team;
		this.resource = resource;
		this.board = board;
		generateFamilyMember();
	}

	private void generateFamilyMember() {
		familyMembers=new HashMap<>();
		familyMembers.put(Color.BLACK,new FamilyMember(this,Color.BLACK));
		familyMembers.put(Color.WHITE,new FamilyMember(this,Color.WHITE));
		familyMembers.put(Color.ORANGE,new FamilyMember(this,Color.ORANGE));
		familyMembers.put(Color.UNCOLORED,new FamilyMember(this,Color.UNCOLORED));		
	}
	
	public void setFamilyMember(Color color,int actionPoint){
		FamilyMember f= familyMembers.get(color);
		f.setActionpoint(actionPoint);		
	}
	
	 public void subResources(Resource r){ 
	    this.resource.subResources(r); 
	 } 
		   
	public void addResources(Resource r){ 
		this.resource.addResources(r); 
		  } 
	
	public boolean isEnoughtResource(Resource r){ 
		return this.resource.isEnought(r); 
		  }
	@Override
	public FamilyMember getFamilyMember(Color c){
		return familyMembers.get(c);
	}
	
	public void placeFamilyMember(int idSpaceAction,Color c,int servant) throws Exception{
		FamilyMember f= familyMembers.get(c);
		command=new PlaceFamilyMemberCommand(board,idSpaceAction,f,servant);
		command.isLegal();
	}

	public Team getTeam() {
		return team;
	}

	public Resource getResource() {
		return resource;
	}

	@Override
	public void playRound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vaticanReport() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareForNewRound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subPoint(Point point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPoint(Point point) {
		// TODO Auto-generated method stub
		
	}

}