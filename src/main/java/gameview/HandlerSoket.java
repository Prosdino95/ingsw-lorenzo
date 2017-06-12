package gameview;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandlerSoket implements Runnable{
	
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private Socket s;
	private ViewController vc;
	
	
	public HandlerSoket(ViewController vc) throws IOException, InterruptedException{
		s = new Socket("localhost", 3003);
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
		this.vc=vc;
		
	}
	
	public void doRequest(ClientRequest request) throws IOException {
		ServerResponse response=null;
		out.writeObject(request);
		out.flush();
		out.reset();
		System.out.println("Sent to server: " + request);
		try {
			response = (ServerResponse) (in.readObject());
			System.out.println("receive from server"+response);
		} catch (ClassNotFoundException | IOException e) {
			in.close();
			out.close();
			s.close();
			e.printStackTrace();
		}		
		vc.setSRIn(response);
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
					vc.receve(sr);
				}	
				if(vc.getCROut()!=null)
				{
					srIn= this.send(crOut);
					crOut=null;
				}
			}
			
		} catch (ClassNotFoundException | IOException | InterruptedException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}
