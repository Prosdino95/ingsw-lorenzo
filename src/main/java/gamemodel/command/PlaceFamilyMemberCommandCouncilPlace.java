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
			MemoryActionSpace h=(MemoryActionSpace)a;	
			if(!f.isUsed())
				if(h.controlPlayer(f))
					if(IsEnoughtStrong())
						if(controlServant()){
								f.use();
								h.activateEffect(f);
								h.addPlayer(f);
							}
						else throw new Exception("servants non sufficienti");
					else throw new Exception("punti azione insufficenti");
				else throw new Exception("spazio già occupato da un tuo familiare");
			else throw new Exception("familiare già impiegato");
		}
}
