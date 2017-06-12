package gameview;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandlerSocket implements Runnable,HandlerServer{
	
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private Socket s;
	private ViewController vc;
	private ClientRequest crOut;
	
	
	public HandlerSocket(ViewController vc) throws IOException, InterruptedException{
		s = new Socket("localhost", 3003);
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
		this.vc=vc;
		
	}
	
	private void send(ClientRequest request) throws IOException {
		ServerResponse response=null;
		out.writeObject(request);
		out.flush();
		out.reset();
		System.out.println("Sent to server: " + request);
	}

	public void doRequest(ClientRequest request) {
		this.crOut=request;
		
	}
	
	private synchronized ClientRequest getCROut() {
		return this.crOut;		
	}
	@Override
	public void run() 
	{
		System.out.println("run");
		try {
			while(true)
			{
				Thread.sleep(100);
				if(s.getInputStream().available()>1)       
				{
					ServerResponse sr=(ServerResponse)(in.readObject());
					vc.placeResponse(sr);
				}	
				if(crOut!=null)
				{
					this.send(crOut);
					crOut=null;
				}
			}
			
		} catch (ClassNotFoundException | IOException | InterruptedException  e) {
			try {
				in.close();
				out.close();
				s.close();
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
}
