package server;

import gamemodel.Action;
import gamemodel.Player;
import gamemodel.RealGame;
import gamemodel.command.GameException;
import gameview.ClientRequest;

public class Controller {
	
	RealGame game;
	
	public Controller(RealGame game){
		this.game=game;
	}
	


	public void doRequest(ClientRequest request, Player player){
		Action a =new Action(player,game.getBoard().getActionSpace(request.getWhere()),player.getFamilyMember(request.getWhich()),request.getServants());
		try {
			player.placeFamilyMember(a);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
