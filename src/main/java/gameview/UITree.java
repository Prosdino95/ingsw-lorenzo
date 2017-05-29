package gameview;

import gamemodel.*;
import gamemodel.ActionSpace.*;

public class UITree {
	private UINodeChooseUI root;
	private UINode next;
	public ClientResponse response = new ClientResponse();
	
	
	public UITree(Board b, Player p) {
		UINodeSetResponseType placeFM = 
				new UINodeSetResponseType("Place family member", 
						response::setType,
						ResponseType.PLACEFAMILYMEMBER);
//		UINodeSetResponseType sendMessage = 
//			new UINode("Chat", 
//					response::setType, 
//					ResponseType.CHAT);

		UINodeChooseValue<ActionSpace> where = 
				new UINodeChooseValue<ActionSpace>("Where?",
					response::setWhere,
					b::getActionSpaces);
		UINodeChooseValue<FamilyMember> which = 
				new UINodeChooseValue<FamilyMember>("Which?",
						response::setWhich,
						p::getFamilyMembers);		
		UINodeGetInput servants= 
				new UINodeGetInput("How many servants?",
						response::setServants);
		
		root= new UINodeChooseUI("Root")
				.addSon(placeFM
						.addSon(where
								.addSon(which
										.addSon(servants))))
				.addSon(new UINodeChooseUI("state"));
//				.addSon(new UINodeChoose("Chat"));
		
		reset();

		System.out.println("Hi, this is Lorenzo!");
	}

	public void run() {
		while (true) {
			while (next != null) {
				next.run();
				next = next.getNextNode();
			}
			System.out.println(response);
			reset();
		}
	}

	public void reset() {
		next = root;
		response.cleanUp();
	}
}