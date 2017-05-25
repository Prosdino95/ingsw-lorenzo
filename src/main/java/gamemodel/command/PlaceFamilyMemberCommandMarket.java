package gamemodel.command;

import gamemodel.*;
import gamemodel.ActionSpace.ActionSpace;

public class PlaceFamilyMemberCommandMarket implements Command {
	private FamilyMember f;
	private int servant;
	private ActionSpace a;
	
	public PlaceFamilyMemberCommandMarket(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		this.a=board.getActionSpace(id);
	}

	private boolean IsEnoughtStrong(){
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
			if(!f.isUsed())
				if(a.isFree())
					if(IsEnoughtStrong())
						if(controlServant()){
							f.use();
							a.activateEffect(f);
							a.occupy();				
							}
						else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
					else throw new GameException(GameError.FM_ERR_PA);
				else throw new GameException(GameError.SA_ERR);
			else throw new GameException(GameError.FM_ERR_USE);
		}		
}

