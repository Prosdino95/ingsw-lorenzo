package server;

import gamemodel.Action;
import gamemodel.Player;
import gamemodel.RealGame;
import gamemodel.command.GameException;
import gameview.ClientRequest;
import gameview.ModelShell;
import gameview.ServerResponse;

public class Controller {
	
	RealGame game;
	private ServerResponse sr;
	
	public Controller(RealGame game){
		this.game=game;
	}
	


	public ServerResponse doRequest(ClientRequest request, Player player){
		sr=new ServerResponse();
		switch(request.getType()){
		case IWANTAMODEL:sr.setModel(new ModelShell(game.getBoard(),game.getPlayer()));
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
}
