package gamemodel.command;

import gamemodel.Action;

public class PlaceFamiliMemberCommandHandPLittle extends PlaceFamilyMemberCommandHAndP{
	
	
	public PlaceFamiliMemberCommandHandPLittle(Action action){
		super(action);
	}
	
	@Override
	public void isLegal() throws GameException {
		if(!f.isUsed())
			if(IsEnoughtStrong())
				if(controlServant())
					if(h.isAccessible(action)){
						f.getPlayer().getFamilyMember(f.getColor()).use();
						cardEffect(h.getType());
						h.occupy();
						h.addPlayer(f);
					}
					else throw new GameException(GameError.SA_ERR);
				else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
			else throw new GameException(GameError.FM_ERR_PA);
		else throw new GameException(GameError.FM_ERR_USE);
	}


}
