package gameview.gui;

import java.io.IOException;
import java.util.List;

import gamemodel.Color;
import gamemodel.Question;
import gamemodel.command.GameError;
import gamemodel.Player;
import gamemodel.Team;
import gameview.ViewController;
import gameview.gui.actionspacecontroll.MakeFM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class RequestController {
	ClientRequest cr = new ClientRequest();
	
	private GuiView gv;
	private ViewController vc;
	@FXML Pane serverResponse;
	@FXML TextFlow currentRequestFlow;
	@FXML AnchorPane root;	
	@FXML TextFlow servText;
	@FXML Pane blackFM;
	@FXML Pane whiteFM;
	@FXML Pane orangeFM;
	@FXML Pane uncoloredFM;

	public void initialize(ViewController vc) throws IOException {
		//this.gv=gv;
		this.vc = vc;
		root.setBackground(new Background(new BackgroundImage(new Image("/wood.jpg"), null, null, null, null)));
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/GuiQuestion.fxml"));
		serverResponse.getChildren().add(loader.load());
		GuiQuestionController questionController= loader.getController();
		servText.getChildren().add(new Text("Add servant"));
		generateFM();				
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

	private void generateFM() {
		Player p=new Player(null,null,Team.RED);
		MakeFM.makeFM(blackFM, p.getFamilyMember(Color.BLACK),60,40);
		MakeFM.makeFM(orangeFM, p.getFamilyMember(Color.ORANGE),60,40);	
		MakeFM.makeFM(whiteFM, p.getFamilyMember(Color.WHITE),60,40);	
		MakeFM.makeFM(uncoloredFM, p.getFamilyMember(Color.UNCOLORED),60,40);	
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
	
	public void addServant(){
		cr.setServants(cr.getServants() + 1);
		showCurrentRequest();
	}
	
	public void subServant(){
		if(cr.getServants()>0)
			cr.setServants(cr.getServants() -1);
		showCurrentRequest();
	}
	
	public void sendRequest(){
		System.out.println("RequestController -- Sending: " + cr);
		if (vc == null) {
			System.out.println("RequestController -- Maybe you should turn the network on?");
		} else {
			ServerResponse sr = vc.syncSend(cr);
			//TODO Pass it to serverResponseController
		}
		cr = new ClientRequest();
		showCurrentRequest();
	}
	
	public void finishAction(){
		System.out.println("RequestController -- Sending a finish action");
		if (vc != null) {
			vc.syncSend(new ClientRequest(RequestType.FINISHACTION));
		}
		cr = new ClientRequest();
		showCurrentRequest();
	}

	public void giveSR(ServerResponse sr) {
		
	}
}
