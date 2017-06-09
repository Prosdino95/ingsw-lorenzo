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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerServer implements Runnable {
	
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private Socket s;
	private Queue<ServerResponse> serverMessages=new ArrayDeque<>();
	private ClientRequest crOut;
	private ServerResponse srIn;
	
	
	public HandlerServer() throws IOException, InterruptedException{
		s = new Socket("localhost", 3001);
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
	}


	public ServerResponse send(ClientRequest request) throws IOException {
		ServerResponse response=null;
		out.writeObject(request);
		out.flush();
		out.reset();
		// System.out.println("Sent to server: " + request);
		try {
			response = (ServerResponse) in.readObject();
			// System.out.println("receive from server"+response);
		} catch (ClassNotFoundException | IOException e) {
			in.close();
			out.close();
			s.close();
			e.printStackTrace();
		}		
		return response;
	}


	@Override
	public void run() 
	{
		System.out.println("run");
		try {
			while(true)
			{
				if(s.getInputStream().available()>0)       
				{
					ServerResponse sr=(ServerResponse)(in.readObject());
					if(sr.isThereAMessage())
						serverMessages.add(sr);
					else 
						srIn=sr;
					
					System.out.println(sr);
					
				}
				
				if(crOut!=null)
				{
					srIn= this.send(crOut);
					crOut=null;
				}
				
			}
			
		} catch (ClassNotFoundException | IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean hasMessage()
	{
		return !serverMessages.isEmpty();
	}


	public ServerResponse getMessage() 
	{
		return serverMessages.remove();
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		System.out.println("ciao");
		ExecutorService pool = Executors.newCachedThreadPool();
		new Thread(new Runnable() {
			public void run(){
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
			}
		}).start();	
	}
	
}
