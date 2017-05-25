package gamemodel.command;

import gamemodel.*;

public class PlaceFMCommandFactory {
	private static final PlaceFMCommandFactory singleton=new PlaceFMCommandFactory();

	public static PlaceFMCommandFactory getSingleton() {
		return singleton;
	}

	public Command placeFMCommandFactory(Board board, int id, FamilyMember f, int servant) {
		switch(board.getActionSpace(id).getType()){
			case TOWER:				
				return new PlaceFamilyMemberCommandTower(board,id,f,servant);
			case MARKET:
				return new PlaceFamilyMemberCommandMarket(board,id,f,servant);
			case HARVEST:
				return new PlaceFamilyMemberCommandHAndP(board,id,f,servant);
			case PRODUCTION:
				return new PlaceFamilyMemberCommandHAndP(board,id,f,servant);
			case COUNCIL_PALACE:
				return new PlaceFamilyMemberCommandCouncilPlace(board,id,f,servant);
		}
		return null;		
	}
}

