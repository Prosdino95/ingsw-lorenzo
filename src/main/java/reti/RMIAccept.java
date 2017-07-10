package reti;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * With socket, server side there's basically someone waiting for users to
 * connect. When this happens the server registers him and assigns him to the
 * current game.
 * With RMI, through the accept method, the client presents itself to the server.
 */
public interface RMIAccept extends Remote
{
	public HandlerViewRMI accept(HandlerServerRMI hsr) throws RemoteException;
}
