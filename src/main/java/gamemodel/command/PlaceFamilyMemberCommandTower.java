package gamemodel.command;

import gamemodel.*;
import gamemodel.ActionSpace.ActionSpace;
import gamemodel.ActionSpace.TowerActionSpace;
import gamemodel.card.Card;

public class PlaceFamilyMemberCommandTower implements Command {
	
	private FamilyMember f;
	private int servant;
	private TowerActionSpace t;
	
	public PlaceFamilyMemberCommandTower(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		this.t=(TowerActionSpace) board.getActionSpace(id);
	}

	public PlaceFamilyMemberCommandTower(Action action) {
		this.f = action.getFm();
		this.servant = action.getServants();
		this.t = (TowerActionSpace) action.getActionSpace();
	}

	private boolean IsEnoughtStrong(){
		return(f.getActionpoint() >= t.getActionCost());
	}

	private boolean controlServant() throws GameException{
		if(servant>=0)
			if(f.getPlayer().isEnoughtResource(new Resource(0,0,0,servant))){
				f.getPlayer().subResources(new Resource(0,0,0,servant));
				return true;
			}
		return false;			
	}

	@Override
	public void isLegal() throws GameException {
		if(!f.isUsed())
			if(t.isFree())
				if(t.getTower().controlPlayer(f))				
					if(t.getTower().isFree())
						if(IsEnoughtStrong())
							if(controlServant()){
								t.activateEffect(f);
									if(f.getPlayer().controlResourceAndPay(t.getCard())){
										f.getPlayer().getFamilyMember(f.getColor()).use();
										t.getTower().occupyTower();									
										t.giveCard(f);
										t.occupy();
										t.getTower().addPlayer(f);
										}
								else{
									t.rollbackEffect(f);
									throw new GameException(GameError.RESOURCE_ERR_CARD);	 									
								}
							}
							else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
						else throw new GameException(GameError.FM_ERR_PA);
					else throw new GameException(GameError.TWR_ERR_OCCUPY);					
				else throw new GameException(GameError.TWR_ERR_FM);
			else throw new GameException(GameError.SA_ERR);
		else throw new GameException(GameError.FM_ERR_USE);
	}
		
}

