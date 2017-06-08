package server;

import java.util.List;
import java.util.Map;

import gamemodel.Action;
import gamemodel.Player;
import gamemodel.Question;
import gamemodel.Model;
import gamemodel.command.GameException;
import gameview.ClientRequest;
import gameview.ModelShell;
import gameview.ServerResponse;

public class Controller {
	
	Model game;
	private ServerResponse sr;
	private Map<Player, HandlerView> playerToHV;
	
	
	public Controller(Model game){
		this.game=game;
	}
	


	public ServerResponse doRequest(ClientRequest request, Player player){
		sr=new ServerResponse();
		switch(request.getType()){
		case IWANTAMODEL:sr.setModel(new ModelShell(game.getBoard(),player));
		break;
		case PLACEFAMILYMEMBER: return PlaceFM(request,player);
		default:
			break;
		}
		return sr;	
	}
	
	private ServerResponse PlaceFM(ClientRequest request, Player player){
		Action a =new Action(player,game.getBoard().getActionSpace(request.getWhere()),player.getFamilyMember(request.getWhich()),request.getServants());
		try {
			player.placeFamilyMember(a);
			return sr;
		} catch (GameException e) {
			sr.setError(e.getType());
			return sr;
		}
	}



	public void sendMessage(String string, Player player) 
	{
		playerToHV.get(player).sendMessage(string);
				
	}



	public void setPlayerToHV(Map<Player, HandlerView> playerToHV) {
		this.playerToHV = playerToHV;
	}



	public String answerToQuestion(Question gq, Player player) {
		return (String)(playerToHV.get(player).answerToQuestion(gq));
	}
}
