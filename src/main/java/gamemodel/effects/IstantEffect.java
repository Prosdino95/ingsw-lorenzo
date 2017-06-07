package gamemodel.effects;

import gamemodel.Player;
import gamemodel.command.GameException;

public interface IstantEffect{
	
	public void activate(Player player) throws GameException;
	
}
