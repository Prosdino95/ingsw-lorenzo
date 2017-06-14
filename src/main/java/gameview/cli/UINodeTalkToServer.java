package gameview.cli;

import java.io.IOException;


import gamemodel.Question;
import reti.ClientRequest;
import reti.ServerResponse;

public class UINodeTalkToServer extends UINode {

	public UINodeTalkToServer(String desc, UITree tree) {
		super(desc, tree);
	} 
	
	@Override
	public void run() throws IOException {
		ClientRequest request = tree.getRequest();
		boolean finishedTalking = false;
		ServerResponse response;
		
		do {
			response = tree.sendRequestToServer(request);
			System.out.print("UINodeTalkToServer -- Received response: ");
			System.out.println(response);

			switch (response.getType()) {
			case QUESTION:
				Question question = response.getQuestion();
				System.out.println(question);
				request = new ClientRequest(CLIView.getString());
				break;
			case ERROR:
				System.out.print("You can't do that because: ");
				System.out.println(response.getError());
				finishedTalking = true;
				break;
			case NEW_MODEL:
				System.out.println("UINodeTalkToServer -- Maybe make this be the ok response?");
				throw new RuntimeException();
//				break;
			case OK:
				System.out.println("Received ok from server");
				finishedTalking = true;
				break;
			case MESSAGE:
				System.out.println("UINodeTalkToServer -- AAAAAAAAAAAAAAAH");				
				throw new RuntimeException();
//				break;
			default:
				System.out.println("UINodeTalkToServer -- AAAAAAAAAAAAAAAH");				
				break;
			}
		} while (!finishedTalking);
		
		super.run();
	}
}
