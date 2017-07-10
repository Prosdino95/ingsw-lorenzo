package gameview.cli;

import java.io.IOException;
import gamemodel.Question;
import reti.ClientRequest;
import reti.ServerResponse;

/**
* The UINodeTalkToServer object represents some logic in tree structure and communication between 
* client and server,in particular when a player reaches a leaf node means that the action is finished 
* and makes a package request to be sent to server that has to confirm or not player's action
*/

public class UINodeTalkToServer extends UINode {

	public UINodeTalkToServer(String desc, UITree tree) {
		super(desc, tree);
	} 
	
	@Override
	public void run() throws IOException {
		ClientRequest request = tree.getRequest();
		boolean finishedTalking = false;
		ServerResponse response = null;
		
		do {
			response = tree.sendRequestToServer(request);

			switch (response.getType()) {
			case QUESTION:
				Question question = response.getQuestion();
				System.out.println(question);
				try {
					request = new ClientRequest(this.tree.getString());
				} catch (OfflineException e) {
				}
				break;
			case ERROR:
				System.out.print("You can't do that because: ");
				System.out.println(response.getError());
				finishedTalking = true;
				break;
			case NEW_MODEL:
				throw new AssertionError();
			case OK:
				System.out.println("Received ok from server");
				finishedTalking = true;
				break;
			case MESSAGE:			
				throw new AssertionError();
			default:
				throw new AssertionError();			
			}
		} while (!finishedTalking);
		
		super.run();
	}
}
