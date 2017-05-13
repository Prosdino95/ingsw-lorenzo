package gameModel;

public class StupidCard implements Card {
	private int id;
	private CardType type;
	
	public StupidCard(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public CardType getType() {
		return type;
	}

}
