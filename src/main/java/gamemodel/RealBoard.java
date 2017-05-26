package gamemodel;

import java.util.ArrayList;
import java.util.List;

import gamemodel.ActionSpace.ActionSpace;
import gamemodel.ActionSpace.RealActionSpace;
import gamemodel.ActionSpace.RealTowerActionSpace;
import gamemodel.ActionSpace.TowerActionSpace;
import gamemodel.card.Card;

public class RealBoard implements Board {
	private List<ActionSpace> actionSpaces;
	private List<Player> players;
	private List<Card> territoriesCards;
	private List<Card> buildingsCards;
	private List<Card> charactersCards; 
	private List<Card> venturesCards;
	private List<Card> cards;
	private Dice dice;
	
	public RealBoard() {
		this.actionSpaces = new ArrayList<ActionSpace>();
		this.players = new ArrayList<Player>();
		this.cards = new ArrayList<Card>();
	}
	
	public RealBoard(List<Card> cards, List<ActionSpace> actionSpaces) {
		this.players = new ArrayList<Player>();
		this.actionSpaces = actionSpaces;
		
		this.cards = cards;
		this.venturesCards = new ArrayList<Card>();
		this.buildingsCards = new ArrayList<Card>();
		this.charactersCards = new ArrayList<Card>();
		this.territoriesCards = new ArrayList<Card>();
		for (Card c : cards) {
			switch (c.getType()) {
			case BUILDINGS:
				buildingsCards.add(c);
				break;
			case CHARACTERS:
				charactersCards.add(c);
				break;
			case TERRITORIES:
				territoriesCards.add(c);
				break;
			case VENTURES:
				venturesCards.add(c);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void setupRound() {
		for (ActionSpace as : actionSpaces) {
			if (as instanceof TowerActionSpace) {
				CardType color = ((TowerActionSpace) as).getTower().getType();
				Card card = popCard(color);
				if (card==null) {
					System.err.println("Finished cards");
				}
				((RealTowerActionSpace) as).attachDevelopmentCard(card);
			}
		}
		
		for (Player p : players) {
			p.prepareForNewRound();
		}
	}

	private Card popCard(CardType color) {
		switch (color) {
			case BUILDINGS:
				return buildingsCards.remove(0);
		case CHARACTERS:
				return charactersCards.remove(0);
		case TERRITORIES:
				return territoriesCards.remove(0);
		case VENTURES:
				return venturesCards.remove(0);
		default:
				break;
		}

		for (Card c : cards) {
			if (c.getType().equals(color)) {
				return c;
			}
		}
		return null;
	}

	private Card getCard(CardType type) {
		for (Card c : cards) {
			if (c.getType().equals(type)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public void addActionSpace(RealActionSpace a) {
		this.actionSpaces.add(a);
	}

	@Override
	public RealActionSpace getActionSpace(int id) {
		for (ActionSpace as : this.actionSpaces) {
			if (as.getId() == id) return (RealActionSpace) as;
		}
		return null;
	}

	@Override
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public String toString() {
		String str = "";
		str += "Action Spaces\n";
		for (ActionSpace as : actionSpaces) {
			str += as.toString();
			str += "\n";
		}
		return str;
	}

	public Dice getDice() {
		return dice;
	}

	private void setDice(Dice dice) {
		this.dice = dice;
	}

	@Override
	public List<ActionSpace> getActionSpaces() {
		return this.actionSpaces;
	}

}
