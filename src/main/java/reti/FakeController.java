package reti;

import gamemodel.Model;
import gamemodel.player.Player;
import reti.server.Controller;

public class FakeController extends Controller {

	public FakeController(Model game) {
		super(game);
	}

	public void notifyNewModel() {}
	public void sendMessage(String string, Player player) {}
	public void sendMessageToAll(String message){}
}
