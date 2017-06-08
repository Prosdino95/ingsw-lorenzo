package gamemodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.RealActionSpace;
import gamemodel.actionSpace.RealTowerActionSpace;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.Excommunication;
import gamemodel.permanenteffect.PermanentEffect;

public class Board implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<ActionSpace> actionSpaces;
	private List<Player> players;
	private transient List<Card> territoriesCards;
	private transient List<Card> buildingsCards;
	private transient List<Card> charactersCards; 
	private transient List<Card> venturesCards;
	private transient List<Card> cards;
	private transient Dice dice;
	private Excommunication[]excommunicationCards=new Excommunication[3];


	public Board() {
		this.actionSpaces = new ArrayList<ActionSpace>();
		this.players = new ArrayList<Player>();
		this.cards = new ArrayList<Card>();
	}
	
	public Board(List<Card> cards, List<ActionSpace> actionSpaces) {
		this.players = new ArrayList<Player>();
		this.actionSpaces = actionSpaces;
		this.dice=new Dice();
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
		// TODO inizializzazione 3 carte scomunica
	}

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
		dice.rollDice();
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
	
	public Excommunication[] getExcommunicationCards() 
	{
		return excommunicationCards;
	}

	public void addActionSpace(RealActionSpace a) {
		this.actionSpaces.add(a);
	}


	public RealActionSpace getActionSpace(int id) {
		for (ActionSpace as : this.actionSpaces) {
			if (as.getId() == id) return (RealActionSpace) as;
		}
		return null;
	}

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


	public List<ActionSpace> getActionSpaces() {
		return this.actionSpaces;
	}

}
