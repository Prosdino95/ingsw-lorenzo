package gameview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import reti.ClientRequest;
import reti.ServerResponse;
import reti.client.HandlerServer;
import reti.client.HandlerSocket;

public class ViewController {
		
	private Queue<ServerResponse> serverMessages=new ArrayDeque<>();
	private ClientRequest crOut;
	private ServerResponse srIn = null;
	private HandlerServer hs;

	
	public ViewController() throws IOException, InterruptedException, NotBoundException{
		super();
		hs=new HandlerSocket(this);
		//hs=new HandlerServerRMIImpl(this);
		new Thread((Runnable) hs).start();
	}
	

	public boolean hasMessage()
	{
		return !serverMessages.isEmpty();
	}
	
	public void placeResponse(ServerResponse sr) throws IOException{
		
		switch(sr.getType())
		{
		case OK:
		case ERROR:	
		case QUESTION:  
					this.setSRIn(sr);
					break;
						
		case MESSAGE:
		case NEW_MODEL:
		case PLAYER_ASSIGNED:
					this.serverMessages.add(sr);
					break;
		default:
			System.out.println("What is going on ???");
			break;
		}	
	}


	public ServerResponse getMessage() 
	{
		return serverMessages.remove();
	}
	
	
	private void setSRIn(ServerResponse response){
		this.srIn=response;
		this.crOut=null;
	}


	private synchronized ServerResponse getSRIn() {
		ServerResponse temp=srIn;
		srIn=null;
		return temp;
	}

	public ServerResponse syncSend(ClientRequest request) {
		hs.doRequest(request);
		ServerResponse srr;
		while (true) { 
	    	try {
	    		Thread.sleep(100); 
	    	} catch (InterruptedException e) {
	    		e.printStackTrace(); 
	    	}     	
	    	srr = getSRIn(); 
	    	if (srr != null) return srr; 
	    } 
	}	
}


/*public static void main(String[] args) throws IOException, InterruptedException
{
	System.out.println("ciao");
	ExecutorService pool = Executors.newCachedThreadPool();
		try{BufferedReader inK = new BufferedReader(new InputStreamReader(System.in));
		ServerSocket ss=new ServerSocket(3001);
		Socket s=ss.accept();
		ObjectOutputStream out2 = new ObjectOutputStream(s.getOutputStream());	
		while(true){							
			ServerResponse sr= new ServerResponse(inK.readLine());
			out2.writeObject(sr);
			out2.flush();
			out2.reset();}
		}
		catch(Exception e){}
}*/
