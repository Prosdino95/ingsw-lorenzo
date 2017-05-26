package gamemodel.jsonparsing;

import java.util.ArrayList;

import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.effects.*;
import gamemodel.*;


public class EffectParsing {
	
	private List<Effect> istantEffect=new ArrayList<>();;

	
	public List<Effect> parsing(JsonArray effects) {
		String type;
		for (JsonValue item : effects){		
			type=item.asObject().getString("type", null);
			switch(type){
				case "council-privileges":istantEffect.add(councilPrivileges(item));
				break;
				case "point-mod": istantEffect.add(pointMod(item));
				break;	
				case "resourse-mod": istantEffect.add(resourceMod(item));
				break;
				case "testeffect": istantEffect.add(new TestEffects());
				break;
			}	
		}
		return istantEffect;
	}
	
	private Effect councilPrivileges(JsonValue item) {
		int quantity=item.asObject().getInt("quantity", 0);
		return new CouncilPrivileges(quantity);
	}

	private Effect pointMod(JsonValue item) {
		int military=item.asObject().getInt("military", 0);
		int faith=item.asObject().getInt("faith", 0);
		int victory=item.asObject().getInt("victory", 0);
		return new PointModify(new Point(military,faith,victory));
	}

	private Effect resourceMod(JsonValue item) {
		int gold=item.asObject().getInt("gold", 0);
		int wood=item.asObject().getInt("wood", 0);
		int stone=item.asObject().getInt("stone", 0);
		int servants=item.asObject().getInt("servants", 0);
		return new ResourceModify(new Resource(gold,stone,wood,servants));
		
	}
	
}
