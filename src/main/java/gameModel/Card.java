package gameModel;

public interface Card {
	public void pay(Player p);
	public CardType getType();
	public Resource getRequirement(); 
}
