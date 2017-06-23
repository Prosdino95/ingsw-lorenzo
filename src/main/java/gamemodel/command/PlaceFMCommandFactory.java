package gamemodel.command;

import java.util.function.Function;

import gamemodel.*;

public class PlaceFMCommandFactory {
	
	Function<Action, Command> generateCommand;	
	
	public Command placeFMCommandFactory(Action action){
		return generateCommand.apply(action);		
	}
	
	public static PlaceFMCommandFactory GenerateCommandFactory(int num) {
		if(num>2) 
			return new PlaceFMCommandFactory(PlaceFMCommandFactory::BigFactory);
		else
			return new PlaceFMCommandFactory(PlaceFMCommandFactory::LittleFactory);
	}
	
	private PlaceFMCommandFactory(Function<Action, Command> f){
		this.generateCommand=f;		
	}

	private static Command BigFactory(Action action) {
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
	
	private static Command LittleFactory(Action action) {
		switch(action.getActionSpace().getType()){
			case TOWER:				
				return new PlaceFamilyMemberCommandTower(action);
			case MARKET:
				return new PlaceFamilyMemberCommandMarket(action);
			case HARVEST:
				return new PlaceFamilyMemberCommandHandPLittle(action);
			case PRODUCTION:
				return new PlaceFamilyMemberCommandHandPLittle(action);
			case COUNCIL_PALACE:
				return new PlaceFamilyMemberCommandCouncilPlace(action);
		}
		return null;		
	}
}

