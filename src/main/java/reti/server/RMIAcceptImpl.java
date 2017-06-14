package reti.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import reti.HandlerServerRMI;
import reti.HandlerViewRMI;
import reti.RMIAccept;

public class RMIAcceptImpl extends UnicastRemoteObject implements RMIAccept {

	private static final long serialVersionUID = 1L;
	private Server server;

	protected RMIAcceptImpl(Server server) throws RemoteException 
	{
		this.server=server;

	}
	public HandlerViewRMI accept(HandlerServerRMI hsr) throws RemoteException 
	{
		HandlerViewRMI hvr=new HandlerViewRMIImpl(hsr);
		server.addHV((HandlerViewRMIImpl)hvr);
		return hvr;
	}
	
}
