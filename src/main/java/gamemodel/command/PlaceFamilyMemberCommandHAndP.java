package gamemodel.command;

import gamemodel.*;

import gamemodel.ActionSpace.ActionSpace;
import gamemodel.ActionSpace.ActionSpaceType;
import gamemodel.ActionSpace.MemoryActionSpace;
import gamemodel.ActionSpace.TowerActionSpace;
import gamemodel.card.HarvesterAndBuildings;

public class PlaceFamilyMemberCommandHAndP implements Command {
	private FamilyMember f;
	private int servant;
	private MemoryActionSpace h;
	
	public PlaceFamilyMemberCommandHAndP(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		// this.h=board.getActionSpace(id);
	}

	public PlaceFamilyMemberCommandHAndP(Action action) {
		this.f = action.getFm();
		this.servant = action.getServants();
		this.h = (MemoryActionSpace) action.getActionSpace();
	}
	
	private boolean IsEnoughtStrong(){
		return(f.getActionpoint()>=h.getActionCost());
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
				if(h.controlPlayer(f))
					if(IsEnoughtStrong())
						if(controlServant())
							if(h.isFree()){
								f.use();
								f.setActionpoint(f.getActionpoint()+servant);
								cardEffect(h.getType());
								h.occupy();
								h.addPlayer(f);
							}
							else{
								f.use();
								f.setActionpoint(f.getActionpoint()+servant-3);
								cardEffect(h.getType());
								h.addPlayer(f);
							}
						else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
					else throw new GameException(GameError.FM_ERR_PA);
				else throw new GameException(GameError.SA_ERR_FM);
			else throw new GameException(GameError.FM_ERR_USE);
		}

	private void cardEffect(ActionSpaceType type) {
		
		if(type==ActionSpaceType.HARVEST){			
			for(int i=0;i<f.getPlayer().getTerritories().size();i++){
				HarvesterAndBuildings c=(HarvesterAndBuildings)f.getPlayer().getTerritories().get(i);
				if(f.getActionpoint()<c.getActionCost())
					c.activePermanentEffect(f.getPlayer());
			}
				
		}
		if(type==ActionSpaceType.PRODUCTION){
			for(int i=0;i<f.getPlayer().getBuildings().size();i++){
				HarvesterAndBuildings c=(HarvesterAndBuildings)f.getPlayer().getBuildings().get(i);
				if(f.getActionpoint()<c.getActionCost())
					c.activePermanentEffect(f.getPlayer());
			}			
		}		
	}
		
}
