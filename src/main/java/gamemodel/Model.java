package gamemodel;


import java.io.Serializable;

import java.util.ArrayList;


import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamemodel.actionSpace.*;
import gamemodel.card.*;
import gamemodel.card.CardType;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.card.VentureCard;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.command.PlaceFMCommandFactory;
import gamemodel.jsonparsing.ASParsing;
import gamemodel.jsonparsing.CardParsing;
import gamemodel.jsonparsing.CustomizationFileReader;
import gamemodel.jsonparsing.ExcommunicationParsing;
import gamemodel.jsonparsing.FaithRequirements;
import gamemodel.jsonparsing.LeaderCardParsing;
import gamemodel.jsonparsing.TowerASParsing;
import gamemodel.permanenteffect.PEffect;
import reti.server.Controller;

public class Model implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Player> players;
	private Board board;	
	private transient TurnOrder turnOrder;
	private transient Map<Integer,Integer> faithPointsRequirement= new HashMap<>();
	private transient Map<Integer,Integer> victoryPointsBoundedTofaithPoints=new HashMap<>();
	private transient Controller controller;
	private transient PlaceFMCommandFactory commandFactory;
	private transient Map<Integer,Integer> victoryPointsBoundedToTerritoryCards= new HashMap<>();
	private transient Map<Integer,Integer> victoryPointsBoundedToCharacterCards= new HashMap<>();
	public int turn=1;
	private Player currentPlayer;
	private transient List<Object> leaderCard=new ArrayList<>();
	private GameState gameState; 
	private int delay;
	
	
	public static void main(String[] args){
		// Model m=new Model(4);
		//System.out.println(m.getBoard().getActionSpaces());
		//m.nextTurn();
	}
	
	public Model(int num){
		initializeGame(num);
		this.delay=200000;
	}
	
	public Model(int num, int delay){
		initializeGame(num);
		this.delay=delay;
	}
	
	
	public void setController(Controller c){
		this.controller=c;
	}
	
	
	
	private void nextPlayer(){
		currentPlayer=turnOrder.getNextPlayer();
		currentPlayer.doAction();
	}
	
	public void updateState(){
		boolean all=true;
		for(Player p:players)		
			if(!p.isDead())
				all=false;
		if(all)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				Thread.currentThread().interrupt();
			}
		switch(gameState){
		case GAME_FINISH:
			whoIsWinner(players);
			//TODO messaggio vittoria
			System.out.println("finitoooooooooooooooo");
			controller.shutDown();
			break;
		case TURN_FINISH:
			if(turn%2==0){
				gameState=GameState.VATICAN_TIME;
				controller.notifyNewModel();
			}
			else{
				System.out.println("next turn");
				turn++;
				gameState=GameState.SET_UP_ROUND;
			}					
			break;
		case VATICAN_TIME:
			boolean allPlayed=true;
			for(Player p:players){
				if(!p.playedVatican())
					allPlayed=false;
				if(p.isDead() && !p.playedVatican())
					p.vaticanReport(0);
			}	
			if(allPlayed && turn==6)
				gameState=GameState.GAME_FINISH;
			else if(allPlayed){
				turn++;
				gameState=GameState.SET_UP_ROUND;
			}
			break;
		case ACTION_FINISH:
			currentPlayer=null;
			if(!turnOrder.hasNext()){
				gameState=GameState.TURN_FINISH;
			}
			else{
				nextPlayer();
				gameState=GameState.PLAYER_PLAING;
				controller.notifyNewModel();
			}			
			break;
		case PLAYER_PLAING:
			if(currentPlayer.isDead())
				try {
					currentPlayer.finishAction();
				} catch (GameException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException();
				}
			break;
		case SET_UP_ROUND:
			setupRound();
			nextPlayer();
			controller.notifyNewModel();
			gameState=GameState.PLAYER_PLAING;
			break;
		default:
			break;
		}
	}
		


	public void setupRound() {		
		this.turnOrder.setupRound(board.getTurnOrder());
		board.setupRound(turn);
		for(Player p:players)
			p.prepareForNewRound();
	}
	
	
	private void victoryPointsBoundedTofaithPointsInitialize()
	{
		victoryPointsBoundedTofaithPoints.put(0,0);
		victoryPointsBoundedTofaithPoints.put(1,1);
		victoryPointsBoundedTofaithPoints.put(2,2);
		victoryPointsBoundedTofaithPoints.put(3,3);
		victoryPointsBoundedTofaithPoints.put(4,4);
		victoryPointsBoundedTofaithPoints.put(5,5);
		victoryPointsBoundedTofaithPoints.put(6,7);
		victoryPointsBoundedTofaithPoints.put(7,9);
		victoryPointsBoundedTofaithPoints.put(8,11);
		victoryPointsBoundedTofaithPoints.put(9,13);
		victoryPointsBoundedTofaithPoints.put(10,15);
		victoryPointsBoundedTofaithPoints.put(11,17);
		victoryPointsBoundedTofaithPoints.put(12,19);
		victoryPointsBoundedTofaithPoints.put(13,22);
		victoryPointsBoundedTofaithPoints.put(14,35);
		victoryPointsBoundedTofaithPoints.put(15,30);
	}
	public Map<Integer, Integer> getVictoryPointsBoundedTofaithPoints() 
	{
		return victoryPointsBoundedTofaithPoints;
	}

	private void victoryPointsBoundedToTerritoryCardsInitialize()
	{
		victoryPointsBoundedToTerritoryCards.put(0,0);
		victoryPointsBoundedToTerritoryCards.put(1,0);
		victoryPointsBoundedToTerritoryCards.put(2,0);
		victoryPointsBoundedToTerritoryCards.put(3,1);
		victoryPointsBoundedToTerritoryCards.put(4,4);
		victoryPointsBoundedToTerritoryCards.put(5,10);
		victoryPointsBoundedToTerritoryCards.put(6,20);
	}
	private void victoryPointsBoundedToCharacterCardsInitialize()
	{
		victoryPointsBoundedToCharacterCards.put(0,0);
		victoryPointsBoundedToCharacterCards.put(1,1);
		victoryPointsBoundedToCharacterCards.put(2,3);
		victoryPointsBoundedToCharacterCards.put(3,6);
		victoryPointsBoundedToCharacterCards.put(4,10);
		victoryPointsBoundedToCharacterCards.put(5,15);  
		victoryPointsBoundedToCharacterCards.put(6,21);
	}
	public void initializeGame(int num) {
		board = new Board();

		List<Card> developmentCards = new ArrayList<Card>();
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/CharacterCards.json",new CardParsing(board)::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/VentureCards.json",new CardParsing(board)::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/TerritoryCards.json",new CardParsing(board)::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/BuildingCards.json",new CardParsing(board)::parsing).parse());				
		Collections.shuffle(developmentCards);

		List<ActionSpace> actionSpaces = new ArrayList<ActionSpace>();
		
		actionSpaces.addAll(new CustomizationFileReader<ActionSpace>("Config/ActionSpace.json",new ASParsing(board)::parsing).parse());
		actionSpaces.addAll(new CustomizationFileReader<TowerActionSpace>("Config/TowerActionSpace.json",new TowerASParsing(board)::parsing).parse());
		
		board.addBoard(developmentCards, actionSpaces);

		// Initialize players
		players = new ArrayList<Player>();
		players.add(new Player(new Resource(5,2,2,3), board, Team.RED,this));
		players.add(new Player(new Resource(6,2,2,3), board, Team.BLUE,this));
		if(num>=3)players.add(new Player(new Resource(7,2,2,3), board, Team.GREEN,this));
		if(num==4)players.add(new Player(new Resource(8,2,2,3), board, Team.YELLOW,this));
		if(num<4){
			board.getActionSpaces().remove(board.getActionSpace(20));
			board.getActionSpaces().remove(board.getActionSpace(21));
		}
		
		List<Excommunication> ex=new CustomizationFileReader<Excommunication>("Config/Excommunication.json",new ExcommunicationParsing()::parsing).parse();
		board.setEXCard(ex);
		
		leaderCard.addAll(new CustomizationFileReader<LeaderCard>("Config/LeaderCards.json",new LeaderCardParsing(board)::parsing).parse());
		
		List<Integer> faithRequirement=new CustomizationFileReader<Integer>("Config/FaithRequirements.json",new FaithRequirements()::parsing).parse();
		faithPointsRequirement.put(1,faithRequirement.get(0));
		faithPointsRequirement.put(2,faithRequirement.get(1));
		faithPointsRequirement.put(3,faithRequirement.get(2));
		victoryPointsBoundedTofaithPointsInitialize();
		victoryPointsBoundedToTerritoryCardsInitialize();
		victoryPointsBoundedToCharacterCardsInitialize();
		
		this.commandFactory=PlaceFMCommandFactory.GenerateCommandFactory(players.size());
		turnOrder=new TurnOrder(players);
		gameState=GameState.SET_UP_ROUND;
	}
	
	public void giveLeaderCard(Player player, int index) {
		player.giveLeaderCard((LeaderCard) leaderCard.remove(index));		
	}


	public Board getBoard() {
		return board;
	}

	
	public List<Player> getPlayers() {
		return this.players;
	}

	public Player getPlayer(Team team) {
		for (Player p : players) {
			if (p.getTeam() == team) return p;
		}
		throw new RuntimeException();
	}
	

	public PlaceFMCommandFactory getCommandFactory() {
		return commandFactory;
	}

	public Integer answerToQuestion(Question gq, Player player) throws GameException {
		if (controller == null) {
			throw new GameException(GameError.NOT_PLAYING_ONLINE);
		}
		return controller.answerToQuestion(gq, player);
	}


	private int occurrence(List<Player> players,String string,int points) //testato
	{
		int number=0;
		if(string.equals("military"))
			for(Player player:players)
				if(player.getPoint().getMilitary()==points)
					number++;
		if(string.equals("victory"))
			for(Player player:players)
				if(player.getPoint().getVictory()==points)
					number++;
		return number;
	}
	/*private*/ public void pointsVictoryBoundedToMilitaryPoints(List<Player> players) //testato
	{
		Player temp;
		for(int co=0;co<players.size();co++)   //ordina in modo decrescente la lista dei giocatori in base ai punti miliatri
			for(int c=0;c<(players.size()-1);c++)
				if(players.get(c).getPoint().getMilitary()<players.get(c+1).getPoint().getMilitary())
				{
					temp=players.get(c);
					players.set(c,players.get(c+1));
					players.set(c+1,temp);
				}
		if(occurrence(players,"military",players.get(0).getPoint().getMilitary())>1)
			for(int c=0;c<occurrence(players,"military",players.get(0).getPoint().getMilitary());c++)
				players.get(c).addPoint(new Point(0,0,5));
		if(occurrence(players,"military",players.get(0).getPoint().getMilitary())==1)
		{
			players.get(0).addPoint(new Point(0,0,5));
			for(int c=1;c<=occurrence(players,"military",players.get(1).getPoint().getMilitary());c++)
				players.get(c).addPoint(new Point(0,0,2));
		}
	}
	private void pointsVictoryAssignment(List<Player> players) //non testato
	{
		for(Player player:players)
		{
			if(player.getPEffects(PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_FIVE_VICTORY_POINT).size()>0)
				player.subPoint(new Point(0,0,player.getPoint().getVictory()/5));
			if(player.getPEffects(PEffect.NO_VICTORY_POINTS_BOUNDED_TO_TERRITORY_CARDS).isEmpty())
				player.addPoint(new Point(0,0,victoryPointsBoundedToTerritoryCards.get(player.countCard(CardType.TERRITORY))));
			if(player.getPEffects(PEffect.NO_VICTORY_POINTS_BOUNDED_TO_CHARACTER_CARDS).isEmpty())
				player.addPoint(new Point(0,0,victoryPointsBoundedToCharacterCards.get(player.countCard(CardType.CHARACTER))));
			if(player.getPEffects(PEffect.NO_VICTORY_POINTS_BOUNDED_TO_VENTURE_CARDS).isEmpty())
				for(VentureCard ventureCard:player.getVentures())
				{
					try 
					{
						ventureCard.activePermanentEffect(player);
					}
					catch (GameException e) 
					{
						// TODO Auto-generated catch block
						throw new RuntimeException();
					}
				}
			int resources=player.getResource().getGold();
			resources+=player.getResource().getServant();
			resources+=player.getResource().getStone();
			resources+=player.getResource().getWood();
			player.addPoint(new Point(0,0,resources/5));
			if(player.getPEffects(PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_MILITARY_POINT).size()>0)
				player.subPoint(new Point(0,0,player.getPoint().getMilitary()));
			if(player.getPEffects(PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_WOOD_AND_STONE_ON_YOUR_BUILDINGS_CARDS_COST).size()>0)
			{
				int stoneAndWood=0;
				for(HarvesterAndBuildings harvesterAndBuildings:player.getBuildings())
				{
					stoneAndWood+=harvesterAndBuildings.getResourcePrice().getStone();
					stoneAndWood+=harvesterAndBuildings.getResourcePrice().getWood();
				}
				player.subPoint(new Point(0,0,stoneAndWood));
			}
			if(player.getPEffects(PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_RESOURCE).size()>0)
			{
				int resource=player.getResource().getGold();
				resource+=player.getResource().getServant();
				resource+=player.getResource().getStone();
				resource+=player.getResource().getWood();
				player.subPoint(new Point(0,0,resource));
			}
		}
		pointsVictoryBoundedToMilitaryPoints(players);		
	}
	public void whoIsWinner(List<Player> players)  //testato
	{
		/*pointsVictoryAssignment(players);*/
		Player temp;
		for(int co=0;co<players.size();co++)   //ordina in modo decrescente la lista dei giocatori in base ai punti vittoria
			for(int c=0;c<(players.size()-1);c++)
				if(players.get(c).getPoint().getVictory()<players.get(c+1).getPoint().getVictory())
				{
					temp=players.get(c);
					players.set(c,players.get(c+1));
					players.set(c+1,temp);
				}
		if(occurrence(players,"victory",players.get(0).getPoint().getVictory())>1)
			for(Player player:turnOrder.getListActionOrder())
				if(player.getPoint().getVictory()==players.get(0).getPoint().getVictory())
				{
					
					players.set(0, player);
					//System.out.println("The winner is " + player);
					break;
				}
		else
			System.out.println("The winner is " + players.get(0));  //TODO migliorare
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public List<Object> getLeaderCards() {
		return this.leaderCard;
	}

	public void sendMessage(String string, Player player) {
		controller.sendMessage(string, player);		
	}

	// TODO
	public Integer getTurnDelay() {
		return delay;
	}
	
	public void setCurretPlayer(Player p){
		this.currentPlayer=p;
	}

	public void finishAction() {
		this.gameState=GameState.ACTION_FINISH;		
	}

	public GameState getState() {
		return gameState;
	}


}



