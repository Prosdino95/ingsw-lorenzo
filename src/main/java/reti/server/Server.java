package reti.server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.*;
import java.util.List;
import java.util.concurrent.*;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Server {
	
	private ExecutorService pool = Executors.newCachedThreadPool();
	private ServerSocket serverSocket;
	private GameManager gm;
	private boolean live=true;
	
	private int delay;
	private int port;
	private int gameDelay;
	
	public static void main(String[]args) throws IOException, ClassNotFoundException, AlreadyBoundException{
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);		
		Server server=new Server();
		server.setUpServer();
		Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown(server)));		
		server.serverSocket=new ServerSocket(server.port);			
		Registry registry = LocateRegistry.getRegistry();
		RMIAcceptImpl rai= new RMIAcceptImpl(server);
		registry.bind("rai",rai);
		System.out.println("RegistroPronto");
		server.start();
	}

	private void setUpServer() throws IOException {
		Path path = FileSystems.getDefault().getPath("Config/ServerConfig.json");
		List<String> jsonFile=Files.readAllLines(path);
		StringBuilder builder = new StringBuilder();
		for(String s : jsonFile) {
		    builder.append(s);
		}
		String config = builder.toString();
		JsonObject item=Json.parse(config).asObject();	
		port=item.getInt("port", 3003);
		delay=item.getInt("server-delay", 1000);
		gameDelay=item.getInt("game-deley", 200000);
	}

	private void start() throws IOException {
		System.out.println("server start");
		gm=new GameManager(delay,gameDelay);
		pool.execute(gm);
		while(live){
			Socket s=serverSocket.accept();				
			System.out.println("ricevuta nuova connessione");
			HandlerViewSocket hv =new HandlerViewSocket(s);		
			addHV(hv);			
		}		
	}
	
	public synchronized void addHV(HandlerView hv){
		if(gm == null || gm.getIsFull()) {
			gm = new GameManager(delay,gameDelay);
			new Thread(gm).start();
		}
		gm.addHV(hv);
		
	}
	
	public void Shutdown(){
		try {
			serverSocket.close();
			live=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}	

class Shutdown implements Runnable {
	private Server s;

	public Shutdown(Server s) {
		this.s = s;
	}
	
	public void run() {
		s.Shutdown();
	}
	
}