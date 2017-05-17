package gamemodel.command;

import gamemodel.*;

public class PlaceFamilyMemberCommandMarket implements Command {
	private FamilyMember f;
	private int servant;
	private ActionSpace a;
	
	public PlaceFamilyMemberCommandMarket(Board board,int id,FamilyMember f, int servant) {
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
}

