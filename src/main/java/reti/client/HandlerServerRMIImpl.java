package reti.client;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import gamemodel.jsonparsing.CustomizationFileReader;
import gameview.ViewController;
import reti.ClientRequest;
import reti.HandlerServerRMI;
import reti.HandlerViewRMI;
import reti.RMIAccept;
import reti.ServerResponse;

public class HandlerServerRMIImpl extends UnicastRemoteObject implements HandlerServerRMI, HandlerServer {
	
	private static final long serialVersionUID = 1L;
	private transient ViewController vc;
	private transient HandlerViewRMI hv;
	private String serverIp;
	private Registry registry;
	
	public HandlerServerRMIImpl(ViewController viewController) throws NotBoundException, IOException {
		setUpClient();
		registry=LocateRegistry.getRegistry(serverIp);
		RMIAccept acc=(RMIAccept) registry.lookup("rai");
		this.hv=acc.accept(this);
		this.vc=viewController;
	}

	@Override
	public void doRequest(ClientRequest request){
		try {
			hv.doRequest(request);
		} catch (RemoteException e) {
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
	}

	@Override
	public void sendResponse(ServerResponse sr) throws IOException,RemoteException {
		vc.placeResponse(sr);
	}

	@Override
	public void shutdown() {
		
	}
	
	private void setUpClient() throws IOException{	
		String config = CustomizationFileReader.reedFile(new File("Config/ClientConfig.json"));
		JsonObject item=Json.parse(config).asObject();	
		serverIp=item.getString("server-ip", "localhost");
	}
	
}
