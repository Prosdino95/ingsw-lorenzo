package reti.server;



import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import gamemodel.Action;
import gamemodel.GameQuestion;
import gamemodel.LeaderCard;
import gamemodel.Player;
import gamemodel.Question;
import gamemodel.Model;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import reti.ClientRequest;
import reti.ResponseType;
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
				Thread.currentThread().interrupt();
			}
			continue;
		}
		
		ClientRequest request=requestQueue.remove();
		HandlerView hv=playerToHV.get(request.getPlayer());
		switch(request.getType()){
		case FINISHACTION:
			//System.out.println("inviata da: "+request.getPlayer());
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
		default:
			break;
		}	
		}
	}
	
	public void giveLeaderCard() {
		//TODO ordine turno
		int index;
		sendMessageToAll("now it's time to choose your leaders, so wait your turn");	
			for(int i=0;i<4;i++)
				for(HandlerView hv:playerToHV.values()){
					try {
						index=answerToQuestion(new Question(GameQuestion.LEADER,game.getLeaderCards()),hv);
						game.giveLeaderCard(hv.getPlayer(), index);
						hv.sendResponse(new ServerResponse());
					} catch (GameException e) {
						game.giveLeaderCard(hv.getPlayer(), 0);
					}				
				}
			sendMessageToAll("iniziamo a giocare");
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
				player.finishAction();
				notifyNewModel();
				hv.sendResponse(new ServerResponse());
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

	
	public Integer answerToQuestion(Question gq, Player player)throws GameException{
		HandlerView hv=playerToHV.get(player);
		return answerToQuestion(gq,hv);
	}


	public Integer answerToQuestion(Question gq, HandlerView hv) throws GameException{
		ServerResponse sr;
		if(gq.getGq()==GameQuestion.LEADER)
			sr = new ServerResponse(gq,ResponseType.LEADER);
		else
			sr = new ServerResponse(gq);			
		hv.sendResponse(sr);
		//TODO sistemare coda che potrebbe ricevere cose che non sono risponte
			while (!requestAvailable()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {						
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				if (deadHVs.contains(hv))
					throw new GameException(GameError.PLAYER_DEAD);
			}
			try{
				return Integer.parseInt((String)(requestQueue.remove()).getAnswer());
			}
			catch(NumberFormatException e){
				answerToQuestion(gq,hv);
			}
			return 0;
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

	public void sendOK(Player player) {
		playerToHV.get(player).sendResponse(new ServerResponse());
		
	}
	
	public void sendMessageToAll(String message){
		for(HandlerView hv:playerToHV.values())
			hv.sendResponse(new ServerResponse(message));
	}


}