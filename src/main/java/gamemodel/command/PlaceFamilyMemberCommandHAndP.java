package gamemodel.command;

import gamemodel.*;
import gamemodel.ActionSpace.ActionSpace;
import gamemodel.ActionSpace.MemoryActionSpace;

public class PlaceFamilyMemberCommandHAndP implements Command {
	private FamilyMember f;
	private int servant;
	private ActionSpace a;
	
	public PlaceFamilyMemberCommandHAndP(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		this.a=board.getActionSpaces(id);
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
				if(h.controlPlayer(f))
					if(IsEnoughtStrong())
						if(controlServant())
							if(h.isFree()){
								f.use();
								f.setActionpoint(f.getActionpoint()+servant);
								h.activateEffect(f);
								h.occupy();
								h.addPlayer(f);
							}
							else{
								f.use();
								f.setActionpoint(f.getActionpoint()+servant-3);
								h.activateEffect(f);
								h.addPlayer(f);
							}
						else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
					else throw new GameException(GameError.FM_ERR_PA);
				else throw new GameException(GameError.SA_ERR_FM);
			else throw new GameException(GameError.FM_ERR_USE);
		}
		
}
