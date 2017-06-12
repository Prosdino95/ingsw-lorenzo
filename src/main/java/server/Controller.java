package server;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import gamemodel.Action;
import gamemodel.Player;
import gamemodel.Question;
import gamemodel.Model;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gameview.ClientRequest;
import gameview.ModelShell;
import gameview.ServerResponse;

public class Controller  {
	
	Model game;
	private Map<Player, HandlerViewSocket> playerToHV;
	
	
	public Controller(Model game){
		this.game=game;
	}
	
	public void doRequest(ClientRequest request, Player player) throws IOException {
		ServerResponse sr;
		HandlerViewSocket hv=playerToHV.get(player);
		switch(request.getType()){
		case FINISHACTION:
			finishAction();
			break;
		case PLACEFAMILYMEMBER: 
			sr=placeFM(request,player);
			System.out.println("Doing action: " + sr);
			hv.sendResponse(sr);
			break;	
		default:
			break;		
		}
	}
	
	public void notifyNewModel()
	{
		Collection<HandlerViewSocket> hw=playerToHV.values();
		for(HandlerViewSocket h:hw)
			h.setNewModel();	
	}
	
	private void finishAction() throws IOException {
		try {
			game.finishAction();
			notifyNewModel();
		} catch (GameException e) {
			throw new IOException();
		}
	}
	private ServerResponse placeFM(ClientRequest request, Player player) throws IOException{
		Action a =new Action(player,game.getBoard().getActionSpace(request.getWhere()),player.getFamilyMember(request.getWhich()),request.getServants());
		try {
			player.placeFamilyMember(a);
			return new ServerResponse();
		} catch (GameException e) {
			if(e.getType()==GameError.PLAYER_DEAD)
				throw new IOException();
			return new ServerResponse(e.getType());
		}
	}



	public void sendMessage(String string, Player player) throws GameException
	{
		HandlerViewSocket hv=playerToHV.get(player);
		ServerResponse sr = new ServerResponse(string);	
		try {
			hv.sendResponse(sr);
		} catch (IOException e) {
			throw new GameException(GameError.PLAYER_DEAD);
		}				
	}



	public void setPlayerToHV(Map<Player, HandlerViewSocket> playerToHV) {
		this.playerToHV = playerToHV;
	}



	public String answerToQuestion(Question gq, Player player) throws GameException {
		HandlerViewSocket hv=playerToHV.get(player);
		ServerResponse sr = new ServerResponse(gq);
		try {
			hv.sendResponse(sr);
			return (String)hv.readRequest().getAnswer();
		} catch (IOException |ClassNotFoundException e) {
			throw new GameException(GameError.PLAYER_DEAD);
		}		
	}

	public Model getModel() {
		return game;
	}

	public void notifySendPlayer() {
		Collection<HandlerViewSocket> hw=playerToHV.values();
		for(HandlerViewSocket h:hw)
			h.setSendPlayer();	
	}
}
