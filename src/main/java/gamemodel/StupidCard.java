package gamemodel;

import java.util.Map;

public class StupidCard implements Card {
	private int id;
	private CardType type;
	
	public StupidCard(int id) {
		this.id = id;
	}
	
	public StupidCard(int id, CardType type) {
		this.id = id;
		this.type = type;
	}


	@Override
	public int getId() {
		return id;
	}

	@Override
	public CardType getType() {
		return type;
	}
	
	public String toString() {
		String str = "";
		str += "Card id: " + getId();
		str += " ";
		str += "Card type: " + getType();
		return str;
	}



	@Override
	public void pay(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Resource getResourceRequirement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource getResourcePrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPointRequirement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPointPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<CardType, Integer> getRequirementCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ControlResource(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
