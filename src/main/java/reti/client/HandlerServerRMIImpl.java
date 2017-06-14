package reti.client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gameview.ViewController;
import reti.ClientRequest;
import reti.HandlerServerRMI;
import reti.HandlerViewRMI;
import reti.RMIAccept;
import reti.ServerResponse;

public class HandlerServerRMIImpl extends UnicastRemoteObject implements HandlerServerRMI, HandlerServer {
	
	private static final long serialVersionUID = 1L;
	private ViewController vc;
	private HandlerViewRMI hv;
	
	protected HandlerServerRMIImpl(ViewController viewController) throws RemoteException, NotBoundException {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendResponse(ServerResponse sr) throws IOException,RemoteException {
		vc.placeResponse(sr);
	}
}
