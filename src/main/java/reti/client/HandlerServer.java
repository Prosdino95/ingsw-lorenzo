package reti.client;

import java.io.IOException;
import java.rmi.RemoteException;

import reti.ClientRequest;
import reti.ServerResponse;

/**
 * A HandlerServer object is that which effectively sends to and receives 
 * messages from the server. This object resides on the client, its methods
 * are called exclusively from the ViewController.
 */
public interface HandlerServer 
{

	void doRequest(ClientRequest request);	
	void sendResponse(ServerResponse sr) throws RemoteException,IOException;
	void shutdown();

}
