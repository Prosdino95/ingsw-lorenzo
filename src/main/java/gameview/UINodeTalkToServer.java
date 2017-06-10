package gameview;

import java.io.IOException;


import gamemodel.Question;

public class UINodeTalkToServer extends UINode {

	public UINodeTalkToServer(String desc, UITree tree) {
		super(desc, tree);
	} 
	
	@Override
	public void run() throws IOException {
		ClientRequest request = tree.getRequest();
		ServerResponse response = tree.sendRequestToServer(request);
		boolean finishedTalking = false;

		do {
//			System.out.println("Client -- Received response:");
//			System.out.println(response);
//			System.out.println();

			switch (response.getType()) {
			case QUESTION:
				Question question = response.getQuestion();
				System.out.println(question);
				ClientRequest cr = new ClientRequest(CLIView.getString());
				response = tree.sendRequestToServer();
				break;
			case ERROR:
				System.out.print("You can't do that because: ");
				System.out.println(response.getError());
				finishedTalking = true;
				break;
			case NEW_MODEL:
				System.out.println("Maybe make this be the ok response?");
				throw new RuntimeException();
//				break;
			case OK:
				System.out.println("Client -- Received ok from server");
				finishedTalking = true;
				break;
			case MESSAGE:
				throw new RuntimeException();
//				break;
			default:
				break;
			}
		} while (!finishedTalking);
		
		super.run();
	}
}
