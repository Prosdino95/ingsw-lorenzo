package server;

import java.io.IOException;


import gamemodel.Player;
import gameview.ClientRequest;
import gameview.ServerResponse;

public interface HandlerView  {
	
	void doRequest(ClientRequest request) throws IOException;
	void sendResponse(ServerResponse sr);
	Player getPlayer();
	void setController(Controller c);
	void setPlayer(Player p);

}
