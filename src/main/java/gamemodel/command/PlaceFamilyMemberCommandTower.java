package gamemodel.command;

import gamemodel.*;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.CardType;
import gamemodel.permanenteffect.PEffect;

public class PlaceFamilyMemberCommandTower implements Command {
	
	private FamilyMember f;
	private int servant;
	private TowerActionSpace t;
	private Action action;
	
	public PlaceFamilyMemberCommandTower(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		this.t=(TowerActionSpace) board.getActionSpace(id);
	}

	public PlaceFamilyMemberCommandTower(Action action) {
		this.f = action.getFm();
		this.servant = action.getServants();
		this.t = (TowerActionSpace) action.getActionSpace();
		this.action=action;
	}
	
	private boolean controlNUmberCard(CardType type) {
		switch(type){
		case BUILDING:
			return(f.getPlayer().getBuildings().size()<=5);
		case CHARACTER:
			return(f.getPlayer().getCharacters().size()<=5);
		case TERRITORY:
			return(f.getPlayer().getTerritories().size()<=5);
		case VENTURE:
			return(f.getPlayer().getVentures().size()<=5);
		default:
			return false;		
		}		
	}
	
	private boolean militaryPointRequirement(CardType cardType){
		if(cardType!=CardType.TERRITORY)
			return true;
		if(f.getPlayer().getPEffects(PEffect.NO_NEED_TO_SATISFY_MILITARY_POINTS_REQUIREMENT).size()>0)
			return true;
		int point=f.getPlayer().getPoint().getMilitary();
		int card=f.getPlayer().getTerritories().size();
		if(card<2)
			return true;
		if(card==2 && point>=3)
			return true;
		if(card==3&&point>=7)
			return true;
		if(card==4&&point>=12)
			return true;
		if(card==5&&point>=18)
			return true;
		return false;		
	}

	private boolean IsEnoughtStrong(){
		return(f.getActionpoint() >= t.getActionCost());
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
		if(f.isUsed())
			throw new GameException(GameError.FM_ERR_USE);
		if(!t.isAccessible(action))
			throw new GameException(GameError.SA_ERR);
		if(!t.getTower().controlPlayer(f))
			throw new GameException(GameError.TWR_ERR_FM);
		if(!t.getTower().isFree(f))	
			throw new GameException(GameError.NOT_ENOUGH_MONEY);
		if(!IsEnoughtStrong())
			throw new GameException(GameError.FM_ERR_PA);
		if(!militaryPointRequirement(t.getCard().getType()))
			throw new GameException(GameError.MILITARY_POINT);
		if(!controlNUmberCard(t.getCard().getType()))
			throw new GameException(GameError.TOO_CARD);
		if(!controlServant())
			throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
		else 
			t.activateEffect(f);
		
		if(f.getPlayer().controlResourceAndPay(t.getCard())) {
			if (f.getColor() == Color.STRANGE) {
				t.giveCard(f);
			} else {
				f.getPlayer().getFamilyMember(f.getColor()).use();
				t.getTower().occupyTower();									
				t.giveCard(f);
				t.occupy();
				t.getTower().addPlayer(f);
			}
		}
		else
		{
			t.rollbackEffect(f);
			throw new GameException(GameError.RESOURCE_ERR_CARD);	 									
		}
	}		
}

