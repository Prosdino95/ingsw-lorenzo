package gamemodel.command;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.TowerActionSpace;

public class PlaceFamilyMemberCommandMarket implements Command {
	private FamilyMember f;
	private int servant;
	private ActionSpace a;
	private Action action;
	
	public PlaceFamilyMemberCommandMarket(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		this.a=board.getActionSpace(id);
	}
	
	public PlaceFamilyMemberCommandMarket(Action action) {
		this.f = action.getFm();
		this.servant = action.getServants();
		this.a = action.getActionSpace();
		this.action=action;
	}
	
	
	
	
	private boolean IsEnoughtStrong(){
		return(f.getActionpoint()>=a.getActionCost());
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
				if(a.isAccessible(action))
					if(IsEnoughtStrong())
						if(controlServant()){
							f.getPlayer().getFamilyMember(f.getColor()).use();
							a.setFamilyMember(f);
							a.activateEffect(f);
							a.occupy();				
							}
						else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
					else throw new GameException(GameError.FM_ERR_PA);
				else throw new GameException(GameError.SA_ERR); // non distingue il motivo dell'eccezione
			else throw new GameException(GameError.FM_ERR_USE);
		}		
}

