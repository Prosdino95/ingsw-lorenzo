package gamemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gamemodel.command.*;


public class RealPlayer implements Player {
	private Resource resource;
	private Map<Color,FamilyMember> familyMembers;
	private Point point;
	private Command command;
	private Board board;
	private Team team;
	private List<Card> buildings=new ArrayList<>();
	private List<Card> territories=new ArrayList<>();	
	private List<Card> ventures=new ArrayList<>();
	private List<Card> characters=new ArrayList<>();
	
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
	
	@Override
	public void setFamilyMember(Color color,int actionPoint){
		FamilyMember f= familyMembers.get(color);
		f.setActionpoint(actionPoint);		
	}
	
	@Override
	 public void subResources(Resource r){ 
	    this.resource.subResources(r); 
	 } 
	
	 @Override
	public void addResources(Resource r){ 
		this.resource.addResources(r); 
		  } 
	
	@Override
	public boolean isEnoughtResource(Resource r){ 
		return this.resource.isEnought(r); 
		  }
	
	@Override
	public FamilyMember getFamilyMember(Color c){
		return familyMembers.get(c);
	}
	
	public void placeFamilyMember(int idSpaceAction,Color c,int servant) throws GameException{
		FamilyMember f= familyMembers.get(c);		
		command=PlaceFMCommandFactory.getSingleton().placeFMCommandFactory(board,idSpaceAction,f,servant);
		command.isLegal();
	}
	
	@Override
	public Team getTeam() {
		return team;
	}
	
	@Override
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
		point.addPoint(point);
		
	}

	@Override
	public void addPoint(Point point) {
		point.subPoint(point);
		
	}
	
	@Override
	public boolean isEnoughtPoint(Point point){ 
		return this.point.isEnought(point); 
		  }
	
	public int contCard(CardType type){
		if(type==CardType.BUILDINGS)
			return buildings.size();
		if(type==CardType.CHARACTERS)
			return characters.size();
		if(type==CardType.TERRITORIES)
			return territories.size();
		if(type==CardType.VENTURES)
			return ventures.size();
		return 0;
		
	}
}