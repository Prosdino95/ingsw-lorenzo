package gamemodel.command;

import gamemodel.*;
import gamemodel.ActionSpace.ActionSpace;
import gamemodel.ActionSpace.TowerActionSpace;
import gamemodel.card.Card;

public class PlaceFamilyMemberCommandTower implements Command {
	
	private FamilyMember f;
	private int servant;
	private ActionSpace a;
	
	public PlaceFamilyMemberCommandTower(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		this.a=board.getActionSpace(id);
	}

	private boolean IsEnoughtStrong(){
		//TODO enum....
		TowerActionSpace t=(TowerActionSpace)a;
		ModForza e=(ModForza) f.getPlayer().getPermanentEffect("MOD_FORZA");
		if(e.getCtype().equals(t.getTower().getType()))		
			return(f.getActionpoint()+servant+e.getModForza()>=a.getActionCost());
		return(f.getActionpoint()+servant>=a.getActionCost());
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
		TowerActionSpace t=(TowerActionSpace)a;
		if(!f.isUsed())
			if(t.isFree())
				if(t.getTower().controlPlayer(f))				
					if(t.getTower().isFree())
						if(IsEnoughtStrong())
							if(controlServant()){
								t.activateEffect(f);
									if(CardControl(t.getCard(),f.getPlayer())){
										f.use();
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

	private boolean CardControl(Card c, RealPlayer p) {
		if(c.ControlResource(p)){
			c.pay(p);
			return true;
		}
			
		else return false;
	}
		
}

