package reti.server;

import java.io.IOException;


import gamemodel.Player;
import reti.ClientRequest;
import reti.ServerResponse;

public interface HandlerView  {
	
	void doRequest(ClientRequest request) throws IOException;
	void sendResponse(ServerResponse sr);
	Player getPlayer();
	void setController(Controller c);
	void setPlayer(Player p);
	void shutDown();

}
