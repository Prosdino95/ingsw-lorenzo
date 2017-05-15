package gameModel;

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
	public Resource getRequirement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pay(Player p) {
		// TODO Auto-generated method stub
		
	}

}
