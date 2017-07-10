package reti.client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import gamemodel.jsonparsing.CustomizationFileReader;
import gameview.ViewController;
import reti.ClientRequest;
import reti.ServerResponse;

public class HandlerSocket implements Runnable,HandlerServer{
	
	private ObjectOutputStream out; 
	private ObjectInputStream in; 
	private Socket s;
	private ViewController vc;
	private ClientRequest crOut;
	private boolean live=true;
	private String serverIp;
	private int port;
	
	
	public HandlerSocket(ViewController vc) throws IOException, InterruptedException{
		setUpClient() ;
		s = new Socket(serverIp, port);
		out = new ObjectOutputStream(s.getOutputStream());
		in= new ObjectInputStream(s.getInputStream());
		this.vc=vc;
		
	}
	
	private void send(ClientRequest request) throws IOException {
		out.writeObject(request);
		out.flush();
		out.reset();
	}

	public void doRequest(ClientRequest request) {
		this.crOut=request;	
	}
	
	private void setUpClient() throws IOException{	
		String config = CustomizationFileReader.reedFile(new File("Config/ClientConfig.json"));
		JsonObject item=Json.parse(config).asObject();	
		port=item.getInt("port", 3003);
		serverIp=item.getString("server-ip", "localhost");
	}
	
	@Override
	public void run() 
	{
		try {
			while(live)
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
			shutdown();
			
		}
		
	}

	@Override
	public void sendResponse(ServerResponse sr) {		
	}

	@Override
	public void shutdown() {
		try {
			live=false;
			s.close();
			in.close();
			out.close();
		} catch (IOException e) {
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}		
	}
}
