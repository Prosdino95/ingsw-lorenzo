package gamemodel.command;

import gamemodel.Action;
import gamemodel.Color;

public class PlaceFamilyMemberCommandHandPLittle extends PlaceFamilyMemberCommandHAndP{
	
	
	public PlaceFamilyMemberCommandHandPLittle(Action action){
		super(action);
	}
	
	@Override
	public void isLegal() throws GameException {
		if(!f.isUsed())
			if(IsEnoughtStrong())
				if(controlServant())
					if (f.getColor() == Color.STRANGE) {
						f.getPlayer().getPersonalBonusTile().activate(f.getPlayer(),h.getType());								
						cardEffect(h.getType());
					}
					else if(h.isAccessible(action)){
						f.getPlayer().getFamilyMember(f.getColor()).use();
						cardEffect(h.getType());
						h.occupy();
						f.getPlayer().getPersonalBonusTile().activate(f.getPlayer(),h.getType());
						h.addPlayer(f);
					}
					else throw new GameException(GameError.SA_ERR);
				else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
			else throw new GameException(GameError.FM_ERR_PA);
		else throw new GameException(GameError.FM_ERR_USE);
	}


}
