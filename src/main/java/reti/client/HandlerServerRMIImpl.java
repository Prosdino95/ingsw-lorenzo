package reti.client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import gameview.ViewController;
import reti.ClientRequest;
import reti.HandlerServerRMI;
import reti.HandlerViewRMI;
import reti.RMIAccept;
import reti.ServerResponse;

public class HandlerServerRMIImpl extends UnicastRemoteObject implements HandlerServerRMI, HandlerServer {
	
	private static final long serialVersionUID = 1L;
	private transient ViewController vc;
	private transient HandlerViewRMI hv;
	
	public HandlerServerRMIImpl(ViewController viewController) throws RemoteException, NotBoundException {
		Registry r=LocateRegistry.getRegistry();
		RMIAccept acc=(RMIAccept) r.lookup("rai");
		this.hv=acc.accept(this);
		this.vc=viewController;
	}

	@Override
	public void doRequest(ClientRequest request){
		try {
			hv.doRequest(request);
		} catch (RemoteException e) {
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
	}

	@Override
	public void sendResponse(ServerResponse sr) throws IOException,RemoteException {
		vc.placeResponse(sr);
	}

	@Override
	public void shutdown() {		
	}
}
