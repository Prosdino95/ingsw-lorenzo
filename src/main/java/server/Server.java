package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {
	
	private ExecutorService pool = Executors.newCachedThreadPool();
	private ServerSocket serverSocket;
	private GameManager gm;
	
	
	public static void main(String[]args) throws IOException, ClassNotFoundException{
		
		
		Server server=new Server();
		Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown(server.serverSocket)));		
		server.serverSocket=new ServerSocket(3001);
		server.start();
	}


	private void start() throws IOException {
		System.out.println("server start");
		while(true){
			Socket s=serverSocket.accept();
			if(gm==null || gm.getIsFull()){
				gm=new GameManager();
				pool.execute(gm);
			}
			System.out.println("ricevuta nuova connessione");
			HandlerView hv =new HandlerView(s);		
			gm.addHV(hv);			
		}
		
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