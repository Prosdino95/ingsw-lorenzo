package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gamemodel.Player;
import gameview.ClientRequest;
import gameview.HandlerServerRMI;
import gameview.ServerResponse;

public class HandlerViewRMIImpl extends UnicastRemoteObject implements HandlerView,HandlerViewRMI {
	
	private Controller controller;
	private Player player;
	private HandlerServerRMI hsr;

	private static final long serialVersionUID = 1L;

	protected HandlerViewRMIImpl(HandlerServerRMI hsr) throws RemoteException {
		super();
		this.hsr=(HandlerServerRMI) hsr;
	}

	@Override
	public void doRequest(ClientRequest request) throws RemoteException  {
		request.setPlayer(player);
		controller.doRequest(request);		
	}

	@Override
	public void sendResponse(ServerResponse sr) {
		try {			
			hsr.sendResponse(sr);
		} catch (IOException e) {
			controller.imDead(this);
		}
		
		
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setController(Controller c) {
		this.controller=c;
		
	}

	@Override
	public void setPlayer(Player p) {
		this.player=p;
		
	}

}
