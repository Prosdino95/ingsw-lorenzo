package gamemodel.jsonparsing;

import java.util.ArrayList;


import java.util.List;

import com.eclipsesource.json.*;

import gamemodel.ActionSpace.*;
import gamemodel.effects.*;


public class ASParsing {
	
	private List<RealActionSpace> AS=new ArrayList<>();
	private List<Effect> effects;
	private ActionSpaceType type;
	private int cost;
	
	
		
	public List<RealActionSpace> parsing(JsonArray items){
		for (JsonValue item : items) {
    		cost=item.asObject().getInt("action-cost", 1);
    		effects=new ArrayList<>();
    		effects=new EffectParsing().parsing(item.asObject().get("effect").asArray());
    		makeAS(item.asObject().getString("type", null));
    	}	
		return AS;
	}
	
    private ActionSpaceType makeAS(String s) {
    	switch(s){
    		case "market":marketAS();
    		break;
    		case "tower":towerAS();
    		break;
    		case "harvest":memoryAS(ActionSpaceType.HARVEST);
    		break;
    		case "production":memoryAS(ActionSpaceType.PRODUCTION);
    		break;
    		case "council-place":memoryAS(ActionSpaceType.COUNCIL_PALACE);
    		break;
    	}
		return null;
	}
    
    private void memoryAS(ActionSpaceType type) {
		this.type=type;
		AS.add(new MemoryActionSpace(cost, effects, type));   	
		
	}

	private void towerAS() {
		// TODO Auto-generated method stub
		
	}

	private void marketAS(){
    	this.type=ActionSpaceType.MARKET;
    	AS.add(new RealActionSpace(cost, effects, type));   	
    }

}
