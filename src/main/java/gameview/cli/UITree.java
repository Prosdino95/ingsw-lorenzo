package gameview.cli;

import java.io.IOException;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpace;
import gameview.ViewController;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;

public class UITree {
	private UINode root;
	private UINode next;
	private ClientRequest request = new ClientRequest();
	private ViewController serverHandler;
	private Model model;
	private Player player;
	boolean hasModel = false;
	boolean hasPlayer = false;
	
		
	public UITree(ViewController serverEndler) {
		this.serverHandler=serverEndler;

// 		// Riusciremo a infilarlo nell'albero un giorno?
//		UINodeSetResponseType sendMessage = 
//		new UINode("Chat", 
//				response::setType, 
//				ResponseType.CHAT);

		UINodeLog log = new UINodeLog("", this, serverEndler);
		UINodeChooseUI menu = new UINodeChooseUI("Menu'", this); 
		UINodeSetRequestType placeFM = 
				new UINodeSetRequestType("Place family member", 
						request::setType, 
						RequestType.PLACEFAMILYMEMBER, this);
		UINodeSetRequestType finishTurn = 
				new UINodeSetRequestType("Notify finish action",
						request::setType,
						RequestType.FINISHACTION, this);
				new UINodeSetRequestType("Place family member", 
						request::setType, 
						RequestType.PLACEFAMILYMEMBER, this);
		UINodeChooseValue<ActionSpace> where = 
				new UINodeChooseValue<ActionSpace>("Where?",
						request::setWhere,
						() -> this.getModel().getBoard().getActionSpaces(),
						this);
		UINodeChooseValue<FamilyMember> which =
				new UINodeChooseValue<FamilyMember>("Which?",
						request::setWhich,
						() -> this.getPlayer().getFamilyMembers(),
						this);
		UINodeGetInput servants= 
				new UINodeGetInput("How many servants?",
						request::setServants, this);
		UINode talkToServer = new UINodeTalkToServer("Waiting for server response...", this);
		UINodeSetRequestType vatican= new UINodeSetRequestType("Talk to the Pope", 
										request::setType, 
										RequestType.VATICAN_REPORT, this);
		
		root= log.addSon(
				menu.addSon(
				  placeFM.addSon(
	                where.addSon(
	                  which.addSon(
	                	servants.addSon(
	                	  talkToServer)))))
				   // .addSon(
				  //vatican.addSon(
				   // talkToServer))
				    .addSon(
				  finishTurn.addSon(
					talkToServer))); 
		
		reset();
		System.out.println("Hi, this is Lorenzo!");
	}

	Player getPlayer() {
		return player;
	}

	Model getModel() {
		return model;
	}
	
	ServerResponse sendRequestToServer() throws IOException {
		return sendRequestToServer(request);
	}

	public ServerResponse sendRequestToServer(ClientRequest request) throws IOException { 		
	    ServerResponse srr = serverHandler.syncSend(request);
	    return srr;
	} 

	public void run() throws IOException {
		while (true) {
			while (next != null) {
				next.run();
				next = next.getNextNode();
			}
			reset();
		}
	}

	private void reset() {
		next = root;
		request.cleanUp();
	}


	public ClientRequest getRequest() {
		return request;
	}

	public void setModel(Model model2) {
		model = model2;
		hasModel = true;
	}

	public void setPlayer(Team playerTeam) {
		player = model.getPlayer(playerTeam);
		hasPlayer = true;
	}
}