package gamemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.Card;
import gamemodel.command.*;
import gamemodel.effects.Effect;


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
	private List<PermanentEffect> permanentEffects=new ArrayList<>();
	private Action currentAction = new Action();
	
	public RealPlayer(Resource resource, Board board,Team team) {
		this.team=team;
		this.resource = resource;
		this.board = board;
		this.point=new Point(0,7,0);  //TODO risettare a (0,0,0)
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
	
	public void placeFamilyMember(Action action) throws GameException {
		currentAction = action;
		increasePower();
		command=PlaceFMCommandFactory.getSingleton().
				placeFMCommandFactory(action);
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
		this.point.subPoint(point);
		
	}

	@Override
	public void addPoint(Point point) {
		this.point.addPoint(point);
		
	}
	
	@Override
	public boolean isEnoughtPoint(Point point){ 
		return this.point.isEnought(point); 
		  }
	
	@Override
	public int contCard(CardType type)
	{
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

	@Override
	public List<FamilyMember> getFamilyMembers() {
		return  new ArrayList<>(familyMembers.values());
	}

	public Point getPoint() {
		return point;
	}

	public List<Card> getBuildings() {
		return buildings;
	}

	public List<Card> getTerritories() {
		return territories;
	}

	public List<Card> getVentures() {
		return ventures;
	}

	public List<Card> getCharacters() {
		return characters;
	}

	public void giveCard(Card card) {
		card.activeIstantEffect(this);
		for(Effect e:card.getPermanentEffects())
			if(e instanceof PermanentEffect)				
				this.permanentEffects.add((PermanentEffect) e);
		
	}

	public PermanentEffect getPermanentEffect(String tag) {
		for(PermanentEffect e:this.permanentEffects)
			if(e.tag.equals(tag))
				return e;
		return null;		
	}
	
	private void increasePower()
	{
		FamilyMember fm = currentAction.getFm().clone();
		fm.setActionpoint(fm.getActionpoint() + currentAction.getServants());
		currentAction.setFm(fm);
	
		//TODO enum....
//		List<ModForza> e = (ModForza) permanentEffects("MOD_FORZA");
		for (PermanentEffect e : permanentEffects) {
			if (e.tag.equals("MOD_FORZA")) {
				StrengthModifyAndDiscount mf = (StrengthModifyAndDiscount) e;
				if (mf.getAtype() == ActionSpaceType.TOWER && 
						currentAction.getActionSpace().getType() == ActionSpaceType.TOWER) {
					if (mf.getCtype() == ((TowerActionSpace) currentAction.getActionSpace()).getTower().getType()) {
						fm.setActionpoint(fm.getActionpoint() + mf.getModForza());
						currentAction.setFm(fm);
					}
				} else if (mf.getAtype() == ActionSpaceType.HARVEST &&
						currentAction.getActionSpace().getType() == ActionSpaceType.HARVEST) {
					fm.setActionpoint(fm.getActionpoint() + mf.getModForza());
					currentAction.setFm(fm);
				} else if (mf.getAtype() == ActionSpaceType.PRODUCTION &&
						currentAction.getActionSpace().getType() == ActionSpaceType.PRODUCTION) {
					fm.setActionpoint(fm.getActionpoint() + mf.getModForza());
					currentAction.setFm(fm);
				} 
			}
		}
	}
	
	
	
	
	
	
	

	@Override
	public boolean controlResourceAndPay(Card card)
	{
		Resource discount=new Resource(0,0,0,0);
		for (PermanentEffect e : permanentEffects) 
			if (e.tag.equals("Discount")) 
			{
				StrengthModifyAndDiscount mf = (StrengthModifyAndDiscount) e;
				if (mf.getCtype() == ((TowerActionSpace) currentAction.getActionSpace()).getTower().getType()) 
					discount.addResources(mf.getDiscount());
			}
		if(card.controlResource(this, discount)){
			card.pay(this, discount);
			return true;
		}
			
		else return false;			
	}
	
	
	
	
	
	
	
	
	
	
	
}