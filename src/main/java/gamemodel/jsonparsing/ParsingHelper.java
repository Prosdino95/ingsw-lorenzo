package gamemodel.jsonparsing;

import com.eclipsesource.json.JsonValue;

import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.card.CardType;

public class ParsingHelper {
	
	static Point pointParsing(JsonValue item) {
		int military=item.asObject().getInt("military", 0);
		int faith=item.asObject().getInt("faith", 0);
		int victory=item.asObject().getInt("victory", 0);
		return new Point(military,faith,victory);
	}

	static Resource resourceParsing(JsonValue item) {
		int gold=item.asObject().getInt("gold", 0);
		int wood=item.asObject().getInt("wood", 0);
		int stone=item.asObject().getInt("stone", 0);
		int servants=item.asObject().getInt("servants", 0);
		return new Resource(gold,stone,wood,servants);	
	}
	
	static CardType getCardType(JsonValue item){
		String s=item.asObject().getString("card-type","");
		switch(s){
		case"building":return CardType.BUILDING;
		case"territory":return CardType.TERRITORY;
		case"venture":return CardType.VENTURE;
		case"character":return CardType.CHARACTER;
		default:return null;
		}
	}

}
