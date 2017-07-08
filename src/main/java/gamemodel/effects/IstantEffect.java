package gamemodel.effects;

import java.io.Serializable;

import gamemodel.command.GameException;
import gamemodel.player.Player;

public interface IstantEffect extends Serializable{
	
	public void activate(Player player) throws GameException;
	public String toString();
}
