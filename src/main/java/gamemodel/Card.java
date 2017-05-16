package gamemodel;

import java.util.Map;

public interface Card {
	public void pay(Player p);
	public Resource getResourceRequirement();
	public Resource getResourcePrice();
	public Point getPointRequirement();
	public Point getPointPrice();
	public CardType getType();
	public int getId(); 
	public Map<CardType, Integer> getRequirementCard(); 

}
