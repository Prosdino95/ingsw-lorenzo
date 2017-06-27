package gameview.cli;

import java.io.IOException;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpace;
import gameview.ViewController;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;

// TODO: Cosa succede se tutte le scelte vanno in error?
// TODO: Finale di partita
// TODO: Carte leader, funzionano?
// TODO: Forse giveMeMoney e' da rimuovere
// TODO: Mentre navigo l'albero non posso tornare indietro?

public class UITree {
	private UINode root;
	private UINode next;
	private ClientRequest request = new ClientRequest();
	private ViewController serverHandler;
	private Model model;
	private Player player;
	boolean hasModel = false;
	boolean hasPlayer = false;
	private boolean live=true;
	
		
	public UITree(ViewController serverEndler) {
		this.serverHandler=serverEndler;

// 		// Riusciremo a infilarlo nell'albero un giorno?
//		UINodeSetResponseType sendMessage = 
//		new UINode("Chat", 
//				response::setType, 
//				ResponseType.CHAT);

		UINodeLog log = new UINodeLog("", this, serverEndler);
		UINodeChooseUI menu = new UINodeChooseUI("Menu'", this); 
		UINodeSetRequest placeFM = 
				new UINodeSetRequest("Place family member", 
						request::setType, 
						RequestType.PLACEFAMILYMEMBER, this);
		UINodeSetRequest finishTurn = 
				new UINodeSetRequest("Notify finish action",
						request::setType,
						RequestType.FINISHACTION, this);
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
		
		UINode leaderCards = new UINodeSetRequest("Leader cards", 
				request::setType, 
				RequestType.LEADERCARD, this);
		
		UINodeChooseValue<LeaderCard> whichLeader = 
				new UINodeChooseValue<LeaderCard>("Which?", 
						request::setWhichLeaderCard, 
						() -> this.getPlayer().getLCList(), 
						this);
		
		UINodeChooseValue<String> whatLeader = 
				new UINodeChooseValue<String>("What do you want to do with it?", 
						request::setWhatLC,
						request::possibleLeaderCardActions,
						this);
		
		UINodeSetRequest giveMeMoney = 
				new UINodeSetRequest("I WANT money. Now.", 
						request::setType, 
						RequestType.IWANTMONEY, this);
		UINode exit= new UINode("exit",this){				
			@Override
			public void run(){
				tree.shutdown();
			}
		};
		
		root= log
			  .addSon(
				menu
				.addSon(
				  placeFM
				  .addSon(
	                where
	                .addSon(
	                  which
	                  .addSon(
	                	servants
	                	.addSon(
	                	  talkToServer)))))
			  .addSon(
				leaderCards
				.addSon(
				  whichLeader
				  .addSon(
				    whatLeader
				    .addSon(
				      talkToServer))))
			  .addSon(
				finishTurn
				.addSon(
				  talkToServer))
			  .addSon(
			    giveMeMoney
			    .addSon(
			      talkToServer))
			  .addSon(exit)); 
		
		reset();
		System.out.println("Hi, this is Lorenzo!");
	}

	protected void shutdown() {
		live=false;
		serverHandler.shutdown();
		
	}

	Player getPlayer() {
		return player;
	}

	Model getModel() {
		return model;
	}
	
	ServerResponse sendRequestToServer(){
		return sendRequestToServer(request);
	}

	public ServerResponse sendRequestToServer(ClientRequest request){ 		
	    ServerResponse srr = serverHandler.syncSend(request);
	    return srr;
	} 

	public void run() throws IOException {
		while (live) {
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
		if (player != null)
			player = model2.getPlayer(player.getTeam());
		hasModel = true;
	}

	public void setPlayer(Team playerTeam) {
		player = model.getPlayer(playerTeam);
		hasPlayer = true;
	}
}