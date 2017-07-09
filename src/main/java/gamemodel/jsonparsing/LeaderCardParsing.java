package gamemodel.jsonparsing;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.Board;
import gamemodel.card.CardRequirement;
import gamemodel.card.LeaderCard;
import gamemodel.card.Requirement;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;
/**
 * This class has the methods for parsing 
 * Leader Cards from the configuration file;
 */
public class LeaderCardParsing {
	
	private int id;
	private String name;
	private Requirement requirements;
	private List<IstantEffect> activateEffect;
	private PermanentEffect permanentEffect;
	private List<LeaderCard>leaderCard=new ArrayList<>();
	private Board board;
	
	public LeaderCardParsing(Board board) {
		this.board=board;
	}

	public List<LeaderCard> parsing(String json){
		JsonArray items = Json.parse(json).asObject().get("leader-cards").asArray();
		for (JsonValue item : items) {
			permanentEffect=null;
			id=item.asObject().getInt("id", 0);
			name=item.asObject().getString("name", null);
			requirements=requirementsParsing(item.asObject().get("requirements"));
			if(item.asObject().get("activate-effect")!=null){
    			activateEffect=new ArrayList<>();
    			activateEffect=new IstantEffectParsing().parsing(item.asObject().get("activate-effect").asArray(),board);
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
		case"all":return new Requirement(ParsingHelper.pointParsing(item),ParsingHelper.resourceParsing(item),cardParsing(item));
		}
		return null;
	}

	private CardRequirement cardParsing(JsonValue item) {
		int all=item.asObject().getInt("all", 0);
		if(all!=0)
			return new CardRequirement(all);
		int vc=item.asObject().getInt("venture", 0);
		int cc=item.asObject().getInt("character", 0);
		int tc=item.asObject().getInt("territory", 0);
		int bc=item.asObject().getInt("building", 0);
		return new CardRequirement(tc,cc,bc,vc);
	}

}
