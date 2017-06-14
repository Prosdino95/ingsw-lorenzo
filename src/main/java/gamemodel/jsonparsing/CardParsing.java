package gamemodel.jsonparsing;

import java.util.*;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.card.*;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;
import gamemodel.*;

public class CardParsing {
	
	private List<Card> cards=new ArrayList<>();
	private String name;
	private int period;
	private int actionCost;
	private Resource rRequirement,rPrice;
	private Point pRequirement,pPrice;
	private Map<CardType, Integer> rCard;
	private List<IstantEffect> istantEffects,activateEffects;
	private List<PermanentEffect> permanentEffects;
	private CardType type;
	private int id=0;
	
	public List<Card> parsing(String json){
		JsonArray items = arrayBuild(json); 
		for (JsonValue item : items) {
			actionCost=item.asObject().getInt("action-cost", 0);
			period=item.asObject().getInt("period", 0);
    		name=item.asObject().getString("name", null);
    		id=item.asObject().getInt("id", -1);
    		cardCostParsing(item);
    		if(item.asObject().get("istant-effect")!=null){
    			istantEffects=new ArrayList<>();
    			istantEffects=new IstantEffectParsing().parsing(item.asObject().get("istant-effect").asArray());
    		}
    		if(item.asObject().get("activate-effect")!=null){
    			activateEffects=new ArrayList<>();
    			activateEffects=new IstantEffectParsing().parsing(item.asObject().get("activate-effect").asArray());
    		}
    		if(item.asObject().get("permanent-effect")!=null){
    			permanentEffects=new ArrayList<>();
    			permanentEffects=new PermanentEffectParsing().parsing(item.asObject().get("permanent-effect").asArray());
    		}
    		makeCard(); 
    	}	
		return cards;
	}
	
	
	
	private void makeCard() {
		switch(type){
		case BUILDINGS:cards.add(new HarvesterAndBuildings(id,name,period,rRequirement, rPrice, pRequirement, pPrice, istantEffects,activateEffects, type,actionCost));
			break;
		case CHARACTERS:cards.add(new CharactersCard(id,name,period,rRequirement, rPrice, pRequirement, pPrice, istantEffects,permanentEffects, type));
			break;
		case TERRITORIES:cards.add(new HarvesterAndBuildings(id,name,period,rRequirement, rPrice, pRequirement, pPrice, istantEffects,activateEffects, type,actionCost));
			break;
		case VENTURES:cards.add(new VentureCard(id,name,period,rRequirement, rPrice, pRequirement, pPrice, istantEffects,activateEffects, type));
			break;
		}
	}



	private JsonArray arrayBuild(String json) {

		if(Json.parse(json).asObject().get("building-cards")!=null){
			type=CardType.BUILDINGS;
			return Json.parse(json).asObject().get("building-cards").asArray();
		}
		if(Json.parse(json).asObject().get("character-cards")!=null){
			type=CardType.CHARACTERS;
			return Json.parse(json).asObject().get("character-cards").asArray();
		}
		if(Json.parse(json).asObject().get("territory-cards")!=null){
			type=CardType.TERRITORIES;
			return Json.parse(json).asObject().get("territory-cards").asArray();
		}
		if(Json.parse(json).asObject().get("venture-cards")!=null){
			type=CardType.VENTURES;
			return Json.parse(json).asObject().get("venture-cards").asArray();
		}
		return null;
	}



	private void cardCostParsing(JsonValue item) {
		rRequirement=null;
		rPrice=null;
		pRequirement=null;
		pPrice=null;		
		if(item.asObject().get("resource-requirement")!=null)
			rRequirement=resourceParsing(item.asObject().get("resource-requirement").asObject());
		
		if(item.asObject().get("resource-price")==null)
			rPrice=rRequirement;
		else
			rPrice=resourceParsing(item.asObject().get("resource-price").asObject());
		
		if(item.asObject().get("point-requirement")!=null)
			pRequirement=pointParsing(item.asObject().get("point-requirement").asObject());
		
		if(item.asObject().get("point-price")==null)
			pPrice=pRequirement;
		else
			pPrice=pointParsing(item.asObject().get("point-price").asObject());	
		
		if(item.asObject().get("card-requirement")!=null)
			cardRequirementBuild(item);
	}



	private void cardRequirementBuild(JsonValue item) {
		rCard=new HashMap<>();		
	}



	private Point pointParsing(JsonValue item) {
		int military=item.asObject().getInt("military", 0);
		int faith=item.asObject().getInt("faith", 0);
		int victory=item.asObject().getInt("victory", 0);
		return new Point(military,faith,victory);
	}

	private Resource resourceParsing(JsonValue item) {
		int gold=item.asObject().getInt("gold", 0);
		int wood=item.asObject().getInt("wood", 0);
		int stone=item.asObject().getInt("stone", 0);
		int servants=item.asObject().getInt("servants", 0);
		return new Resource(gold,stone,wood,servants);
		
	}

}
