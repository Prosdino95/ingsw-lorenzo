package gameview.gui;


import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.text.Font;
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
	private List<Pane>fm=new ArrayList<>();


	public void initialize(GuiView gv) throws IOException {
		this.gv=gv;
		fm.add(blackFM);
		fm.add(whiteFM);
		fm.add(orangeFM);
		fm.add(uncoloredFM);
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
		for(Pane p:fm)
			p.getChildren().clear();
		if(gv.getPlayer()!=null)
			generateFM();
	}
	
	public void generateFM() {
		Player p=gv.getPlayer();
		Font font=new Font(20);
		if(!p.getFamilyMember(Color.BLACK).isUsed()){
			MakeFM.makeFM(blackFM, p.getFamilyMember(Color.BLACK),60,40);
			Text blackPoint=new Text(""+p.getFamilyMember(Color.BLACK).getActionpoint());
			blackPoint.setFill(javafx.scene.paint.Color.WHITE);
			blackPoint.setFont(font);
			blackFM.getChildren().add(new TextFlow(blackPoint));
		}
		if(!p.getFamilyMember(Color.ORANGE).isUsed()){
			MakeFM.makeFM(orangeFM, p.getFamilyMember(Color.ORANGE),60,40);
			Text orangePoint=new Text(""+p.getFamilyMember(Color.ORANGE).getActionpoint());
			orangePoint.setFont(font);
			orangeFM.getChildren().add(new TextFlow(orangePoint));
		}
		if(!p.getFamilyMember(Color.WHITE).isUsed()){
			MakeFM.makeFM(whiteFM, p.getFamilyMember(Color.WHITE),60,40);	
			Text whitePoint=new Text(""+p.getFamilyMember(Color.WHITE).getActionpoint());
			whitePoint.setFont(font);
			whiteFM.getChildren().add(new TextFlow(whitePoint));
		}
		if(!p.getFamilyMember(Color.UNCOLORED).isUsed()){
			MakeFM.makeFM(uncoloredFM, p.getFamilyMember(Color.UNCOLORED),60,40);	
			Text uncoloredPoint=new Text(""+p.getFamilyMember(Color.UNCOLORED).getActionpoint());
			uncoloredPoint.setFont(font);
			uncoloredFM.getChildren().add(new TextFlow(uncoloredPoint));
		}
	}

	public void setActionSpace(Integer id) {
//		System.out.println("RequestController -- ActionSpace " + id + " got set.");
		getCr().setWhere(id);
		showCurrentRequest();
	}
	
	public void showCurrentRequest() {
		Text t = new Text(this.getCr().toString());
		List<Node> children = currentRequestFlow.getChildren();
		if (!children.isEmpty()) children.clear();
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
		System.out.println(gv.getState());
		if(gv.getState()==GUIState.IDLE) return;
		if(gv.getState()==GUIState.VATICAN){
			setCr(new ClientRequest(questionController.getAnswer(),RequestType.VATICAN_REPORT));
			gv.setRequest(getCr());
		}
		else if(gv.getState()==GUIState.QUESTION || gv.getState()==GUIState.LEADER) {
			setCr(new ClientRequest(questionController.getAnswer()));
			gv.setRequest(getCr());
		} else {
			gv.setRequest(getCr());
		}
		questionController.clear();
		System.out.println("RequestController -- Sent: " + getCr());
		setCr(new ClientRequest());
		showCurrentRequest();
	}
	

	public void finishAction(){
		if(gv.getState()!=GUIState.ACTION) return;
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
