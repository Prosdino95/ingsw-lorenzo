package gameModel;

public class StupidCard implements Card {
	private int id;
	
	public StupidCard(int id) {
		this.id = id;
	}

	@Override
	public int getId(Card card) {
		return id;
	}

}
