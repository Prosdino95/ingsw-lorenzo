package gamemodel.effects;

import java.io.Serializable;

import gamemodel.Player;
import gamemodel.command.GameException;

public interface IstantEffect extends Serializable{
	
	public void activate(Player player) throws GameException;
	
}
