package gamemodel.jsonparsing;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.*;
import gamemodel.actionSpace.*;
import gamemodel.card.CardType;
import gamemodel.effects.*;

public class TowerASParsing {
	
	private int cost;
	private List<TowerActionSpace> AS=new ArrayList<>();
	private List<IstantEffect> effects;
	private int id=0;
	private final ActionSpaceType TYPE=ActionSpaceType.TOWER;
	private Tower territories=new Tower(CardType.TERRITORIES);
	private Tower buildings=new Tower(CardType.BUILDINGS);
	private Tower ventures=new Tower(CardType.VENTURES);
	private Tower characters=new Tower(CardType.CHARACTERS);
	
	
	public List<TowerActionSpace> parsing(String json){
		JsonArray items = Json.parse(json).asObject().get("TowerActionSpace").asArray();
		for (JsonValue item : items) {
    		cost=item.asObject().getInt("action-cost", 1);
    		effects=null;
    		if(item.asObject().get("effect")!=null){
    			effects=new ArrayList<>();
    			effects=new IstantEffectParsing().parsing(item.asObject().get("effect").asArray());
    		}
    		makeAS(item.asObject().getString("card-tower", null));
    		id++;
    	}	
		return AS;
	}
	
	private void makeAS(String tower){
    	switch(tower){
			case "territories":AS.add(new RealTowerActionSpace(id,cost,effects,territories,TYPE));
			break;
			case "buildings":AS.add(new RealTowerActionSpace(id,cost,effects,buildings,TYPE));
			break;
			case "ventures":AS.add(new RealTowerActionSpace(id,cost,effects,ventures,TYPE));
			break;
			case "characters":AS.add(new RealTowerActionSpace(id,cost,effects,characters,TYPE));
			break;
    	}
	}	
}
