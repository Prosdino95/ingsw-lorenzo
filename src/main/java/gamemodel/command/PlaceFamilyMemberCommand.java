package gamemodel.command;

import gamemodel.ActionSpace;
import gamemodel.Board;
import gamemodel.FamilyMember;
import gamemodel.Resource;
import gamemodel.TowerActionSpace;

public class PlaceFamilyMemberCommand implements Command {
	
	private Board board;
	private FamilyMember f;
	private int idSpaceActions;
	private int servants;
	private ActionSpace a;

	public PlaceFamilyMemberCommand(Board board,int id,FamilyMember f,int servants) {
		this.board=board;
		this.f=f;
		this.idSpaceActions=id;
		this.servants=servants;
	}

	@Override
	public void isLegal() throws Exception {
		a= board.getActionSpaces(idSpaceActions);
		switch(a.getType()){
			case TOWER:				
				placeFMInTower();
				break;
			case MARKET:
				placeFMInMarket();
				break;
			case HARVEST:
				placeFMInHarvestAndProduction();
				break;
			case PRODUCTION:
				placeFMInHarvestAndProduction();
				break;
			case COUNCIL_PALACE:
				placeFMInCouncilPlace();
				break;
		}		
	}
	
	private boolean IsEnoughtStrong(){
		return(f.getActionpoint()+servants>=a.getActionCost());
	}

	private boolean controlServant() throws Exception{
		if(servants>=0)
			if(f.getPlayer().isEnoughtResource(new Resource(0,0,0,servants))){
				f.getPlayer().subResources(new Resource(0,0,0,servants));
				return true;
			}
		return false;			
	}
	private boolean CardControl() {
		//TODO
		return true;
		
	}
	private void placeFMInTower() throws Exception{
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

	private void placeFMInMarket() throws Exception{
		if(!f.isUsed())
			if(a.isFree())
				if(IsEnoughtStrong())
					if(controlServant()){
						f.use();
						a.activateEffect(f);
						a.occupy();				
						}
					else throw new Exception("servants non sufficienti");
				else throw new Exception("punti azione insufficenti");
			else throw new Exception("spazio azione occupato");
		else throw new Exception("familiare già impiegato");
	}
	
	private void placeFMInHarvestAndProduction() throws Exception{
		if(!f.isUsed())
			if(IsEnoughtStrong())
				if(controlServant())
					if(a.isFree()){
						f.use();
						a.activateEffect(f);
						a.occupy();
					}
					else{
						f.use();
						f.setActionpoint(f.getActionpoint()-3);
						a.activateEffect(f);
					}
				else throw new Exception("servants non sufficienti");
			else throw new Exception("punti azione insufficenti");
		else throw new Exception("familiare già impiegato");
	}
	
	private void placeFMInCouncilPlace(){
		
	}


}
