package gamemodel.jsonparsing;

import java.util.ArrayList;

import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.effects.*;
import gamemodel.*;


public class IstantEffectParsing {
	
	private List<IstantEffect> istantEffect;
	
	public IstantEffectParsing(){
		istantEffect=new ArrayList<>();
	}

	
	public List<IstantEffect> parsing(JsonArray effects) {
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
				case "resource-for-resource": istantEffect.add(resourceForResource(item));
				break;	
			}	
		}
		return istantEffect;
	}
	
	private IstantEffect resourceForResource(JsonValue item) {
		JsonValue forWhat,receive;
		String type=null;
		int forEach=0;
		Resource rin=null,rout=null;
		Point pin=null,pout=null;
		forEach=item.asObject().getInt("quantity", 0);
		
		receive=item.asObject().get("receive");
		if(receive.asObject().get("resource")!=null)
			rout=resourceMod(receive.asObject().get("resource"));
		
		if(receive.asObject().get("point")!=null)
			pout=pointMod(receive.asObject().get("point"));
		
		try{type=item.asObject().getString("for",null);}
		
		catch(UnsupportedOperationException e){		
			forWhat=item.asObject().get("for");
			if(forWhat.asObject().get("resource")!=null){
				rin=resourceMod(forWhat.asObject().get("resource"));
				type="Resource";
			}		
			else if(forWhat.asObject().get("point")!=null){
				pin=pointMod(forWhat.asObject().get("point"));
				type="Point";
			}
		}	
		return ResourceForResource.constructor(type, rin, pin, rout, pout, forEach);
	}
	
	

	private IstantEffect exchange(JsonValue item) {
		JsonValue give,receive;
		Resource rin=null,rout=null;
		Point pin=null,pout=null;
		IstantEffect councilPrivilege=null;
		give=item.asObject().get("give");
		if(give.asObject().get("resource")!=null)
			rout=resourceMod(give.asObject().get("resource"));
		
		if(give.asObject().get("point")!=null)
			pout=pointMod(give.asObject().get("point"));
		
		receive=item.asObject().get("receive");
		if(receive.asObject().get("resource")!=null)
			rin=resourceMod(receive.asObject().get("resource"));
		
		if(receive.asObject().get("point")!=null)
			pin=pointMod(receive.asObject().get("point"));
				
		if(receive.asObject().get("council-privileges")!=null)
			councilPrivilege=councilPrivileges(receive.asObject().get("council-privileges"));
		
		return new Exchange(pin, pout, rin, rout, councilPrivilege);
			
	}
	

	private IstantEffect councilPrivileges(JsonValue item) {
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