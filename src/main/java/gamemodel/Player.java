package gamemodel;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.CharactersCard;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.card.VentureCard;
import gamemodel.command.*;
import gamemodel.permanenteffect.Debuff;
import gamemodel.permanenteffect.*;
import gamemodel.permanenteffect.StrengthModifyAndDiscount;
import server.GameQuestion;


public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private Team team;
	private Resource resource;
	private Point point;
	private List<HarvesterAndBuildings> buildings=new ArrayList<>();
	private List<HarvesterAndBuildings> territories=new ArrayList<>();	
	private List<VentureCard> ventures=new ArrayList<>();
	private List<CharactersCard> characters=new ArrayList<>();
	private Map<Color,FamilyMember> familyMembers;
	private Board board;	
	private transient List<PermanentEffect> permanentEffects = new ArrayList<>();	
	private transient Action currentAction = new Action();
	private transient Model model;
	private transient Player currentPlayer;
	
	public Player(Resource resource, Board board,Team team) 
	{
		this.team=team;
		this.resource = resource;
		this.board = board;
		this.point=new Point(0,0,0);
		generateFamilyMember();
	}
	
	public Player(Resource resource, Board board,Team team,Model model){
		this.model=model;
		this.team=team;
		this.resource = resource;
		this.board = board;
		this.point=new Point(0,0,0);
		generateFamilyMember();
	}

	private void generateFamilyMember() {
		familyMembers=new HashMap<>();
		familyMembers.put(Color.BLACK,new FamilyMember(this,Color.BLACK));
		familyMembers.put(Color.WHITE,new FamilyMember(this,Color.WHITE));
		familyMembers.put(Color.ORANGE,new FamilyMember(this,Color.ORANGE));
		familyMembers.put(Color.UNCOLORED,new FamilyMember(this,Color.UNCOLORED));		
	}
	
	private void setFamilyMember(Color color,int actionPoint){
		FamilyMember f = familyMembers.get(color);
		f.setActionpoint(actionPoint);		
	}
	
	 public void subResources(Resource r) {
		if (r == null) return;  
	    this.resource.subResources(r); 
	 } 
	
	public void addResources(Resource r)
	{ 
		if(r==null) return;
		
		if(!r.isEnought(new Resource(0,0,0,0)))
			throw new RuntimeException();
		
		 for(PermanentEffect permanentEffect:this.getPEffects("DEBUFF_RESOURCE"))
		 {
			r.subResources(((Debuff)permanentEffect).getResources());
			r.normalize();
		 }	
		 this.resource.addResources(r);
	} 
	
	public boolean isEnoughtResource(Resource r){ 
		return this.resource.isEnought(r); 
	}
	
	public FamilyMember getFamilyMember(Color c){
		return familyMembers.get(c);
	}
	
	public void placeFamilyMember(Action action) throws GameException {
		System.out.println(this+" : "+currentPlayer);
		if(!this.equals(currentPlayer))
			throw new GameException(GameError.ERR_NOT_TURN);
		currentAction = action;
		increasePower();
		Command command;
		command=PlaceFMCommandFactory.getSingleton().placeFMCommandFactory(action);
		command.isLegal();
	}
	
	public Team getTeam() {
		return team;
	}
	
	public Resource getResource() {
		return resource;
	}

	public void playRound() {
		// TODO Auto-generated method stub
		
	}


	public void prepareForNewRound() {
		board.getDice().setFMActionPoints(familyMembers);
		for(PermanentEffect permanentEffect:this.getPEffects("FM")){
			((FamilyMemberModify)permanentEffect).modify(this.familyMembers);
		}
		
	}

	public void subPoint(Point point) {
		this.point.subPoint(point);
		
	}

	public void addPoint(Point point) {
		this.point.addPoint(point);
		for(PermanentEffect permanentEffect:this.getPEffects("DEBUFF_POINT"))
		{
			this.subPoint(((Debuff)permanentEffect).getPoints());
		}
		
	}
	
	public boolean isEnoughtPoint(Point point){ 
		return this.point.isEnought(point); 
		  }
	
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

	public List<FamilyMember> getFamilyMembers() {
		return  new ArrayList<>(familyMembers.values());
	}

	public Point getPoint() {
		return point;
	}
	

	public List<HarvesterAndBuildings> getBuildings() {
		return buildings;
	}

	public List<HarvesterAndBuildings> getTerritories() {
		return territories;
	}

	public List<VentureCard> getVentures() {
		return ventures;
	}

	public List<CharactersCard> getCharacters() {
		return characters;
	}

	public void giveCard(Card card) throws GameException {
		card.activeIstantEffect(this);
		if (card instanceof CharactersCard)
			for(PermanentEffect e : ((CharactersCard) card).getPermanentEffects())
				this.permanentEffects.add((PermanentEffect) e);
		
	}

	public PermanentEffect getPermanentEffect(String tag) {
		for(PermanentEffect e:this.permanentEffects)
			if(e.hasTag(tag))
				return e;
		return null;		
	}
	
	private void increasePower()
	{
		FamilyMember fm = currentAction.getFm().clone();
		for (PermanentEffect e : permanentEffects) 
		{
			if (e.hasTag("HALVE_SERVANTS"))
				fm.setActionpoint(fm.getActionpoint() + currentAction.getServants()/2);
		}
		fm.setActionpoint(fm.getActionpoint() + currentAction.getServants());
		currentAction.setFm(fm);
	
		//TODO enum....
//		List<ModForza> e = (ModForza) permanentEffects("MOD_FORZA");
		for (PermanentEffect e : permanentEffects) {
			if (e.hasTag("MOD_FORZA")) {
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
	
	public List<PermanentEffect> getPEffects(String tag)
	{
		List<PermanentEffect> temp=new ArrayList<>();
		for(PermanentEffect pEffect:this.permanentEffects)
			if(pEffect.hasTag(tag))
				temp.add(pEffect);
		return temp;
	}
	
	public boolean controlResourceAndPay(Card card)
	{
		Resource discount=new Resource(0,0,0,0);
		for (PermanentEffect e : permanentEffects) 
			if (e.hasTag("Discount")) 
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
		public void vaticanReport(int period,int requirement,int victoryPoints)
	{
		int a=0; //TODO implementare scelta utente
		if(this.point.getFaith()<requirement)
			this.permanentEffects.add(board.getExcommunicationCards()[period-1].getPermanentEffect());
		if(this.point.getFaith()>=requirement)
		{
			if(a==0)
				this.permanentEffects.add(board.getExcommunicationCards()[period-1].getPermanentEffect());
			if(a==1)
			{
				this.subPoint(new Point(0,this.point.getFaith(),0));
				this.addPoint(new Point(0,0,victoryPoints));
			}
		}
	}

		public String answerToQuestion(Question question) throws GameException {
			return model.answerToQuestion(question, this);
		}

		public void setCurrentPlayer(Player nextPlayer) {
			this.currentPlayer=nextPlayer;			
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((team == null) ? 0 : team.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Player other = (Player) obj;
			if (team != other.team)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Player [team=" + team + "]";
		}
		
		
		
		

}