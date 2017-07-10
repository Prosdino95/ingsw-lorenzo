package reti.server;



import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import gamemodel.Action;
import gamemodel.GameQuestion;
import gamemodel.Question;
import gamemodel.card.LeaderCard;
import gamemodel.Model;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;
import gameview.gui.LeaderCardAction;
import reti.ClientRequest;
import reti.ResponseType;
import reti.ServerResponse;

/**
 * The controller class is the link connecting the model of a game to its 
 * players. When a request reaches the controller, he gets the player
 * associated with the user, interprets the request, and call the right method
 * inside the model.
 */
public class Controller{
	
	Model game;
	private Map<Player, HandlerView> playerToHV;
	private Queue<ClientRequest> requestQueue=new ArrayDeque<>();
	private List<HandlerView> deadHVs = new ArrayList<>();
	private boolean live=true;
	
	
	public Controller(Model game){
		this.game=game;
	}
	
	public void doRequest(ClientRequest request){
		requestQueue.add(request);
	}
	
	public void run(){
		while(live){
			game.updateState();
					
			ServerResponse sr;
			if(this.requestQueue.isEmpty()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
					Thread.currentThread().interrupt();
				}
				continue;
			}

			
		ClientRequest request=requestQueue.remove();
		HandlerView hv=playerToHV.get(request.getPlayer());
		if(request.getPlayer().isDead())
			request.getPlayer().setAlive();
		switch(request.getType()){
		case FINISHACTION:
			try {
				request.getPlayer().finishAction();
				hv.sendResponse(new ServerResponse());				
			} 
			catch (GameException e) {
				hv.sendResponse(new ServerResponse(e.getType()));
			}			
			break;
		case PLACEFAMILYMEMBER:
				sr=placeFM(request,request.getPlayer());
				hv.sendResponse(sr);
			break;
		case VATICAN_REPORT:
			hv.getPlayer().vaticanReport(Integer.parseInt(request.getAnswer()));
			hv.sendResponse(new ServerResponse());
			break;
		case CHAT:
			break;
		case IWANTAMODEL:
			break;
		case LEADERCARD:
			Integer id = request.getLeaderCardID();
			LeaderCardAction action = request.getAction();
			Player p = hv.getPlayer();
			switch (action) {
			case ACTIVATE:
				try {
					p.activateOPT(id);
					hv.sendResponse(new ServerResponse());
				} catch (GameException e) {
					hv.sendResponse(new ServerResponse(e.getType()));
				}
				break;
			case DISCARD:
				try {
					p.discardLC(id);
					hv.sendResponse(new ServerResponse());
				} catch (GameException e) {
					hv.sendResponse(new ServerResponse(e.getType()));
				}
				break;
			case PLAY:
				try {
					p.playLC(id);
					hv.sendResponse(new ServerResponse());
				} catch (GameException e) {
					hv.sendResponse(new ServerResponse(e.getType()));
				}
				break;
			default:
				break;
			}
			this.notifyNewModel();
			break;
		case ANSWER:
			assert(false);
			break;
		case IWANTMONEY:
			Player pl = hv.getPlayer();
			game.sendMessage("Before -> Resources: " + pl.getResource() + " Points: " + pl.getPoint(), pl);
			pl.addResources(new Resource(100, 100, 100, 100));
			pl.addPoint(new Point(100, 100, 100));
			game.sendMessage("After -> Resources: " + pl.getResource() + " Points: " + pl.getPoint(), pl);
			hv.sendResponse(new ServerResponse());
			break;
		default:
			break;
		}	
		}
	}
	
	public void giveLeaderCard() {
		int index=0,i,j;
		Random random=new Random();
		List<Object> cards=new ArrayList<>(); 
		sendMessageToAll("Now it's time to choose your leader cards, wait for your turn");
		for(i=0;i<4;i++){
			for(j=0;j<4;j++){
				index=random.nextInt(game.getLeaderCards().size());
				cards.add(game.getLeaderCards().remove(index));
			}			
			for(HandlerView hv:playerToHV.values())
				try {
					index=answerToQuestion(new Question(GameQuestion.LEADER,cards),hv);
					hv.getPlayer().giveLeaderCard((LeaderCard)cards.remove(index));
					hv.sendResponse(new ServerResponse());
				} catch (GameException e) {
					game.giveLeaderCard(hv.getPlayer(), 0);
				}
			cards.clear();
		}
	}

	public void notifyNewModel() 
	{
		Collection<HandlerView> hw=playerToHV.values();
		for(HandlerView h:hw) {	
			h.sendResponse(new ServerResponse(game));
				
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
			while (!requestAvailable()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {						
					Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
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

	public void shutDown() {
		for(HandlerView hv:playerToHV.values())
			hv.shutDown();
		live=false;		
	}


}
