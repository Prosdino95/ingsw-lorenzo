package gamemodel;

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
			case MARKET:
				placeFMInMarket();
			case HARVEST:
				placeFMInHarvester();				
			case PRODUCTION:
				placeFMInProductions();
			case COUNCIL_PALACE:
				placeFMInCouncilPlace();
		}		
	}
	
	private boolean IsEnoughtStrong(){
		return(f.getActionpoint()+servants>=a.getActionCost());		
	}

	private void placeFMInTower() throws Exception{
		TowerActionSpace t=(TowerActionSpace)a;
		if(!f.isUsed())
			if(t.isFree())
				if(t.getTower().controlPlayer(f))				
					if(t.getTower().isFree())
						if(IsEnoughtStrong()){
								f.use();
								t.getTower().occupyTower();
								t.activateEffect(f);
								t.giveCard(f);
								t.occupy();
								t.getTower().addPlayer(f);
								}
						else throw new Exception("punti azione insufficenti");
					else throw new Exception("torre occupata");					
				else throw new Exception("torre occupata già da un tuo familiare");
			else throw new Exception("spazio azione occupato");
		else throw new Exception("familiare già impiegato");
	}
	
	
	private void placeFMInMarket(){
		if(a.isFree())
			if(IsEnoughtStrong()){
				a.activateEffect(f);
				a.occupy();				
			}		
	}
	
	private void placeFMInHarvester(){
		
	}
	
	private void placeFMInProductions() {
		
	}
	
	private void placeFMInCouncilPlace(){
		
	}


}
