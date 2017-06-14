package reti;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIAccept extends Remote
{
	public HandlerViewRMI accept(HandlerServerRMI hsr) throws RemoteException;
}
