package gamemodel.jsonparsing;

import java.util.ArrayList;



import java.util.List;

import com.eclipsesource.json.*;

import gamemodel.Board;
import gamemodel.actionSpace.*;
import gamemodel.effects.*;


public class ASParsing {
	
	private List<ActionSpace> AS=new ArrayList<>();
	private List<IstantEffect> effects=new ArrayList<>();
	private ActionSpaceType type;
	private int cost;
	private int id;
	private Board board;
		

	public ASParsing(Board board) {
		this.board=board;
	}

	public List<ActionSpace> parsing(String json){
		JsonArray items = Json.parse(json).asObject().get("ActionSpace").asArray();
		for (JsonValue item : items) {
			effects=new ArrayList<>();
			id=item.asObject().getInt("id", -1);
    		cost=item.asObject().getInt("action-cost", 1);
    		if(item.asObject().get("effect")!=null){
    			effects=new ArrayList<>();
    			effects=new IstantEffectParsing().parsing(item.asObject().get("effect").asArray(),board);
    		}
    		makeAS(item.asObject().getString("type", null));
    		
    	}	
		return AS;
	}
	
    private ActionSpaceType makeAS(String s) {
    	switch(s){
    		case "market":marketAS();
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
		AS.add(new MemoryActionSpace(id,cost, effects, type));   	
		
	}

	private void marketAS(){
    	this.type=ActionSpaceType.MARKET;
    	AS.add(new ActionSpace(id,cost, effects, type));   	
    }

}
