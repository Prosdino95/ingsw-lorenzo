package server;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

import gamemodel.Player;
import gamemodel.command.GameException;
import gameview.ClientRequest;

public class EndlerView {
	
	private Controller controller;
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private ClientRequest request;
	private Player player;
	
	
	public EndlerView(Socket s) throws IOException{	
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
	}
	
	
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setController(Controller c){
		this.controller=c;
	}
	
	public void run() throws ClassNotFoundException, IOException{
		while(true){
			request=(ClientRequest) in.readObject();
			controller.doRequest(request,player);
		}
	}
	
	
	
	
	

}
