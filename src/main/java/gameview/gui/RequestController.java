package gameview.gui;


import java.io.IOException;
import java.util.List;

import gamemodel.Color;
import gamemodel.Player;
import gamemodel.Team;
import gameview.ViewController;
import gameview.gui.actionspacecontroll.MakeFM;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import reti.ClientRequest;
import reti.RequestType;
import reti.ResponseType;
import reti.ServerResponse;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class RequestController {
	private ClientRequest cr = new ClientRequest();
	
	private GuiView gv;
	@FXML Pane serverResponse;
	@FXML TextFlow currentRequestFlow;
	@FXML AnchorPane root;	
	@FXML TextFlow servText;
	@FXML Pane blackFM;
	@FXML Pane whiteFM;
	@FXML Pane orangeFM;
	@FXML Pane uncoloredFM;
	private GuiQuestionController questionController;


	public void initialize(GuiView gv) throws IOException {
		this.gv=gv;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/GuiQuestion.fxml"));
		serverResponse.getChildren().add(loader.load());
		questionController= loader.getController();
		servText.getChildren().add(new Text("Add servant"));
		blackFM.setOnMouseClicked(e -> {
			getCr().setWhich(Color.BLACK);
			showCurrentRequest();
		});
		orangeFM.setOnMouseClicked(e -> {
			getCr().setWhich(Color.ORANGE);
			showCurrentRequest();
		});
		uncoloredFM.setOnMouseClicked(e -> {
			getCr().setWhich(Color.UNCOLORED);
			showCurrentRequest();
		});
		whiteFM.setOnMouseClicked(e -> {
			getCr().setWhich(Color.WHITE);
			showCurrentRequest();
		});
		
		
	}
	
	void update() {
		showCurrentRequest();
	}

	public void generateFM() {
		Player p=gv.getPlayer();
		MakeFM.makeFM(blackFM, p.getFamilyMember(Color.BLACK),60,40);
		MakeFM.makeFM(orangeFM, p.getFamilyMember(Color.ORANGE),60,40);	
		MakeFM.makeFM(whiteFM, p.getFamilyMember(Color.WHITE),60,40);	
		MakeFM.makeFM(uncoloredFM, p.getFamilyMember(Color.UNCOLORED),60,40);	
	}

	public void setActionSpace(Integer id) {
//		System.out.println("RequestController -- ActionSpace " + id + " got set.");
		getCr().setWhere(id);
		showCurrentRequest();
	}
	
	public void showCurrentRequest() {
		Text t = new Text(this.getCr().toString());
		List<Node> children = currentRequestFlow.getChildren();
		while (!children.isEmpty()) children.remove(0);
		this.currentRequestFlow.getChildren().add(t);
	}
	
	public void addServant(){
		getCr().setServants(getCr().getServants() + 1);
		showCurrentRequest();
	}
	
	public void subServant(){
		if(getCr().getServants()>0)
			getCr().setServants(getCr().getServants() -1);
		showCurrentRequest();
	}
	
	public void sendRequest(){
		ServerResponse sr = null;
		if(gv.getState()==GUIState.VATICAN){
			setCr(new ClientRequest(questionController.getAnswer(),RequestType.VATICAN_REPORT));
			gv.setRequest(getCr());
		}
		if(gv.getState()==GUIState.QUESTION) {
			setCr(new ClientRequest(questionController.getAnswer()));
			gv.setRequest(getCr());
		} else {
			gv.setRequest(getCr());
		}
		questionController.clear();
		System.out.println("RequestController -- Receve: " + sr);
		System.out.println("RequestController -- Sent: " + getCr());
		setCr(new ClientRequest());
		showCurrentRequest();
	}
	

	public void finishAction(){
		System.out.println("RequestController -- Sending a finish action");
		gv.setRequest(new ClientRequest());
		showCurrentRequest();
	}

	public void giveSR(ServerResponse sr) {
		questionController.clear();
		questionController.update(sr);	
	}

	public ClientRequest getCr() {
		return cr;
	}

	public void setCr(ClientRequest cr) {
		this.cr = cr;
	}

	public void setPlayerTurn(Player player) {
		questionController.setCurrentPlayer(player);	
	}

}
