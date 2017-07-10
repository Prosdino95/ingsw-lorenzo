package reti;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Symmetrical version of the HandlerServerRMI. The interface exposed by the 
 * server to replicate the socket behaviour of placing a request in the 
 * server side queue.
 */
public interface HandlerViewRMI extends Remote 
{
	public void doRequest(ClientRequest request) throws RemoteException;


	
}
