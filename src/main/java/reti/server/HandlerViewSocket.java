package reti.server;

import java.io.IOException;



import java.io.*;
import java.net.*;
import java.util.ArrayDeque;

import java.util.Queue;

import gamemodel.Player;
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
			} 
			catch (ClassNotFoundException | IOException | InterruptedException e) {
				shutDown();				
			}		
		}
	}
	
	@Override
	public void sendResponse(ServerResponse sr){
		this.responseQueue.add(sr);
	}
	
	 private void send(ServerResponse sr) throws IOException {
		 System.out.println("server sent response " + sr);
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

	@Override
	public void shutDown() {
		controller.imDead(this);
		live=false;
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
