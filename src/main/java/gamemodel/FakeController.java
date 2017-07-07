package gamemodel;

import reti.server.Controller;

public class FakeController extends Controller {

	public FakeController(Model game) {
		super(game);
	}

	public void notifyNewModel() {}
	public void sendMessage(String string, Player player) {}
}
