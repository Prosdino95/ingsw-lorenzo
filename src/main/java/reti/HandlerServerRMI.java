package reti;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMI interface exposed by the client to the server. The sendResponse 
 * method basically reproduces the socket behaviour of placing a response in the
 * response queue client side.
 */
public interface HandlerServerRMI extends Remote 
{
	public void sendResponse(ServerResponse sr) throws RemoteException, IOException;	
}
