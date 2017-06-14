package gamemodel.effects;

import java.io.Serializable;

import gamemodel.Player;

public interface EffectRollBack extends Serializable
{
	public void rollBack(Player player);
}
