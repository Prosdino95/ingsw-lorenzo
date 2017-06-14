package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import gameview.HandlerServerRMI;

public interface RMIAccept extends Remote
{
	public HandlerViewRMI accept(HandlerServerRMI hsr) throws RemoteException;
}
