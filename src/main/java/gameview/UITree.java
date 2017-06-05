package gameview;

import java.io.IOException;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.command.GameError;

public class UITree {
	private UINodeChooseUI root;
	private UINode next;
	private ClientRequest request = new ClientRequest();
	private ServerEndler serverEndler;
	private ModelShell ms;
	
	public UITree(Board b, Player p, ServerEndler serverEndler) {
		this.serverEndler=serverEndler;
		UINodeSetRequestType placeFM = 
				new UINodeSetRequestType("Place family member", 
						request::setType,
						RequestType.PLACEFAMILYMEMBER, this);
//		UINodeSetResponseType sendMessage = 
//			new UINode("Chat", 
//					response::setType, 
//					ResponseType.CHAT);

		UINodeChooseValue<ActionSpace> where = 
				new UINodeChooseValue<ActionSpace>("Where?",
					request::setWhere,
					ms::getActionSpaces, this);
		UINodeChooseValue<FamilyMember> which = 
				new UINodeChooseValue<FamilyMember>("Which?",
						request::setWhich,
						ms::getFamilyMembers, this);		
		UINodeGetInput servants= 
				new UINodeGetInput("How many servants?",
						request::setServants, this);
		
		UINode talkToServer = new UINode("Waiting for server response...", this) {
			@Override
			public void run() throws IOException {
				ServerResponse response = tree.sendRequestToServer();

				while (!response.isItOk()) {

					System.out.println("Client -- Received response:");
					System.out.println(response);
					System.out.println();
					
					if (response.isThereAQuestion()) {
						ServerQuestion serverQuestion = response.getQuestion();
						System.out.println(serverQuestion.getQuestion());
						serverQuestion.setAnswer(CLIView.getString());
						request = serverQuestion.getRequest();
						tree.sendRequestToServer();
					} else if (response.isThereAnError()) {
						System.out.print("You can't do that because: ");
						print(response.getError());
					} else {
						int a = 1/0;
					}
				}
				
				System.out.println("Client -- Received ok from server");
				System.out.println();
				
				return;
			}
		};
		root= new UINodeChooseUI("Root", this)
				.addSon(placeFM
						.addSon(where
								.addSon(which
										.addSon(servants
											.addSon(talkToServer)))))
				.addSon(new UINodeChooseUI("state", this));
//				.addSon(new UINodeChoose("Chat"));
		
		reset();

		System.out.println("Hi, this is Lorenzo!");
		
	}
	
	
	public UITree(Board b, Player p) {
		UINodeSetRequestType placeFM = 
				new UINodeSetRequestType("Place family member", 
						request::setType,
						RequestType.PLACEFAMILYMEMBER, this);
//		UINodeSetResponseType sendMessage = 
//			new UINode("Chat", 
//					response::setType, 
//					ResponseType.CHAT);

		UINodeChooseValue<ActionSpace> where = 
				new UINodeChooseValue<ActionSpace>("Where?",
					request::setWhere,
					b::getActionSpaces, this);
		UINodeChooseValue<FamilyMember> which = 
				new UINodeChooseValue<FamilyMember>("Which?",
						request::setWhich,
						p::getFamilyMembers, this);		
		UINodeGetInput servants= 
				new UINodeGetInput("How many servants?",
						request::setServants, this);
		
		UINode talkToServer = new UINode("Waiting for server response...", this) {
			@Override
			public void run() throws IOException {
				ServerResponse response = tree.sendRequestToServer();

				while (!response.isItOk()) {

					System.out.println("Client -- Received response:");
					System.out.println(response);
					System.out.println();
					
					if (response.isThereAQuestion()) {
						ServerQuestion serverQuestion = response.getQuestion();
						System.out.println(serverQuestion.getQuestion());
						serverQuestion.setAnswer(CLIView.getString());
						request = serverQuestion.getRequest();
						tree.sendRequestToServer();
					} else if (response.isThereAnError()) {
						System.out.print("You can't do that because: ");
						print(response.getError());
						tree.reset();
					} else if (response.isThereANewModel()) {
						System.out.println("Updated model...");
						ms = response.getModel();
					}
					
					else {
						int a = 1/0;
					}
				}
				
				System.out.println("Client -- Received ok from server");
				System.out.println();
				
				return;
			}
		};



		root= new UINodeChooseUI("Root", this)
				.addSon(placeFM
						.addSon(where
								.addSon(which
										.addSon(servants
											.addSon(talkToServer)))))
				.addSon(new UINodeChooseUI("state", this));
//				.addSon(new UINodeChoose("Chat"));
		
		reset();

		System.out.println("Hi, this is Lorenzo!");
	}



	protected void print(Object error) {
		System.out.println(error);
	}

	protected ServerResponse sendRequestToServer() throws IOException {
		return serverEndler.send(request);
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

	public void reset() {
		next = root;
		request.cleanUp();
	}
}