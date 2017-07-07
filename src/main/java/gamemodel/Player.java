package gamemodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.CharactersCard;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.card.VentureCard;
import gamemodel.command.Command;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.Debuff;
import gamemodel.permanenteffect.FamilyMemberModify;
import gamemodel.permanenteffect.PEffect;
import gamemodel.permanenteffect.PermanentEffect;
import gamemodel.permanenteffect.StrengthModifyAndDiscount;


public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private Team team;
	private Resource resource;
	private Point point;
	private List<HarvesterAndBuildings> buildings=new ArrayList<>();
	private List<HarvesterAndBuildings> territories=new ArrayList<>();	
	private List<VentureCard> ventures=new ArrayList<>();
	private List<CharactersCard> characters=new ArrayList<>();
	private Board board;	
	private transient List<PermanentEffect> permanentEffects = new ArrayList<>();	
	private transient Action currentAction = new Action();
	private Model model;
	private boolean death=false;
	private List<LeaderCard> leaderCards = new ArrayList<>();
	private List<FamilyMember> familyMembers; 
	private PersonalBonusTile personalBonusTile;
	private boolean alradyPlaceFM=false;
	private boolean alradyPlaceVatican=false;
	private transient Timer timer;
	
	
	public Player(Resource resource, Board board,Team team) 
	{
		this.team=team;
		this.resource = resource;
		this.board = board;
		this.point=new Point(0,0,0);
		generateFamilyMember();
		personalBonusTile=new PersonalBonusTile(new Resource(0,1,1,1),new Point(1,0,0),new Resource(2,0,0,0));
	}
	
	public Player(Resource resource, Board board,Team team,Model model){
		this.model=model;
		this.team=team;
		this.resource = resource;
		this.board = board;
		this.point=new Point(0,0,0);
		generateFamilyMember();
		personalBonusTile=new PersonalBonusTile(new Resource(0,1,1,1),new Point(1,0,0),new Resource(2,0,0,0));
	}

	public Player(Resource resource2, Point point) {
		resource = resource2;
		this.point = point;
		generateFamilyMember();
		personalBonusTile=new PersonalBonusTile(new Resource(0,1,1,1),new Point(1,0,0),new Resource(2,0,0,0));
	}

	private void generateFamilyMember() {
		familyMembers = new ArrayList<>();
		familyMembers.add(new FamilyMember(this,Color.BLACK));
		familyMembers.add(new FamilyMember(this,Color.WHITE));
		familyMembers.add(new FamilyMember(this,Color.ORANGE));
		familyMembers.add(new FamilyMember(this,Color.UNCOLORED));
	}
	
	public PersonalBonusTile getPersonalBonusTile() 
	{
		return personalBonusTile;
	}
	public Model getModel() {
		return model;
	}
	public void subResources(Resource r) {
		if (r == null) return;  
	    this.resource.subResources(r); 
	 } 
	
	public void addResources(Resource r)
	{ 
		if(r==null) return;
		
		if(!r.isEnought(new Resource(0,0,0,0)))
			throw new AssertionError();
		
		for(PermanentEffect permanentEffect:this.getPEffects(PEffect.DEBUFF_RESOURCE))
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
		if (c == Color.STRANGE) return new FamilyMember(null, null);
		for(FamilyMember fm:this.familyMembers)
			if(fm.getColor()==c){
				//System.out.println(fm);
				return familyMembers.get(familyMembers.indexOf(fm));
			}
		return null;
	}
	
	public void placeFamilyMember(Action action) throws GameException {
		//if(!this.equals(currentPlayer) && currentPlayer!=null )
		if(!this.equals(model.getCurrentPlayer()))
			throw new GameException(GameError.ERR_NOT_TURN);
		if(this.alradyPlaceFM)
			throw new GameException(GameError.ALREADY_PLACE_FM);
		currentAction = action;
		increasePower();
		Command command;
		command=model.getCommandFactory().placeFMCommandFactory(action);
		command.isLegal();
		alradyPlaceFM=true;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public Resource getResource() {
		return resource;
	}

	public void prepareForNewRound() {
		board.getDice().setFMActionPoints(familyMembers);
		for(FamilyMember fm:familyMembers)
			fm.prepareForNewRound();
		for(PermanentEffect permanentEffect:this.getPEffects(PEffect.FM)){
			((FamilyMemberModify)permanentEffect).modify(this.familyMembers);
		}		
	}

	public void subPoint(Point point) {
		this.point.subPoint(point);
		
	}

	public void addPoint(Point point) {
		this.point.addPoint(point);
		for(PermanentEffect permanentEffect:this.getPEffects(PEffect.DEBUFF_POINT))
		{
			this.subPoint(((Debuff)permanentEffect).getPoints());
		}
		
	}
	
	public boolean isEnoughtPoint(Point point){ 
		return this.point.isEnought(point); 
		  }
	
	public int countCard(CardType type)
	{
		if(type==CardType.BUILDING)
			return buildings.size();
		if(type==CardType.CHARACTER)
			return characters.size();
		if(type==CardType.TERRITORY)
			return territories.size();
		if(type==CardType.VENTURE)
			return ventures.size();
		return 0;
	}

	public List<FamilyMember> getFamilyMembers() {
		return familyMembers;
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
		if(card.isInstanceOfResourceModify(card.getIstantEffect()) && this.getPEffects(PEffect.RESOURCES_TWICE_FROM_DEVELOPEMENT_CARDS_ISTANT_EFFECT).size()>0)
			for(IstantEffect ie:card.getResourceModifyInstantEffects(card.getIstantEffect()))
				ie.activate(this);
		if (card instanceof CharactersCard)
			this.permanentEffects.add(((CharactersCard) card).getPermanentEffects());
		switch(card.getType())
		{
			case BUILDING:
				buildings.add((HarvesterAndBuildings) card);
				break;
			case CHARACTER:
				characters.add((CharactersCard)card);
				break;
			case TERRITORY:
				territories.add((HarvesterAndBuildings)card);
				break;
			case VENTURE:
				ventures.add((VentureCard) card);
				break;
			default:
				break;
		}
	}

	public PermanentEffect getPermanentEffect(PEffect tag) {
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
			if (e.hasTag(PEffect.HALVE_SERVANTS))
				fm.setActionpoint(fm.getActionpoint() + currentAction.getServants()/2);
		}
		fm.setActionpoint(fm.getActionpoint() + currentAction.getServants());
		currentAction.setFm(fm);
	
		for (PermanentEffect e : permanentEffects) {
			if (e.hasTag(PEffect.MODIFY_STRENGTH)) {
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
	
	public List<PermanentEffect> getPEffects(PEffect tag)
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
			if (e.hasTag(PEffect.DISCOUNT)) 
			{
				StrengthModifyAndDiscount mf = (StrengthModifyAndDiscount) e;
				if (mf.getCtype()==CardType.ALL || mf.getCtype() == ((TowerActionSpace) currentAction.getActionSpace()).getTower().getType()) 
					discount.addResources(mf.getDiscount());
			}
		if(card.controlResource(this, discount)){
			card.pay(this, discount);
			return true;
		}
			
		else return false;			
	}
	
	public void vaticanReport(int selection)
	{
		this.alradyPlaceVatican=true;
		int period=model.getTurn();
		int requirement=model.getFaithPointsRequirement().get(period);
		int victoryPoints=model.getVictoryPointsBoundedTofaithPoints().get(requirement);
		if (this.death)
			return;
		
		if(this.point.getFaith()<requirement)
		{
			this.permanentEffects.add(board.getExcommunicationCards()[period-1].getPermanentEffect());			
			if(period==3)
				addPoint(new Point(0,0,model.getVictoryPointsBoundedTofaithPoints().get(this.point.getFaith())));
			model.sendMessage("Not enought faith point, you are excommunicated",this);
		}
					 // TODO da testare
		if(this.point.getFaith()>=requirement) 
		{
			if(selection==0)
			{
				this.permanentEffects.add(board.getExcommunicationCards()[period-1].getPermanentEffect());
				if(period==3)
					addPoint(new Point(0,0,model.getVictoryPointsBoundedTofaithPoints().get(this.point.getFaith())));
				model.sendMessage("Decided not to support the church", this);
			}
			if(selection==1)
			{
				if(this.getPEffects(PEffect.FIVE_ADDITIONAL_VICTORY_POINTS_WHEN_SUPPORT_THE_CHURCH).size()>0)
					this.addPoint(new Point(0,0,5));
				this.subPoint(new Point(0,this.point.getFaith(),0));
				this.addPoint(new Point(0,0,victoryPoints));
			}
		}
	}

		public Integer answerToQuestion(Question question) throws GameException {
			return model.answerToQuestion(question, this);
		}


		public void doAction() {
			if (death) {
				return;
			}
			else {
				System.out.println("timer start");
				updateTimer();
			}
		}
		
		public void doVatican() {
			if (death) {
				return;
			}
			else {
				updateTimer();
			}
		}
		
		private void updateTimer(){
			Turn turn=new Turn(this);
			timer=new Timer();
			timer.schedule(turn, model.getTurnDelay());
		}
		
		public void timerFinished() {
			model.sendMessage("Sveglia!! Il tuo tempo e' scaduto, hai saltato il turno"+ model.turn, this);
			death=true;
		}
 		
 		public void finishAction() throws GameException{
			if(!this.equals(model.getCurrentPlayer()))
				throw new GameException(GameError.ERR_NOT_TURN);
			else
			{
				timer.cancel();
				alradyPlaceFM=false;
				model.finishAction();
			}
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

		public String toString2() {
			return "" + team + "";
		}

		@Override
		public String toString() {
			String str = "";
			
			str += "\n"
				  + "| " + team + "\n" +
					"| He has [" + resource + "] resources and [" + point + "] points\n";
			      		
				str += "| His cards -> ";
				str += buildings + " ";
				str += this.territories + " ";
				str += this.ventures + " ";
				str += this.characters + " ";
				
			str += "\n";
					
			str += "| His leader cards -> ";
			str += this.getLCList();
			str += "\n";
			str += "| Excomunivation in Game -> ";
			for(int c=0;c<3;c++)
				str += model.getBoard().getExcommunicationCards()[c];
			return str;
		}

		public List<LeaderCard> getLCList() {
			return leaderCards;
		}
		
		public void giveLeaderCard(LeaderCard lc) {
			lc.setOwner(this);
			leaderCards.add(lc);
		}
	
		
		public void playLC(Integer id) throws GameException {
			LeaderCard lc = getLC(id);
			if (lc == null) throw new GameException(GameError.LEADER_CARD_USED);
			lc.play();
			PermanentEffect pe = lc.getPe(); 
			if (pe != null)
				permanentEffects.add(pe);
		}
		
		public void activateLC(LeaderCard lc) throws GameException {
			lc.activateOPT();
		}

		private void discardLC(LeaderCard lc) {
			lc.discard();
			leaderCards.remove(lc);
		}
		
		public void discardLC(Integer lcId) throws GameException {
			LeaderCard lc = getLC(lcId);
			discardLC(lc);
		}

		public void setBoard(Board b) {
			board = b;
		}
		public Board getBoard() {
			return board;
		}

		public void registerPermanentEffect(PermanentEffect tempEffect) {
			if (tempEffect == null) return;
			permanentEffects.add(tempEffect);
		}
		
		public void removePermanentEffect(PermanentEffect eff) {
			if (eff == null) return;
			if (permanentEffects.contains(eff))
				permanentEffects.removeIf(e -> e.equals(eff));
			else
				throw new AssertionError();
		}

		public boolean isDead() {
			return death;
		}
		
		public boolean playedVatican() {
			return alradyPlaceVatican;
		}

		public void setAlive() {
			death=false;		
		}

		public LeaderCard getLC(Integer id) {
			for (LeaderCard l : this.leaderCards) {
				if (l.getId() == id) 
					return l;
			}
			return null;
		}
		
		public void setAlradyPlaceFM(boolean alradyPlaceFM) {
			this.alradyPlaceFM = alradyPlaceFM;
		}


		public void activateOPT(Integer id) throws GameException {
			LeaderCard lc = getLC(id);
			if (lc == null) throw new GameException(GameError.LEADER_CARD_USED);

			lc.activateOPT();
			
		}

		public void play(LeaderCard girolamo) throws GameException {
			playLC(girolamo.getId());
		}
}

class Turn extends TimerTask
{
	Player gm;
	
	public Turn(Player gm) {
		this.gm = gm;
	}
	
	public void run(){
		gm.timerFinished();
	}
}