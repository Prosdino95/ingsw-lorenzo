package gamemodel.effects;

import java.io.Serializable;

import gamemodel.command.GameException;
import gamemodel.player.Player;

/**
 * Instant effects, being activated only in certain, well-defined places,
 * got the privilege of exposing to the model only the activate method.
 */

public interface IstantEffect extends Serializable{
	
	public void activate(Player player) throws GameException;
	public String toString();
}
