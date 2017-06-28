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
	private List<IstantEffect> istantEffects=new ArrayList<>();
	private List<IstantEffect> activateEffects=new ArrayList<>();
	private PermanentEffect permanentEffects;
	private CardType type;
	private int id=0;	
	private Board board;
	
	public CardParsing(Board board){
		this.board=board;
	}
	
	public List<Card> parsing(String json){
		JsonArray items = arrayBuild(json); 
		for (JsonValue item : items) {
			permanentEffects=null;
			actionCost=item.asObject().getInt("action-cost", 0);
			period=item.asObject().getInt("period", 0);
    		name=item.asObject().getString("name", null);
    		id=item.asObject().getInt("id", -1);
    		cardCostParsing(item);
    		if(item.asObject().get("istant-effect")!=null){
    			istantEffects=new ArrayList<>();
    			istantEffects=new IstantEffectParsing().parsing(item.asObject().get("istant-effect").asArray(),board);
    		}
    		if(item.asObject().get("activate-effect")!=null){
    			activateEffects=new ArrayList<>();
    			activateEffects=new IstantEffectParsing().parsing(item.asObject().get("activate-effect").asArray(),board);
    		}
    		if(item.asObject().get("permanent-effect")!=null){
    			permanentEffects=new PermanentEffectParsing().parsing(item.asObject().get("permanent-effect"));
    		}
    		makeCard(); 
    	}	
		return cards;
	}
	
	
	
	private void makeCard() {
		switch(type){
		case BUILDING:cards.add(new HarvesterAndBuildings(id,name,period,rRequirement, rPrice, pRequirement, pPrice, istantEffects,activateEffects, type,actionCost));
			break;
		case CHARACTER:cards.add(new CharactersCard(id,name,period,rRequirement, rPrice, pRequirement, pPrice, istantEffects,permanentEffects, type));
			break;
		case TERRITORY:cards.add(new HarvesterAndBuildings(id,name,period,rRequirement, rPrice, pRequirement, pPrice, istantEffects,activateEffects, type,actionCost));
			break;
		case VENTURE:cards.add(new VentureCard(id,name,period,rRequirement, rPrice, pRequirement, pPrice, istantEffects,activateEffects, type));
			break;
		}
	}



	private JsonArray arrayBuild(String json) {

		if(Json.parse(json).asObject().get("building-cards")!=null){
			type=CardType.BUILDING;
			return Json.parse(json).asObject().get("building-cards").asArray();
		}
		if(Json.parse(json).asObject().get("character-cards")!=null){
			type=CardType.CHARACTER;
			return Json.parse(json).asObject().get("character-cards").asArray();
		}
		if(Json.parse(json).asObject().get("territory-cards")!=null){
			type=CardType.TERRITORY;
			return Json.parse(json).asObject().get("territory-cards").asArray();
		}
		if(Json.parse(json).asObject().get("venture-cards")!=null){
			type=CardType.VENTURE;
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
			rRequirement=ParsingHelper.resourceParsing(item.asObject().get("resource-requirement").asObject());
		
		if(item.asObject().get("resource-price")==null)
			rPrice=rRequirement;
		else
			rPrice=ParsingHelper.resourceParsing(item.asObject().get("resource-price").asObject());
		
		if(item.asObject().get("point-requirement")!=null)
			pRequirement=ParsingHelper.pointParsing(item.asObject().get("point-requirement").asObject());
		
		if(item.asObject().get("point-price")==null)
			pPrice=pRequirement;
		else
			pPrice=ParsingHelper.pointParsing(item.asObject().get("point-price").asObject());	
		
	}

}
