package gamemodel.command;

import gamemodel.*;

public class PlaceFMCommandFactory {
	private static final PlaceFMCommandFactory singleton=new PlaceFMCommandFactory();

	public static PlaceFMCommandFactory getSingleton() {
		if (singleton != null) 
			return singleton;
		return null;
	}

	public Command placeFMCommandFactory(Action action) {
		switch(action.getActionSpace().getType()){
			case TOWER:				
				return new PlaceFamilyMemberCommandTower(action);
			case MARKET:
				return new PlaceFamilyMemberCommandMarket(action);
			case HARVEST:
				return new PlaceFamilyMemberCommandHAndP(action);
			case PRODUCTION:
				return new PlaceFamilyMemberCommandHAndP(action);
			case COUNCIL_PALACE:
				return new PlaceFamilyMemberCommandCouncilPlace(action);
		}
		return null;		
	}
}

