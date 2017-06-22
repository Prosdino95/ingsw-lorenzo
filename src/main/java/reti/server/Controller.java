package reti.server;

import java.io.IOException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import gamemodel.Action;
import gamemodel.Player;
import gamemodel.Question;
import gamemodel.Model;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import reti.ClientRequest;
import reti.ServerResponse;

public class Controller{
	
	Model game;
	private Map<Player, HandlerView> playerToHV;
	private Queue<ClientRequest> requestQueue=new ArrayDeque<>();
	private List<HandlerView> deadHVs = new ArrayList<>();
	
	
	public Controller(Model game){
		this.game=game;
	}
	
	public void doRequest(ClientRequest request){
		requestQueue.add(request);
	}
	
	public void run(){
		while(true){
			ServerResponse sr;
		if(this.requestQueue.isEmpty()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			continue;
		}
		
		ClientRequest request=requestQueue.remove();
		HandlerView hv=playerToHV.get(request.getPlayer());
		switch(request.getType()){
		case FINISHACTION:
			System.out.println("inviata da: "+request.getPlayer());
			finishAction(request.getPlayer(),hv);
			break;
		case PLACEFAMILYMEMBER: 
			sr=placeFM(request,request.getPlayer());
			hv.sendResponse(sr);
			break;
		case ANSWER:
			break;
		case CHAT:
			break;
		case IWANTAMODEL:
			break;
		case LEADERCARD:
			break;
		case VATICAN_REPORT:
			sr=vativan(request,request.getPlayer());
			System.out.println("Controller --- vticn " + sr);
			hv.sendResponse(sr);
			break;
		default:
			break;
		}	
		}
	}
	
	private ServerResponse vativan(ClientRequest request, Player player){
			try{
				player.vaticanReport(1, 0, 5);
				return new ServerResponse();
			}
			catch(GameException e){
				return new ServerResponse(e.getType());
			}
	}
	
	public void notifyNewModel() 
	{
		Collection<HandlerView> hw=playerToHV.values();
		for(HandlerView h:hw) {	
			h.sendResponse(new ServerResponse(game));
				
		}
	}
	
	private void finishAction(Player player, HandlerView hv) {
			try{
				game.finishAction(player);
				hv.sendResponse(new ServerResponse());
				notifyNewModel();				
				}
			catch(GameException e){
				hv.sendResponse(new ServerResponse(e.getType()));
			}			
	}
	private ServerResponse placeFM(ClientRequest request, Player player){
		Action a =new Action(player,game.getBoard().getActionSpace(request.getWhere()),player.getFamilyMember(request.getWhich()),request.getServants());
		try {
			player.placeFamilyMember(a);
			return new ServerResponse();
		} catch (GameException e) {
			return new ServerResponse(e.getType());
		}
	}



	public void sendMessage(String string, Player player)
	{
		HandlerView hv=playerToHV.get(player);
		ServerResponse sr = new ServerResponse(string);	
		hv.sendResponse(sr);
	}



	public void setPlayerToHV(Map<Player, HandlerView> playerToHV) {
		this.playerToHV = playerToHV;
	}



	public Integer answerToQuestion(Question gq, Player player) throws GameException{
		HandlerView hv=playerToHV.get(player);
		ServerResponse sr = new ServerResponse(gq);
			hv.sendResponse(sr);
			//TODO sistemare coda che potrebbe ricevere cose che non sono risponte
			while (!requestAvailable()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				if (deadHVs.contains(hv))
					throw new GameException(GameError.PLAYER_DEAD);
			}
			return Integer.parseInt((String)(requestQueue.remove()).getAnswer());
	}

	private boolean requestAvailable() {
		return !requestQueue.isEmpty();
	}

	public Model getModel() {
		return game;
	}

	public void imDead(HandlerView hvs) {
		deadHVs.add(hvs);
	}
}