package server;

import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import gamemodel.Player;
import gamemodel.RealGame;
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
			System.out.println("wwwwww");
		}
	}
	
	
	
	public static void main(String[]args) throws IOException, ClassNotFoundException
	{
		ServerSocket ss=new ServerSocket(3014);
		Socket s=null;
		System.out.println("server ready");
		s=ss.accept();
		EndlerView ev=new EndlerView(s);
		RealGame rg=new RealGame();
		rg.initializeGame();
		ev.setPlayer(rg.getPlayer());
		Controller c=new Controller(rg);
		ev.setController(c);
		ev.run();
		System.out.println(rg.getBoard().getActionSpace(0));
		
	}
	

}
