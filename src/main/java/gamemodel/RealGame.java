package gamemodel;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import gamemodel.ActionSpace.*;
import gamemodel.card.Card;
import gamemodel.effects.Effect;
import gamemodel.effects.TestEffects;

public class RealGame {
	private List<Player> players;
	private Board board;
	private Integer roundNumber;
	private List<Player> turnOrder;
//	private CustomizationFileReader customizationFileReader;

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
		// Read customization file

		List<Card> developmentCards = new ArrayList<Card>();
		for (int id = 0; id < 24; id++) {
			developmentCards.add(new StupidCard(id, CardType.TERRITORIES));
			developmentCards.add(new StupidCard(id + 24, CardType.BUILDINGS));
			developmentCards.add(new StupidCard(id + 48, CardType.CHARACTERS));
			developmentCards.add(new StupidCard(id + 72, CardType.VENTURES));
		}
		Collections.shuffle(developmentCards);

		List<ActionSpace> actionSpaces = new ArrayList<ActionSpace>();
		Effect e=new TestEffects();
		Tower tower;

		// These parameters must all come from the customization file
		tower = new Tower(CardType.TERRITORIES);
		actionSpaces.add(new RealTowerActionSpace(1, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(3, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(5, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(7, e, tower,ActionSpaceType.TOWER));

		tower = new Tower(CardType.CHARACTERS);
		actionSpaces.add(new RealTowerActionSpace(1, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(3, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(5, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(7, e, tower,ActionSpaceType.TOWER));

		tower = new Tower(CardType.BUILDINGS);
		actionSpaces.add(new RealTowerActionSpace(1, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(3, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(5, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(7, e, tower,ActionSpaceType.TOWER));
		
		tower = new Tower(CardType.VENTURES);
		actionSpaces.add(new RealTowerActionSpace(1, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(3, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(5, e, tower,ActionSpaceType.TOWER));
		actionSpaces.add(new RealTowerActionSpace(7, e, tower,ActionSpaceType.TOWER));

		actionSpaces.add(new RealActionSpace(1, e, ActionSpaceType.MARKET));
		actionSpaces.add(new RealActionSpace(1, e, ActionSpaceType.MARKET));
		actionSpaces.add(new RealActionSpace(1, e, ActionSpaceType.MARKET));
		actionSpaces.add(new RealActionSpace(1, e, ActionSpaceType.MARKET));
		actionSpaces.add(new RealActionSpace(1, e, ActionSpaceType.HARVEST));
		actionSpaces.add(new RealActionSpace(1, e, ActionSpaceType.PRODUCTION));
		actionSpaces.add(new RealActionSpace(1, e, ActionSpaceType.COUNCIL_PALACE));
		
		
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
