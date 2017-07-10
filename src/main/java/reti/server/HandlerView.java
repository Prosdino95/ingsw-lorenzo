package reti.server;

import java.io.IOException;

import gamemodel.player.Player;
import reti.ClientRequest;
import reti.ServerResponse;

/**
 * What the server side Controller needs to be able to talk to the client,
 * the object which implements the HandlerView object represents the single
 * user.
 */
public interface HandlerView  {
	
	void doRequest(ClientRequest request) throws IOException;
	void sendResponse(ServerResponse sr);
	Player getPlayer();
	void setController(Controller c);
	void setPlayer(Player p);
	void shutDown();

}
