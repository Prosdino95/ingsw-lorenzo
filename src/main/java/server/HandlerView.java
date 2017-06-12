package server;

import java.io.IOException;
import java.rmi.Remote;

import gamemodel.Player;
import gameview.ClientRequest;
import gameview.ServerResponse;

public interface HandlerView extends Remote {
	
	void doRequest(ClientRequest request) throws IOException;
	 void sendResponse(ServerResponse sr) throws IOException;

}
