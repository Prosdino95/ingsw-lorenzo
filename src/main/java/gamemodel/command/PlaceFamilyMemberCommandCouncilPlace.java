package gamemodel.command;

import gamemodel.*;
import gamemodel.actionSpace.*;
import gamemodel.player.FamilyMember;
import gamemodel.player.Resource;

public class PlaceFamilyMemberCommandCouncilPlace implements Command {
	private FamilyMember f;
	private int servant;
	private MemoryActionSpace h;
	
	public PlaceFamilyMemberCommandCouncilPlace(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		this.h=(MemoryActionSpace) board.getActionSpace(id);
	}
	
	public PlaceFamilyMemberCommandCouncilPlace(Action action) {
		this.f = action.getFm();
		this.servant = action.getServants();
		this.h = (MemoryActionSpace) action.getActionSpace();
	}
	
	private boolean IsEnoughtStrong(){
		return(f.getActionpoint()+servant>=h.getActionCost());
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
				if(h.getPlayers().size()<4)
					if(IsEnoughtStrong())
						if(controlServant()){
								f.getPlayer().getFamilyMember(f.getColor()).use();
								h.activateEffect(f);
								h.addPlayer(f);
							}
						else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
					else throw new GameException(GameError.FM_ERR_PA);
				else throw new GameException(GameError.SA_MAX_FM);
			else throw new GameException(GameError.FM_ERR_USE);
		}
}
