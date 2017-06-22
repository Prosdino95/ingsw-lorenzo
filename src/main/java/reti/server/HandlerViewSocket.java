package reti.server;

import java.io.IOException;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import gamemodel.Player;
import gamemodel.Question;
import gamemodel.Model;
import gamemodel.Team;
import gamemodel.command.GameException;
import reti.ClientRequest;
import reti.ServerResponse;

public class HandlerViewSocket implements Runnable,HandlerView{

	private Controller controller;
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private Player player;
	private boolean live=true;
	private Socket socket;
	private Queue<ServerResponse> responseQueue=new ArrayDeque<>();
	
	
	public HandlerViewSocket(Socket s) throws IOException{	
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
		this.socket=s;
	}
	
	public HandlerViewSocket()
	{
		super();
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
	
	public void run(){
		ClientRequest request=null;
		while(live){				
			try {
				if (socket.getInputStream().available() > 1) {
					request=readRequest();
					//System.out.println("Server received: " + request);
					doRequest(request);					
				} 
				Thread.sleep(100);
				if(!responseQueue.isEmpty())
					send(this.responseQueue.remove());
					
			} catch (ClassNotFoundException | IOException | InterruptedException e) {
				try {
					controller.imDead(this);
					in.close();
					out.close();
					live=false;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}							
		}
	}
	
	@Override
	public void sendResponse(ServerResponse sr){
		this.responseQueue.add(sr);
	}
	
	 private void send(ServerResponse sr) throws IOException {
		// System.out.println("server sent response " + sr);
			out.writeObject(sr);
			out.flush();
			out.reset();
	}
	
	ClientRequest readRequest() throws ClassNotFoundException, IOException{
		ClientRequest request=null;			
		request=(ClientRequest) in.readObject();				
		System.out.println("Server reading request " + request);
		return request;
	}

	@Override
	public void doRequest(ClientRequest request) {
		request.setPlayer(player);
		controller.doRequest(request);
	}

}
