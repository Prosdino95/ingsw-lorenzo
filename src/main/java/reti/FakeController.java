package reti;

import gamemodel.Model;
import gamemodel.player.Player;
import reti.server.Controller;

/**
 * When testing the offline capabilities of the model, we needed to have a
 * fake controller that effectively does not send the requests. The 
 * FakeController has the methods that the model needs, but does nothing of 
 * what the model asks.
 */
public class FakeController extends Controller {

	public FakeController(Model game) {
		super(game);
	}

	public void notifyNewModel() {}
	public void sendMessage(String string, Player player) {}
	public void sendMessageToAll(String message){}
}
