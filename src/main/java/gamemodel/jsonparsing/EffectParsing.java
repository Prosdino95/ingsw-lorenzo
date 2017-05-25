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
				case "resourse-mod": istantEffect.add(resourceMod(item));
				break;
				case "testeffect": istantEffect.add(new TestEffects());
				break;
			}	
		}
		return istantEffect;
	}
	
	private Effect resourceMod(JsonValue item) {
		int gold=item.asObject().getInt("gold", 0);
		int wood=item.asObject().getInt("wood", 0);
		int stone=item.asObject().getInt("gold", 0);
		int servants=item.asObject().getInt("servants", 0);
		return new ResourceModify(new Resource(gold,stone,wood,servants));
		
	}
	
}
