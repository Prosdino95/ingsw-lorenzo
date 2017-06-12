package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gamemodel.Player;
import gameview.ClientRequest;
import gameview.ServerResponse;

public class HandlerViewRMI extends UnicastRemoteObject implements HandlerView {
	
	private Controller controller;
	private Player player;

	private static final long serialVersionUID = 1L;

	protected HandlerViewRMI() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doRequest(ClientRequest request) throws IOException  {
		controller.doRequest(request, player);		
	}

	@Override
	public void sendResponse(ServerResponse sr) throws IOException {
		
		
	}

}
