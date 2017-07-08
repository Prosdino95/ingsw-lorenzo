package gamemodel.jsonparsing;

import com.eclipsesource.json.JsonValue;

import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.card.CardType;
import gamemodel.player.Color;
import gamemodel.player.Point;
import gamemodel.player.Resource;
/**
 * This class have some useful methods for parsing. 
 * the methods are Statics and protected so each class
 * in the jsonparsing package can call this methods for parsing
 * some common Objects like Points,Resource,CardType...
 */
public class ParsingHelper {
	
	protected static Point pointParsing(JsonValue item) {
		int military=item.asObject().getInt("military", 0);
		int faith=item.asObject().getInt("faith", 0);
		int victory=item.asObject().getInt("victory", 0);
		return new Point(military,faith,victory);
	}

	protected static Resource resourceParsing(JsonValue item) {
		int gold=item.asObject().getInt("gold", 0);
		int wood=item.asObject().getInt("wood", 0);
		int stone=item.asObject().getInt("stone", 0);
		int servants=item.asObject().getInt("servants", 0);
		return new Resource(gold,stone,wood,servants);	
	}
	
	protected static CardType getCardType(JsonValue item){
		String s=item.asObject().getString("card-type","");
		switch(s){
		case"building":return CardType.BUILDING;
		case"territory":return CardType.TERRITORY;
		case"venture":return CardType.VENTURE;
		case"character":return CardType.CHARACTER;
		case"all":return CardType.ALL;
		default:return null;
		}
	}

	protected static Color getColor(String color) {
		switch(color){
		case"white":return Color.WHITE;
		case"black":return Color.BLACK;
		case"orange":return Color.ORANGE;
		case"uncolored":return Color.UNCOLORED;
		}
		return null;
	}
	
	protected static ActionSpaceType getActionSpaceType(JsonValue item){
		String s=item.asObject().getString("action-type","");
		switch(s){
		case"harvest":return ActionSpaceType.HARVEST;
		case"production":return ActionSpaceType.PRODUCTION;
		case"council-place":return ActionSpaceType.COUNCIL_PALACE;	
		case"market":return ActionSpaceType.MARKET;
		case"tower":return ActionSpaceType.TOWER;
		default: return null;	
		}
	}

}
