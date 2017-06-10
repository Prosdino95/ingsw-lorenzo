package server;

import java.io.IOException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import gamemodel.Player;
import gamemodel.Question;
import gamemodel.Model;
import gamemodel.Team;
import gamemodel.command.GameException;
import gameview.ClientRequest;
import gameview.ModelShell;
import gameview.ServerResponse;

public class HandlerView implements Runnable{

	private Controller controller;
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private Player player;
	private boolean live=true;
	private boolean newModel=false;
	private boolean playerAssigned = false;
	private Socket socket;
	
	
	public HandlerView(Socket s) throws IOException{	
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
		this.socket=s;
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
		ClientRequest request=null;
		while(live){				
			try {
				if (socket.getInputStream().available() > 0) {
					request=readRequest();
					System.out.println("Server received: " + request);
					controller.doRequest(request,player);
				} else if (newModel) {
					sendResponse(new ServerResponse(controller.getModel()));
					newModel=false;
				} else if (!playerAssigned && player != null && newModel == false) {
					sendResponse(new ServerResponse(player));
					playerAssigned = true;
				}
				Thread.sleep(100);
			} catch (ClassNotFoundException | IOException | InterruptedException e) {
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
	
	 void sendResponse(ServerResponse sr) throws IOException {
			out.writeObject(sr);
			out.flush();
			out.reset();
	}
	
	ClientRequest readRequest() throws ClassNotFoundException, IOException{
		ClientRequest request=null;			
		request=(ClientRequest) in.readObject();				
		return request;
	}
	

	public static void main(String[]args) throws IOException, ClassNotFoundException
	{
		ServerSocket ss=new ServerSocket(3017);
		Socket s=null;
		System.out.println("server ready");
		s=ss.accept();
		HandlerView ev=new HandlerView(s);
		Model rg=new Model(4);
		ev.setPlayer(rg.getPlayer(Team.BLUE));
		Controller c=new Controller(rg);
		ev.setController(c);
		ev.run();
		System.out.println(rg.getBoard().getActionSpace(0));
		
	}

	public void setNewModel() {
		newModel=true;
		
	}

	public void setSendPlayer() {
		playerAssigned = false;
	}
}
