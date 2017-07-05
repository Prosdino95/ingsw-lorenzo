package gameview.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpace;
import gameview.ViewController;
import gameview.gui.LeaderCardAction;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;

// TODO: Cosa succede se tutte le scelte vanno in error?
// TODO: Finale di partita

public class UITree {
	private UINode root;
	private UINode prev;
	private UINode next;
	private ClientRequest request = new ClientRequest();
	private ViewController viewController;
	private Model model;
	private Player player;
	boolean hasModel = false;
	boolean hasPlayer = false;
	private boolean live=true;
	
	BufferedReader inKeyboard = new BufferedReader(new InputStreamReader(System.in));
	private List<Integer> fakeIntChoices;
	private List<String> stringChoices;
	private List<ServerResponse> messages;
	private List<ServerResponse> responses;
	private long fakeDelay = 1000;
	
	public UITree(List<Integer> intChoices, List<String> stringChoices, List<ServerResponse> messages, LinkedList<ServerResponse> responses2) {
		this((ViewController)null);
		this.fakeIntChoices = intChoices;
		this.stringChoices = stringChoices;
		this.messages = messages;
		this.responses = responses2;
	}
	
	public UITree(ViewController vc) {
		this.viewController=vc;

// 		// Riusciremo a infilarlo nell'albero un giorno?
//		UINodeSetResponseType sendMessage = 
//		new UINode("Chat", 
//				response::setType, 
//				ResponseType.CHAT);

		UINodeLog log = new UINodeLog("", this, vc);
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
		
		UINodeChooseValue<LeaderCardAction> whatLeader = 
				new UINodeChooseValue<>("What do you want to do with it?", 
						request::setAction,
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
//			  .addSon(
//			    giveMeMoney
//			    .addSon(
//			      talkToServer))
			  .addSon(exit)); 
		
		reset();
		System.out.println("Hi, this is Lorenzo!");
	}

	protected void shutdown() {
		live=false;
		if (viewController == null)
			return;
		viewController.shutdown();
		
	}

	Player getPlayer() {
		return player;
	}

	Model getModel() {
		return model;
	}
	
	ServerResponse sendRequestToServer() {
		return sendRequestToServer(request);
	}

	public ServerResponse sendRequestToServer(ClientRequest request) {
		ServerResponse srr;
		if (viewController == null)
			return this.responses.remove(0);
		else 
			srr = viewController.syncSend(request);
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

	public int getInt() throws OfflineException {
		if (viewController == null)
			throw new OfflineException();
		try {
			return Integer.parseInt(inKeyboard.readLine());
		} catch (NumberFormatException e) {
			return getInt();
		} catch (IOException e) {
			e.printStackTrace();
			return getInt();
		}
	}

	public String getString() throws OfflineException {
		if (viewController == null)
			throw new OfflineException();
		try {
			return inKeyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getString();
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

	public Integer getChoice() {
		try {
			Thread.sleep(this.fakeDelay);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		Integer choice = fakeIntChoices.remove(0);
		System.out.println("Chose: " + choice);
		return choice;
	}

	public String getStringChoice() {
		try {
			Thread.sleep(this.fakeDelay );
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		String choice = stringChoices.remove(0);
		System.out.println("Chose: " + choice);
		return choice;
	}

	public boolean hasMessage() {
		if (viewController == null) {
			return !messages.isEmpty();
		}
		return viewController.hasMessage();
	}

	public ServerResponse getMessage() {
		if (viewController == null) {
			return messages.remove(0);
		}
		return viewController.getMessage();
	}
}