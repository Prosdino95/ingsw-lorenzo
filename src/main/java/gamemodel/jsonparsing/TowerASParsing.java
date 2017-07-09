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

/**
 * This class has the methods for parsing 
 * tower action spaces from the configuration file;
 */
public class TowerASParsing {
	
	private int cost;
	private List<TowerActionSpace> AS=new ArrayList<>();
	private List<IstantEffect> effects=new ArrayList<>();
	private int id=0;
	private final ActionSpaceType TYPE=ActionSpaceType.TOWER;
	private Tower territories=new Tower(CardType.TERRITORY);
	private Tower buildings=new Tower(CardType.BUILDING);
	private Tower ventures=new Tower(CardType.VENTURE);
	private Tower characters=new Tower(CardType.CHARACTER);
	private Board board;
	
	
	public TowerASParsing(Board board) {
		this.board=board;
	}

	public List<TowerActionSpace> parsing(String json){
		JsonArray items = Json.parse(json).asObject().get("TowerActionSpace").asArray();
		for (JsonValue item : items) {
			effects=new ArrayList<>();
    		cost=item.asObject().getInt("action-cost", 1);
    		id=item.asObject().getInt("id", -1);
    		if(item.asObject().get("effect")!=null){
    			effects=new ArrayList<>();
    			effects=new IstantEffectParsing().parsing(item.asObject().get("effect").asArray(),board);
    		}
    		makeAS(item.asObject().getString("card-tower", null));
    	}	
		return AS;
	}
	
	private void makeAS(String tower){
    	switch(tower){
			case "territories":AS.add(new TowerActionSpace(id,cost,effects,territories,TYPE));
			break;
			case "buildings":AS.add(new TowerActionSpace(id,cost,effects,buildings,TYPE));
			break;
			case "ventures":AS.add(new TowerActionSpace(id,cost,effects,ventures,TYPE));
			break;
			case "characters":AS.add(new TowerActionSpace(id,cost,effects,characters,TYPE));
			break;
    	}
	}	
}
