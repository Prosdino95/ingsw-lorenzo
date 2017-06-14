package server;


import java.rmi.Remote;
import java.rmi.RemoteException;

import gameview.ClientRequest;

public interface HandlerViewRMI extends Remote 
{
	public void doRequest(ClientRequest request) throws RemoteException;


	
}
