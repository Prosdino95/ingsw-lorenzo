package reti;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HandlerViewRMI extends Remote 
{
	public void doRequest(ClientRequest request) throws RemoteException;


	
}
