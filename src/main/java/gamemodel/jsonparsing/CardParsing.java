package gamemodel.jsonparsing;

import java.util.*;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.card.*;
import gamemodel.effects.Effect;
import gamemodel.*;

public class CardParsing {
	
	private List<Card> cards=new ArrayList<>();
	private String name;
	private int actionCost;
	private Resource rRequirement,rPrice;
	private Point pRequirement,pPrice;
	private Map<CardType, Integer> rCard;
	private List<Effect> istantEffects;
	private CardType type;
	
	public List<Card> parsing(String json){
		JsonArray items = arrayBuild(json); 
		for (JsonValue item : items) {
			actionCost=item.asObject().getInt("action-cost", 0);
    		name=item.asObject().getString("name", null);
    		cardCostParsing(item);
    		if(item.asObject().get("effect")!=null){
    			istantEffects=new ArrayList<>();
    			istantEffects=new EffectParsing().parsing(item.asObject().get("effect").asArray());
    		}
    		makeCard();  		
    	}	
		return cards;
	}
	
	
	
	private void makeCard() {
		switch(type){
		case BUILDINGS:cards.add(new HarvesterAndBuildings(name,rRequirement, rPrice, pRequirement, pPrice, istantEffects, type, rCard, actionCost));
			break;
		case CHARACTERS:cards.add(new HarvesterAndBuildings(name,rRequirement, rPrice, pRequirement, pPrice, istantEffects, type, rCard, actionCost));
			break;
		case TERRITORIES:cards.add(new RealCard(name,rRequirement, rPrice, pRequirement, pPrice, istantEffects, type, rCard));
			break;
		case VENTURES:cards.add(new RealCard(name,rRequirement, rPrice, pRequirement, pPrice, istantEffects, type, rCard));
			break;
		}
	}



	private JsonArray arrayBuild(String json) {
		if(Json.parse(json).asObject().get("buildings-cards")!=null){
			type=CardType.BUILDINGS;
			return Json.parse(json).asObject().get("buildings-cards").asArray();
		}
		if(Json.parse(json).asObject().get("characters-cards")!=null){
			type=CardType.CHARACTERS;
			return Json.parse(json).asObject().get("characters-Cards").asArray();
		}
		if(Json.parse(json).asObject().get("territories-cards")!=null){
			type=CardType.TERRITORIES;
			return Json.parse(json).asObject().get("territories-cards").asArray();
		}
		if(Json.parse(json).asObject().get("ventures-cards")!=null){
			type=CardType.VENTURES;
			return Json.parse(json).asObject().get("ventures-cards").asArray();
		}
		return null;
	}



	private void cardCostParsing(JsonValue item) {
		if(item.asObject().get("resource-requirement")!=null)
			rRequirement=resourceParsing(item.asObject().get("resource-requirement").asObject());
		if(item.asObject().get("resource-price")==null)
			rPrice=rRequirement;
		else
			rPrice=resourceParsing(item.asObject().get("resource-price").asObject());
		if(item.asObject().get("point-requirement")!=null)
			pRequirement=pointParsing(item.asObject().get("resource-requirement").asObject());
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
