package gamemodel.card;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.command.GameException;

public interface Card extends Serializable {
	public boolean controlResource(Player p,Resource discount);
	public Resource getResourceRequirement();
	public Resource getResourcePrice();
	public Point getPointRequirement();
	public Point getPointPrice();
	public CardType getType();
	public int getId(); 
	public void activeIstantEffect(Player p) throws GameException;
	void pay(Player p, Resource discount);
	public int getPeriod();

}
