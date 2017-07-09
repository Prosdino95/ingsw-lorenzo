package gamemodel.effects;

import java.io.Serializable;

import gamemodel.player.Player;

/**
 * Some instant effects must be activated late in the chain of legality 
 * checking. As the model might have been changed by the activation of
 * this particular effects, they should have the ability to restore the
 * model to its previous state.
 * If they can do that, they must implement this interface.
 */
public interface EffectRollBack extends Serializable
{
	public void rollBack(Player player);
}
