package gameview;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandlerServer {
	
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private Socket s;
	
	
	public HandlerServer() throws IOException{
		s = new Socket("localhost", 3001);
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
	}


	public ServerResponse send(ClientRequest request) throws IOException {
		out.writeObject(request);
		out.flush();
		out.reset();
		System.out.println("send to server"+request);
		ServerResponse response = null;
		try {
			response = (ServerResponse) in.readObject();
			System.out.println("receive from server"+response);
		} catch (ClassNotFoundException | IOException e) {
			in.close();
			out.close();
			s.close();
			e.printStackTrace();
		}		
		return response;
	}
}
