package gameModel;

import java.util.HashMap;
import java.util.Map;


public class Player {
	private Resource resource;
	private Map<Color,FamilyMember> familyMembers;
	private Controller controller;
	private Team team;
	
	public Player(Resource resource, Controller controller,Team team) {
		this.team=team;
		this.resource = resource;
		this.controller = controller;
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
	
	public void printResources(){
		System.out.println(resource);
	}

	public void placeFamilyMember(int idSpaceAction,Color c,int servant){
		FamilyMember f= familyMembers.get(c);
		controller.placeFamilyMember(idSpaceAction,f,servant);
	}

	public Team getTeam() {
		return team;
	}

	public Resource getResource() {
		return resource;
	}
	
	
	
	
}