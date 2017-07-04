package gameview.gui;

import java.util.List;

import gamemodel.Color;
import gamemodel.Question;
import gamemodel.command.GameError;
import gameview.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class RequestController {
	ClientRequest cr = new ClientRequest();
	
	@FXML Pane servants;
	@FXML Pane sendRequest;
	@FXML Pane finishAction;
	@FXML Pane serverResponse;
	@FXML TextFlow currentRequestFlow;
	
	private ViewController vc;

	@FXML TextFlow servText;
	@FXML TextFlow sendText;
	@FXML TextFlow finishText;

	@FXML Pane blackFM;
	@FXML Pane whiteFM;
	@FXML Pane orangeFM;
	@FXML Pane uncoloredFM;

	public void initialize(ViewController vc) {
		servText.getChildren().add(new Text("Add servant"));
		sendText.getChildren().add(new Text("Send request"));
		finishText.getChildren().add(new Text("Finish action"));
		
		this.vc = vc;
		
		sendRequest.setOnMouseClicked(e -> {
			System.out.println("RequestController -- Sending: " + cr);
			if (vc == null) {
				System.out.println("RequestController -- Maybe you should turn the network on?");
			} else {
				ServerResponse sr = vc.syncSend(cr);
				// Pass it to serverResponseController
			}
			cr = new ClientRequest();
			showCurrentRequest();
		});
		
		finishAction.setOnMouseClicked(e -> {
			System.out.println("RequestController -- Sending a finish action");
			if (vc != null) {
				vc.syncSend(new ClientRequest(RequestType.FINISHACTION));
			}
			cr = new ClientRequest();
			showCurrentRequest();
		});
		
		servants.setOnMouseClicked(e -> {
			// This sucks
			cr.setServants(cr.getServants() + 1);
			showCurrentRequest();
//			System.out.println("RequestController -- Incrementing servants, now: " + cr.getServants());
		});
		
		blackFM.setOnMouseClicked(e -> {
			cr.setWhich(Color.BLACK);
			showCurrentRequest();

		});
		orangeFM.setOnMouseClicked(e -> {
			cr.setWhich(Color.ORANGE);
			showCurrentRequest();

		});
		uncoloredFM.setOnMouseClicked(e -> {
			cr.setWhich(Color.UNCOLORED);
			showCurrentRequest();

		});
		whiteFM.setOnMouseClicked(e -> {
			cr.setWhich(Color.WHITE);
			showCurrentRequest();
		});
		
		
	}

	public void setActionSpace(Integer id) {
//		System.out.println("RequestController -- ActionSpace " + id + " got set.");
		cr.setWhere(id);
		showCurrentRequest();
	}
	
	public void showCurrentRequest() {
		Text t = new Text(this.cr.toString());
		List<Node> children = currentRequestFlow.getChildren();
		while (!children.isEmpty()) children.remove(0);
		this.currentRequestFlow.getChildren().add(t);
	}

	public void giveSR(ServerResponse sr) {
		
	}
}
