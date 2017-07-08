package gamemodel.jsonparsing;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

/**
 * This class have the methods for parsing 
 * faith points requirements for the Vatican Support. 
 * from configuration file;
 */
public class FaithRequirements {
	
	List<Integer> points=new ArrayList<>();
	
	public List<Integer> parsing(String json){	
		JsonObject item=Json.parse(json).asObject();
		points.add(item.getInt("first-period", 0));
		points.add(item.getInt("second-period", 0));
		points.add(item.getInt("third-period", 0));
		return points;
	}

}
