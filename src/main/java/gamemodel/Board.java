package gamemodel;

import java.io.Serializable;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	private transient List<Card> territoryCards;
	private transient List<Card> buildingCards;
	private transient List<Card> characterCards; 
	private transient List<Card> ventureCards;
	private transient Dice dice;
	private Excommunication[]excommunicationCards=new Excommunication[3];

	
	
	public void addBoard(List<Card> cards, List<ActionSpace> actionSpaces) {
		this.actionSpaces = actionSpaces;
		for (Card c : cards) {
			switch (c.getType()) {
			case BUILDING:
				buildingCards.add(c);
				break;
			case CHARACTER:
				characterCards.add(c);
				break;
			case TERRITORY:
				territoryCards.add(c);
				break;
			case VENTURE:
				ventureCards.add(c);
				break;
			default:
				break;
			}
		}
	}
	
	public Board() {		
		this.dice=new Dice();
		this.ventureCards = new ArrayList<>();
		this.buildingCards = new ArrayList<>();
		this.characterCards = new ArrayList<>();
		this.territoryCards = new ArrayList<>();
		this.actionSpaces=new ArrayList<>();
	}

	public void setupRound(int turn) {
		for (ActionSpace as : actionSpaces) {
			as.prepareForNewRound();
			if (as instanceof MemoryActionSpace)
				((MemoryActionSpace)as).prepareForNewRound();
			if (as instanceof TowerActionSpace) {
				((TowerActionSpace) as).getTower().prepareForNewRound();
				CardType color = ((TowerActionSpace) as).getTower().getType();
				Card card = popCard(color,turn);
				if (card==null) {
					System.err.println("Finished cards");
				}
				((RealTowerActionSpace) as).attachDevelopmentCard(card);
			}
		}
		dice.rollDice();
	}

	private Card popCard(CardType color,int turn) {
		switch (color) {
		case BUILDING:
				return getCard(turn,buildingCards);
		case CHARACTER:
				return getCard(turn,characterCards);
		case TERRITORY:
				return getCard(turn,territoryCards);
		case VENTURE:
				return getCard(turn,ventureCards);
		default:
				return null;
		}
	}

	private Card getCard(int turn,List<Card>card) {
		int p;
		for(Card c: card)
			if(c.getPeriod()==((turn+1)/2))			
				return card.remove(card.indexOf(c));				
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
	
	public void setEXCard(List<Excommunication>ex){
		int random=new Random().nextInt(7);
		//TODO da fare meglio, ora si suppone una lsita orinata secondo il periodo
		excommunicationCards[0]=ex.get(random);
		excommunicationCards[1]=ex.get(random+7);
		excommunicationCards[2]=ex.get(random+14);	
	}

	public LeaderCard getLC(Integer lcId) {
		// La board tiene una mappa da id a carta leader
		return null;
	}

	public List<TowerActionSpace> getActionSpaces(CardType cardType) {
		List<TowerActionSpace> lst = new ArrayList<>();
		for (ActionSpace as : actionSpaces) {
			if (as.getType() != ActionSpaceType.TOWER) continue;
			TowerActionSpace tas = (TowerActionSpace) as;
			Card card = tas.getCard();
			if (card != null && 
					(card.getType() == cardType || cardType == CardType.ALL)) 
				lst.add(tas);
		}
		return lst;
	}


}
