package gamemodel.permanenteffect;

public enum PEffect {
	DEBUFF_RESOURCE,
	DEBUFF_POINT, 
	MODIFY_STRENGTH, 
	DISCOUNT,
	FM,
	HALVE_SERVANTS,
	NO_ACTION_SPACE, 
	NO_MATTER_IF_OCCUPIED,
	NO_BONUS,
	NO_VICTORY_POINTS_BOUNDED_TO_CHARACTER_CARDS,
	NO_VICTORY_POINTS_BOUNDED_TO_VENTURE_CARDS,
	NO_VICTORY_POINTS_BOUNDED_TO_TERRITORY_CARDS,
	LOSE_ONE_VICTORY_POINT_FOR_EVERY_FIVE_VICTORY_POINT,
	LOSE_ONE_VICTORY_POINT_FOR_EVERY_MILITARY_POINT,
	LOSE_ONE_VICTORY_POINT_FOR_EVERY_WOOD_AND_STONE_ON_YOUR_BUILDINGS_CARDS_COST,
	LOSE_ONE_VICTORY_POINT_FOR_EVERY_RESOURCE, 
	NO_FIRST_ACTION,
	NO_NEED_TO_PAY_3_COINS,
	RESOURCES_TWICE_FROM_DEVELOPEMENT_CARDS_ISTANT_EFFECT,
	FIVE_ADDITIONAL_VICTORY_POINTS_WHEN_SUPPORT_THE_CHURCH,
	NO_NEED_TO_SATISFY_MILITARY_POINTS_REQUIREMENT;
	
	public String toString(){
		switch(this){
		case FIVE_ADDITIONAL_VICTORY_POINTS_WHEN_SUPPORT_THE_CHURCH:
			return"5 VP when support pope";
		case HALVE_SERVANTS:
			return"servants X2";
		case LOSE_ONE_VICTORY_POINT_FOR_EVERY_FIVE_VICTORY_POINT:
			return"->|-1 X 5 VP";
		case LOSE_ONE_VICTORY_POINT_FOR_EVERY_MILITARY_POINT:
			return"->|-1 X MP";
		case LOSE_ONE_VICTORY_POINT_FOR_EVERY_RESOURCE:
			return"->|-1 X resource";
		case LOSE_ONE_VICTORY_POINT_FOR_EVERY_WOOD_AND_STONE_ON_YOUR_BUILDINGS_CARDS_COST:
			return"->|-1 X buildings cost";
		case NO_FIRST_ACTION:
			return"no fist action";
		case NO_MATTER_IF_OCCUPIED:
			return"no matter if occupied";
		case NO_NEED_TO_PAY_3_COINS:
			return"no 3 tower coin ";
		case NO_NEED_TO_SATISFY_MILITARY_POINTS_REQUIREMENT:
			return"no MP X terrotory";
		case NO_VICTORY_POINTS_BOUNDED_TO_CHARACTER_CARDS:
			return"->|no character card";
		case NO_VICTORY_POINTS_BOUNDED_TO_TERRITORY_CARDS:
			return"->|no territory card";
		case NO_VICTORY_POINTS_BOUNDED_TO_VENTURE_CARDS:
			return"->|no venture card";
		case RESOURCES_TWICE_FROM_DEVELOPEMENT_CARDS_ISTANT_EFFECT:
			return"istant effect development card X2";
		case NO_BONUS:
			return"no tower bonus";
		default:
			return"";			
		}
	}
}

