package gameview;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerEndler {
	
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	
	
	public ServerEndler() throws IOException{
		Socket s = new Socket("localhost", 3000);
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
	}


	public ServerResponse send(ClientRequest request) throws IOException {
		out.writeObject(request);
		out.flush();
		ServerResponse response = null;
		try {
			response = (ServerResponse) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
