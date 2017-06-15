package gamemodel;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.actionSpace.RealActionSpace;
import gamemodel.actionSpace.RealTowerActionSpace;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.Excommunication;

public class Board implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<ActionSpace> actionSpaces;
	
	private transient List<Card> territoriesCards;
	private transient List<Card> buildingsCards;
	private transient List<Card> charactersCards; 
	private transient List<Card> venturesCards;
	
	private transient List<Card> cards;
	
	private transient Dice dice;
	private Excommunication[]excommunicationCards=new Excommunication[3];

	
	public Board() {
		this.actionSpaces = new ArrayList<ActionSpace>();
		this.cards = new ArrayList<Card>();
		this.dice=new Dice();
	}
	
	public Board(List<Card> cards, List<ActionSpace> actionSpaces) {
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

	public void addActionSpace(RealActionSpace a) {
		this.actionSpaces.add(a);
	}

	public RealActionSpace getActionSpace(int id) {
		for (ActionSpace as : this.actionSpaces) {
			if (as.getId() == id) return (RealActionSpace) as;
		}
		return null;
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

	public void setDice(int black, int white, int orange) {
		dice.setValue(Color.BLACK, black);
		dice.setValue(Color.WHITE, white);
		dice.setValue(Color.ORANGE, orange);
	}
	public Excommunication[] getExcommunicationCards() 
	{
		return excommunicationCards;
	}

	public List<Player> getTurnOrder() {
		for(ActionSpace a:actionSpaces)
		 	if(a.getType()==ActionSpaceType.COUNCIL_PALACE)
		 		return ((MemoryActionSpace) a).getPlayers();
		return null;
		 	
	}

	public LeaderCard getLC(Integer lcId) {
		// La board tiene una mappa da id a carta leader
		return null;
	}

}
