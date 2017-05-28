package gamemodel;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import gamemodel.ActionSpace.*;
import gamemodel.card.Card;
import gamemodel.effects.Effect;
import gamemodel.effects.TestEffects;
import gamemodel.jsonparsing.ASParsing;
import gamemodel.jsonparsing.CardParsing;
import gamemodel.jsonparsing.CustomizationFileReader;
import gamemodel.jsonparsing.TowerASParsing;

public class RealGame {
	private List<Player> players;
	private Board board;
	private Integer roundNumber;
	private List<Player> turnOrder;

	public static void main(String[] args) {
		RealGame game = new RealGame();
		game.start();
	}
	
	public void start() {
		initializeGame();
		
		for (roundNumber = 1; roundNumber < 7; roundNumber++) {
			System.out.println("Round number " + roundNumber);
			setupRound();
			System.out.println(board);
			
			for (int familiare = 0; familiare < 4; familiare++) {
				for (Player p : turnOrder) {
					p.playRound();
				}
				
				if (roundNumber % 2 == 0) {
					for (Player p : players) {
						p.vaticanReport();
					}
				}
			}
		}
	}

	public void setTurnOrder(List<Player> turnOrder) {
		this.turnOrder = turnOrder; 
	}

	private void setupRound() {
		board.setupRound();
	}

	public RealGame initializeGame() {


		List<Card> developmentCards = new ArrayList<Card>();
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/CharacterCards.json",new CardParsing()::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/VentureCards.json",new CardParsing()::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/TerritoryCards.json",new CardParsing()::parsing).parse());
		developmentCards.addAll(new CustomizationFileReader<Card>("Config/BuildingCards.json",new CardParsing()::parsing).parse());				
		Collections.shuffle(developmentCards);

		List<ActionSpace> actionSpaces = new ArrayList<ActionSpace>();
		
		actionSpaces.addAll(new CustomizationFileReader<ActionSpace>("Config/ActionSpace.json",new ASParsing()::parsing).parse());
		actionSpaces.addAll(new CustomizationFileReader<TowerActionSpace>("Config/TowerActionSpace.json",new TowerASParsing()::parsing).parse());				
		
		board = new RealBoard(developmentCards, actionSpaces);

		// Initialize players
		players = new ArrayList<Player>();
		players.add(new RealPlayer(new Resource(5,5,5,5), board, Team.RED));
		players.add(new RealPlayer(new Resource(5,5,5,5), board, Team.BLUE));
		players.add(new RealPlayer(new Resource(5,5,5,5), board, Team.GREEN));
		players.add(new RealPlayer(new Resource(5,5,5,5), board, Team.YELLOW));
		
		for (Player p : players) {
			board.addPlayer(p);
		}

		turnOrder = new ArrayList<Player>();
		for (Player p: players) {
			turnOrder.add(p);
		}
		Collections.shuffle(turnOrder);
		//TODO remove this return (creato per provare CLIView)
		return this;

	}

	public Board getBoard() {
		return board;
	}
}
