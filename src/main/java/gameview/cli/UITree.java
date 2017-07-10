package gameview.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.card.LeaderCard;
import gamemodel.player.FamilyMember;
import gamemodel.player.Player;
import gamemodel.player.Team;
import gameview.ViewController;
import gameview.gui.LeaderCardAction;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;

/**
 * The UITree object represents the structure we use to organize the command line interface;in particular we use 
 * a tree structure where the first nodes represent different kind of action like for example place family member or 
 * play leader card. Every time you go deep in the tree you're closer to complete your action. 
 * We have different kind of nodes and we have a class for each of them
 */






public class UITree {
	private UINode root;
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
	private long fakeDelay = 400;
	
	public UITree(List<Integer> intChoices, List<String> stringChoices, List<ServerResponse> messages, LinkedList<ServerResponse> responses2) {
		this((ViewController) null);
		this.fakeIntChoices = intChoices;
		this.stringChoices = stringChoices;
		this.messages = messages;
		this.responses = responses2;
	}
	
	public UITree(ViewController vc) {
		this.viewController=vc;

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
		
		UINodeChooseUI examineGame = new UINodeChooseUI("Examine game", this);
		UINode board = new UINode("The board", this) {
			public void run() {
				System.out.println(tree.getModel().getBoard());
			}
		}; 
		UINode players = new UINode("The players", this) {
			public void run() {
				for (Player p : this.tree.getModel().getPlayers()) {
					System.out.print(p + "\n");
				}
			}
		};
				

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
				examineGame
				.addSon(board)
				.addSon(players))
			  .addSon(
				finishTurn
				.addSon(
				  talkToServer))
			  .addSon(exit)); 
		
		reset();
		System.out.println("Hi, this is Lorenzo il Magnifico!");
	}

	protected void shutdown() {
		live=false;
		if (isOffline())
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
		if (isOffline())
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
		if (isOffline())
			throw new OfflineException();
		try {
			return Integer.parseInt(inKeyboard.readLine());
		} catch (NumberFormatException e) {
			return getInt();
		} catch (IOException e) {
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
			return 0;
		}
	}

	public String getString() throws OfflineException {
		if (isOffline())
			throw new OfflineException();
		String s = "0";
		try {
			s = inKeyboard.readLine();
		} catch (IOException e) {
			 Thread.currentThread().interrupt();
			 Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
		try{
			Integer.parseInt(s);
		}catch(NumberFormatException e){
			return getString();
		}		
		return s;
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
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
		Integer choice = fakeIntChoices.remove(0);
		System.out.println("Choose: " + choice);
		try {
			Thread.sleep(this.fakeDelay);
		} catch (InterruptedException e) {
			 Thread.currentThread().interrupt();
			 Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
		return choice;
	}

	public String getStringChoice() {
		try {
			Thread.sleep(this.fakeDelay );
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
		String choice = stringChoices.remove(0);
		System.out.println("Choose: " + choice);
		try {
			Thread.sleep(this.fakeDelay);
		} catch (InterruptedException e) {
			 Thread.currentThread().interrupt();
			 Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
		return choice;
	}

	public boolean hasMessage() {
		if (isOffline()) {
			return !messages.isEmpty();
		}
		return viewController.hasMessage();
	}
	
	boolean isOffline() {
		return viewController == null;
	}

	public ServerResponse getMessage() {
		if (isOffline()) {
			return messages.remove(0);
		}
		return viewController.getMessage();
	}
}