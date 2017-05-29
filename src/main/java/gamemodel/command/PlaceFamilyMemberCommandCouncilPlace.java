package gamemodel.command;

import gamemodel.*;
import gamemodel.ActionSpace.*;

public class PlaceFamilyMemberCommandCouncilPlace implements Command {
	private FamilyMember f;
	private int servant;
	private ActionSpace a;
	
	public PlaceFamilyMemberCommandCouncilPlace(Board board,int id,FamilyMember f, int servant) {
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
			MemoryActionSpace h=(MemoryActionSpace)a;	
			if(!f.isUsed())
				if(h.getPlayers().size()<4)
					if(IsEnoughtStrong())
						if(controlServant()){
								f.use();
								h.activateEffect(f);
								h.addPlayer(f);
							}
						else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
					else throw new GameException(GameError.FM_ERR_PA);
				else throw new GameException(GameError.SA_MAX_FM);
			else throw new GameException(GameError.FM_ERR_USE);
		}
}
