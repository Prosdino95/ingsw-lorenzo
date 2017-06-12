package gameview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewController {
		
	private Queue<ServerResponse> serverMessages=new ArrayDeque<>();
	private ClientRequest crOut;
	private ServerResponse srIn = null;

	
	public ViewController() throws IOException, InterruptedException{
		super();
	}
	
	public boolean hasMessage()
	{
		return !serverMessages.isEmpty();
	}
	
	public void receve(ServerResponse sr) throws IOException{
		
		if(sr.isThereAMessage())
			serverMessages.add(sr);
		else if(sr.assignedPlayer())
			serverMessages.add(sr);
		else if(sr.isThereANewModel())
			serverMessages.add(sr);
		else
			System.out.println("Something strange got here!!!");	
	}


	public ServerResponse getMessage() 
	{
		return serverMessages.remove();
	}
	
	public void setCROut(ClientRequest request) {
		this.crOut=request;
		
	}
	
	public synchronized ClientRequest getCROut() {
		return this.crOut;		
	}
	
	public void setSRIn(ServerResponse response){
		this.srIn=response;
		this.crOut=null;
	}


	public synchronized ServerResponse getSRIn() {
		return srIn;
	}

	public ServerResponse syncSend(ClientRequest request) {
		setCROut(request);
		ServerResponse srr;
		while (true) { 
	    	try {
	    		Thread.sleep(100); 
	    	} catch (InterruptedException e) {
	    		e.printStackTrace(); 
	    	}     	
	    	srr =getSRIn(); 
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
