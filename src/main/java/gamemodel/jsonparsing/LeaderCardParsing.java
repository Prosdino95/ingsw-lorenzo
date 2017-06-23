package gamemodel.jsonparsing;

import java.util.ArrayList;

import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.CardRequirement;
import gamemodel.LeaderCard;
import gamemodel.Requirement;
import gamemodel.Resource;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;

public class LeaderCardParsing {
	
	private int id;
	private String name;
	private Requirement requirements;
	private List<IstantEffect> activateEffect;
	private PermanentEffect permanentEffect;
	private List<LeaderCard>leaderCard=new ArrayList<>();
	
	public List<LeaderCard> parsing(String json){
		JsonArray items = Json.parse(json).asObject().get("leader-cards").asArray();
		for (JsonValue item : items) {
			permanentEffect=null;
			id=item.asObject().getInt("id", 0);
			name=item.asObject().getString("name", null);
			requirements=requirementsParsing(item.asObject().get("requirements"));
			if(item.asObject().get("activate-effect")!=null){
    			activateEffect=new ArrayList<>();
    			activateEffect=new IstantEffectParsing().parsing(item.asObject().get("activate-effect").asArray());
    			leaderCard.add(new LeaderCard(id, name, requirements, activateEffect));
    		}
    		if(item.asObject().get("permanent-effect")!=null){
    			permanentEffect=new PermanentEffectParsing().parsing(item.asObject().get("permanent-effect"));
    			leaderCard.add(new LeaderCard(id, name, requirements, permanentEffect));
    		}		
		}
		return leaderCard;
		
	}

	private Requirement requirementsParsing(JsonValue item) {
		String type=item.asObject().getString("type", "");
		switch(type){
		case"card":return new Requirement(cardParsing(item));
		case"point":return new Requirement(ParsingHelper.pointParsing(item));
		case"resource":return new Requirement(ParsingHelper.resourceParsing(item));
		}
		return null;
	}

	private CardRequirement cardParsing(JsonValue item) {
		int vc=item.asObject().getInt("venture", 0);
		int cc=item.asObject().getInt("character", 0);
		int tc=item.asObject().getInt("territory", 0);
		int bc=item.asObject().getInt("building", 0);
		return new CardRequirement(tc,cc,bc,vc);
	}

}
