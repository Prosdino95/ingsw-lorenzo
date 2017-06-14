package gameview;

import java.io.IOException;
import java.rmi.RemoteException;

public interface HandlerServer 
{

	void doRequest(ClientRequest request);	
	void sendResponse(ServerResponse sr) throws RemoteException,IOException;

}
