package gamemodel;

import java.io.Serializable;
import java.util.ArrayList;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamemodel.actionSpace.*;
import gamemodel.card.Card;
import gamemodel.command.GameException;
import gamemodel.command.PlaceFMCommandFactory;
import gamemodel.jsonparsing.ASParsing;
import gamemodel.jsonparsing.CardParsing;
import gamemodel.jsonparsing.CustomizationFileReader;
import gamemodel.jsonparsing.TowerASParsing;
import server.Controller;
import server.GameQuestion;

public class Model implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Player> players;
	private Board board;
	private Integer actionNumber=1;
	private transient TurnOrder turnOrder;
	private transient Map<Integer,Integer> faithPointsRequirement= new HashMap<>();
	private transient Map<Integer,Integer> victoryPointsBoundedTofaithPointsRequirement=new HashMap<>();
	private transient Controller controller;
	private transient PlaceFMCommandFactory commandFactory;
	
	public static void main(String arg[]){
		Model m=new Model(4);
		m.nextTurn();
	}
	
	public Model(int num){
		initializeGame(num);	
	}
	
	
	public void setController(Controller c){
		this.controller=c;
	}
	
	public void nextTurn(){
		Player currentp=turnOrder.getNextPlayer();
		for(Player p:players)
			p.setCurrentPlayer(currentp);     
	}
	
	public void finishAction() throws GameException{
		if(actionNumber==16)
			setupRound();
		nextTurn();
	}

	private void setupRound() {
		board.setupRound();
		this.turnOrder.setupRound(board.getTurnOrder());
		for(Player p:players)
			p.prepareForNewRound();
	}
	
	
	private void victoryPointsBoundedTofaithPointsRequirementInitialize()
	{
		victoryPointsBoundedTofaithPointsRequirement.put(0,0);
		victoryPointsBoundedTofaithPointsRequirement.put(1,1);
		victoryPointsBoundedTofaithPointsRequirement.put(2,2);
		victoryPointsBoundedTofaithPointsRequirement.put(3,3);
		victoryPointsBoundedTofaithPointsRequirement.put(4,4);
		victoryPointsBoundedTofaithPointsRequirement.put(5,5);
		victoryPointsBoundedTofaithPointsRequirement.put(6,7);
		victoryPointsBoundedTofaithPointsRequirement.put(7,9);
		victoryPointsBoundedTofaithPointsRequirement.put(8,11);
		victoryPointsBoundedTofaithPointsRequirement.put(9,13);
		victoryPointsBoundedTofaithPointsRequirement.put(10,15);
		victoryPointsBoundedTofaithPointsRequirement.put(11,17);
		victoryPointsBoundedTofaithPointsRequirement.put(12,19);
		victoryPointsBoundedTofaithPointsRequirement.put(13,22);
		victoryPointsBoundedTofaithPointsRequirement.put(14,35);
		victoryPointsBoundedTofaithPointsRequirement.put(15,30);
	}

	public void initializeGame(int num) {


		List<Card> developmentCards = new ArrayList<Card>();
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/CharacterCards.json",new CardParsing()::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/VentureCards.json",new CardParsing()::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/TerritoryCards.json",new CardParsing()::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/BuildingCards.json",new CardParsing()::parsing).parse());				
		Collections.shuffle(developmentCards);

		List<ActionSpace> actionSpaces = new ArrayList<ActionSpace>();
		
		actionSpaces.addAll(new CustomizationFileReader<ActionSpace>("Config/ActionSpace.json",new ASParsing()::parsing).parse());
		actionSpaces.addAll(new CustomizationFileReader<TowerActionSpace>("Config/TowerActionSpace.json",new TowerASParsing()::parsing).parse());				
		
		board = new Board(developmentCards, actionSpaces);

		// Initialize players
		players = new ArrayList<Player>();
		players.add(new Player(new Resource(5,5,5,5), board, Team.RED,this));
		players.add(new Player(new Resource(5,5,5,5), board, Team.BLUE,this));
		if(num>=3)players.add(new Player(new Resource(5,5,5,5), board, Team.GREEN,this));
		if(num==4)players.add(new Player(new Resource(5,5,5,5), board, Team.YELLOW,this));
		
		List<Integer> faithRequirement=new ArrayList<>(); //inizializzazione tramite jason
		//faithPointsRequirement.put(1,faithRequirement.get(0));
		//faithPointsRequirement.put(2,faithRequirement.get(1));
		//faithPointsRequirement.put(3,faithRequirement.get(2));
		this.victoryPointsBoundedTofaithPointsRequirementInitialize();
		this.commandFactory=PlaceFMCommandFactory.GenerateCommandFactory(players.size());
		turnOrder=new TurnOrder(players);
		setupRound();	
		nextTurn();
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

	public String answerToQuestion(Question gq, Player player) throws GameException {
		return controller.answerToQuestion(gq, player);
	}
}
