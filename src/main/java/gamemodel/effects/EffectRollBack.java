package gamemodel.effects;

import java.io.Serializable;

import gamemodel.player.Player;

public interface EffectRollBack extends Serializable
{
	public void rollBack(Player player);
}
