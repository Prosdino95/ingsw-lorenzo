package reti;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HandlerServerRMI extends Remote 
{
	public void sendResponse(ServerResponse sr) throws RemoteException, IOException;	
}
