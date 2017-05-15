package gameModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RealGame {
	private List<Player> players;
	private Board board;
	private Integer roundNumber;
	private List<Player> turnOrder;
//	private CustomizationFileReader customizationFileReader;

	public void start() {
		initializeGame();
		
		for (roundNumber = 1; roundNumber < 7; roundNumber++) {
			setupRound();
			
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

	private void initializeGame() {
		// Read customization file

		board = new RealBoard();
		
		List<Card> developmentCards = new ArrayList<Card>();
		for (int id = 0; id < 96; id++)
			developmentCards.add(new StupidCard(id));
		Collections.shuffle(developmentCards);
		
		// Initialize players
		players = new ArrayList<Player>();
		players.add(new RealPlayer(new Resource(5,5,5,5), new Command((RealBoard) board), Team.RED));
		players.add(new RealPlayer(new Resource(5,5,5,5), new Command((RealBoard) board), Team.BLUE));
		players.add(new RealPlayer(new Resource(5,5,5,5), new Command((RealBoard) board), Team.GREEN));
		players.add(new RealPlayer(new Resource(5,5,5,5), new Command((RealBoard) board), Team.YELLOW));
		
		turnOrder = players;

		Effect e=new TestEffects();
		List<RealActionSpace> actionSpaces = new ArrayList<RealActionSpace>();
		// Initialize action spaces on board
		actionSpaces.add(new RealActionSpace(7,e,ActionSpaceType.TOWER));
		
	}
}
