package reti.client;

import java.io.IOException;
import java.rmi.RemoteException;

import reti.ClientRequest;
import reti.ServerResponse;

public interface HandlerServer 
{

	void doRequest(ClientRequest request);	
	void sendResponse(ServerResponse sr) throws RemoteException,IOException;

}
