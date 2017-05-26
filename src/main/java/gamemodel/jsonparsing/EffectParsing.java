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
				case "point-mod": istantEffect.add(new PointModify(pointMod(item)));
				break;	
				case "resource-mod": istantEffect.add(new ResourceModify(resourceMod(item)));
				break;
				case "testeffect": istantEffect.add(new TestEffects());
				break;
				case "exchange": istantEffect.add(exchange(item));
				break;
				case "point-for-resource": istantEffect.add(pointForResource(item));
				break;	
			}	
		}
		return istantEffect;
	}
	
	private Effect pointForResource(JsonValue item) {
		// TODO Auto-generated method stub
		return null;
	}

	private Effect exchange(JsonValue item) {
		String out=item.asObject().getString("out", null);
		String in=item.asObject().getString("in", null);
		
		if(in.equals("resource") && out.equals("resource"))
			return(new Exchange(resourceMod(item),resourceMod(item)));
		
		if(in.equals("point") && out.equals("point"))
			return(new Exchange(pointMod(item),pointMod(item)));
		
		if(in.equals("point") && out.equals("resource"))
			return(new Exchange(pointMod(item),resourceMod(item)));
		
		if(in.equals("resource") && out.equals("point"))
			return(new Exchange(resourceMod(item),pointMod(item)));
		return null;
	}

	private Effect councilPrivileges(JsonValue item) {
		int quantity=item.asObject().getInt("quantity", 0);
		return new CouncilPrivileges(quantity);
	}

	private Point pointMod(JsonValue item) {
		int military=item.asObject().getInt("military", 0);
		int faith=item.asObject().getInt("faith", 0);
		int victory=item.asObject().getInt("victory", 0);
		return new Point(military,faith,victory);
	}

	private Resource resourceMod(JsonValue item) {
		int gold=item.asObject().getInt("gold", 0);
		int wood=item.asObject().getInt("wood", 0);
		int stone=item.asObject().getInt("stone", 0);
		int servants=item.asObject().getInt("servants", 0);
		return new Resource(gold,stone,wood,servants);
		
	}
	
}
