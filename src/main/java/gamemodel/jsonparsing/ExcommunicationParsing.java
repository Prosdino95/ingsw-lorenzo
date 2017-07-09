package gamemodel.jsonparsing;

import java.util.ArrayList;

import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.card.Excommunication;
import gamemodel.permanenteffect.PermanentEffect;

/**
 * This class has the methods for parsing 
 * Excommunication Cards from the configuration file;
 */
public class ExcommunicationParsing {
	private int period;
	private PermanentEffect permanentEffects;
	private List<Excommunication> excommunication=new ArrayList<>();
	
	
	public List<Excommunication> parsing(String json){		
		JsonArray items = Json.parse(json).asObject().get("excommunication").asArray();
		for (JsonValue item : items){
			period=item.asObject().getInt("period", 0);
			permanentEffects=new PermanentEffectParsing().parsing(item.asObject().get("effect"));
			excommunication.add(new Excommunication(period,permanentEffects));
		}
		return excommunication;
	}
}
