package reti.server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.*;
import java.util.concurrent.*;

public class Server {
	
	private ExecutorService pool = Executors.newCachedThreadPool();
	private ServerSocket serverSocket;
	private GameManager gm;
	
	public static void main(String[]args) throws IOException, ClassNotFoundException, AlreadyBoundException{
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);		
		Server server=new Server();
		Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown(server.serverSocket)));		
		server.serverSocket=new ServerSocket(3003);			
		Registry registry = LocateRegistry.getRegistry();
		RMIAcceptImpl rai= new RMIAcceptImpl(server);
		registry.bind("rai",rai);
		System.out.println("RegistroPronto");
		server.start();
	}

	private void start() throws IOException {
		System.out.println("server start");
		gm=new GameManager();
		pool.execute(gm);
		while(true){
			Socket s=serverSocket.accept();				
			System.out.println("ricevuta nuova connessione");
			HandlerViewSocket hv =new HandlerViewSocket(s);		
			addHV(hv);			
		}		
	}
	
	public synchronized void addHV(HandlerView hv){
		if(gm == null || gm.getIsFull()) {
			gm = new GameManager();
			new Thread(gm).start();
		}
		gm.addHV(hv);
		
	}
}	

class Shutdown implements Runnable {
	private ServerSocket ss;

	public Shutdown(ServerSocket ss) {
		this.ss = ss;
	}
	
	public void run() {
		try {
			if(ss!=null)
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}