package server;

import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import gamemodel.Player;
import gamemodel.RealGame;
import gamemodel.command.GameException;
import gameview.ClientRequest;
import gameview.ServerResponse;

public class HandlerView implements Runnable{
	
	private Controller controller;
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private ClientRequest request;
	private Player player;
	private boolean live=true;
	
	
	public HandlerView(Socket s) throws IOException{	
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
	}
	
	public HandlerView()
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
		while(live){
			try {					
					request=(ClientRequest) in.readObject();
					System.out.println("receive from client"+request);
					ServerResponse sr;
					sr=controller.doRequest(request,player);
					System.out.println("send to client"+sr);
					out.writeObject(sr);
					out.flush();
					out.reset();
				}
			catch (ClassNotFoundException | IOException e) {
				try {
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
	
	
	
	public static void main(String[]args) throws IOException, ClassNotFoundException
	{
		ServerSocket ss=new ServerSocket(3017);
		Socket s=null;
		System.out.println("server ready");
		s=ss.accept();
		HandlerView ev=new HandlerView(s);
		RealGame rg=new RealGame(4);
		ev.setPlayer(rg.getPlayer());
		Controller c=new Controller(rg);
		ev.setController(c);
		ev.run();
		System.out.println(rg.getBoard().getActionSpace(0));
		
	}
	

}
