package gamemodel.card;

import java.util.Collection;
import java.util.Map;

import gamemodel.CardType;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.effects.Effect;

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
	public void activeIstantEffect(Player p);
	public void activePermanentEffect(Player p);
	public Collection<? extends Effect> getPermanentEffects();

}
