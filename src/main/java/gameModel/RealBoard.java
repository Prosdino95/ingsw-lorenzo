package gameModel;

import java.util.ArrayList;
import java.util.List;

public class RealBoard implements Board {
	private List<Card> developmentCards;
	private List<RealActionSpace> actionSpaces;
	private List<Player> players;
	
	public RealBoard() {
		this.developmentCards = new ArrayList<Card>();
		this.actionSpaces = new ArrayList<RealActionSpace>();
		this.players = new ArrayList<Player>();
	}
	
	public RealBoard(List<Card> developmentCards, List<RealActionSpace> actionSpaces, List<Player> players) {
		this.developmentCards = developmentCards;
		this.actionSpaces = actionSpaces;
		this.players = players;
	}

	@Override
	public void setupRound() {
		for (RealActionSpace as : actionSpaces) {
			if (as instanceof RealTowerActionSpace) {
				((RealTowerActionSpace) as).attachDevelopmentCard(developmentCards.remove(0));
			}
		}
	}

	@Override
	public void addActionSpace(RealActionSpace a) {
		this.actionSpaces.add(a);
	}

	@Override
	public RealActionSpace getActionSpaces(int id) {
		for (RealActionSpace as : this.actionSpaces) {
			if (as.getId() == id) return as;
		}
		return null;
	}

}
