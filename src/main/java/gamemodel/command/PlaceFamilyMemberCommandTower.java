package gamemodel.command;

import gamemodel.*;
import gamemodel.ActionSpace.ActionSpace;
import gamemodel.ActionSpace.TowerActionSpace;

public class PlaceFamilyMemberCommandTower implements Command {
	
	private FamilyMember f;
	private int servant;
	private ActionSpace a;
	
	public PlaceFamilyMemberCommandTower(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		this.a=board.getActionSpaces(id);
	}

	private boolean IsEnoughtStrong(){
		return(f.getActionpoint()+servant>=a.getActionCost());
	}

	private boolean controlServant() throws Exception{
		if(servant>=0)
			if(f.getPlayer().isEnoughtResource(new Resource(0,0,0,servant))){
				f.getPlayer().subResources(new Resource(0,0,0,servant));
				return true;
			}
		return false;			
	}

	@Override
	public void isLegal() throws Exception {
		TowerActionSpace t=(TowerActionSpace)a;
		if(!f.isUsed())
			if(t.isFree())
				if(t.getTower().controlPlayer(f))				
					if(t.getTower().isFree())
						if(IsEnoughtStrong())
							if(controlServant()){
								t.activateEffect(f);
									if(CardControl()){
										f.use();
										t.getTower().occupyTower();									
										t.giveCard(f);
										t.occupy();
										t.getTower().addPlayer(f);
										}
								else throw new Exception("carta troppo costosa");	
								//t.rollbackEffect(f);	
							}
							else throw new Exception("servants non sufficienti");
						else throw new Exception("punti azione insufficenti");
					else throw new Exception("torre occupata");					
				else throw new Exception("torre occupata già da un tuo familiare");
			else throw new Exception("spazio azione occupato");
		else throw new Exception("familiare già impiegato");
	}

	private boolean CardControl() {
		// TODO Auto-generated method stub
		return true;
	}
		
}

