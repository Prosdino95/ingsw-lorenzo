package gamemodel.card;

import java.util.Map;

import gamemodel.CardType;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;

public interface Card {
	public void pay(Player p);
	public Resource getResourceRequirement();
	public Resource getResourcePrice();
	public Point getPointRequirement();
	public Point getPointPrice();
	public CardType getType();
	public int getId(); 
	public Map<CardType, Integer> getRequirementCard(); 
	public boolean ControlResource(Player p);

}
