package gamemodel.jsonparsing;

import java.util.ArrayList;

import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import gamemodel.effects.*;
import gamemodel.player.Point;
import gamemodel.player.Resource;
import gamemodel.*;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.card.CardType;


public class IstantEffectParsing {
	
	private List<IstantEffect> istantEffect;
	
	
	public IstantEffectParsing(){
		istantEffect=new ArrayList<>();
	}

	
	public List<IstantEffect> parsing(JsonArray effects,Board board) {
		String type;
		for (JsonValue item : effects){		
			type=item.asObject().getString("type", null);
			switch(type){
				case "council-privileges":istantEffect.add(councilPrivileges(item));
				break;
				case "point-mod": istantEffect.add(new PointModify(ParsingHelper.pointParsing(item)));
				break;	
				case "resource-mod": istantEffect.add(new ResourceModify(ParsingHelper.resourceParsing(item)));
				break;
				case "testeffect": istantEffect.add(new TestEffects());
				break;
				case "exchange": istantEffect.add(exchange(item));
				break;
				case "resource-for-resource": istantEffect.add(resourceForResource(item));
				break;	
				case "bonus-action": istantEffect.add(bonusAction(item,board));
			}	
		}
		return istantEffect;
	}
	
	private IstantEffect bonusAction(JsonValue item,Board board) {
		Resource resource;
		CardType cType=ParsingHelper.getCardType(item);
		ActionSpaceType asType=ParsingHelper.getActionSpaceType(item);
		if(item.asObject().get("discount")==null)
			resource=new Resource(0,0,0,0);
		else resource=ParsingHelper.resourceParsing(item.asObject().get("discount"));
		int actionValue=item.asObject().getInt("action-value", 0);
		if(cType!=null)
			return new BonusAction(board, actionValue, cType, resource);
		else
			return new BonusAction(board, actionValue, asType);
	}


	private IstantEffect resourceForResource(JsonValue item) {
		JsonValue forWhat,receive;
		String type=null;
		int forEach=0;
		Resource rin=null,rout=null;
		Point pin=null,pout=null;
		forEach=item.asObject().getInt("quantity", 0);
		
		receive=item.asObject().get("receive");
		if(receive.asObject().get("resource")!=null)
			rout=ParsingHelper.resourceParsing(receive.asObject().get("resource"));
		
		if(receive.asObject().get("point")!=null)
			pout=ParsingHelper.pointParsing(receive.asObject().get("point"));
		
		try{type=item.asObject().getString("for",null);}
		
		catch(UnsupportedOperationException e){		
			forWhat=item.asObject().get("for");
			if(forWhat.asObject().get("resource")!=null){
				rin=ParsingHelper.resourceParsing(forWhat.asObject().get("resource"));
				type="Resource";
			}		
			else if(forWhat.asObject().get("point")!=null){
				pin=ParsingHelper.pointParsing(forWhat.asObject().get("point"));
				type="Point";
			}
		}	
		return ResourceForResource.constructor(type, rin, pin, rout, pout, forEach);
	}
	
	

	private IstantEffect exchange(JsonValue item) {
		JsonValue give,receive;
		Resource rin=null,rout=null;
		Point pin=null,pout=null;
		IstantEffect councilPrivilege=null;
		give=item.asObject().get("give");
		if(give.asObject().get("resource")!=null)
			rout=ParsingHelper.resourceParsing(give.asObject().get("resource"));
		
		if(give.asObject().get("point")!=null)
			pout=ParsingHelper.pointParsing(give.asObject().get("point"));
		
		receive=item.asObject().get("receive");
		if(receive.asObject().get("resource")!=null)
			rin=ParsingHelper.resourceParsing(receive.asObject().get("resource"));
		
		if(receive.asObject().get("point")!=null)
			pin=ParsingHelper.pointParsing(receive.asObject().get("point"));
				
		if(receive.asObject().get("council-privileges")!=null)
			councilPrivilege=councilPrivileges(receive.asObject().get("council-privileges"));
		
		return new Exchange(pin, pout, rin, rout, councilPrivilege);
			
	}
	

	private IstantEffect councilPrivileges(JsonValue item) {
		int quantity=item.asObject().getInt("quantity", 0);
		return new CouncilPrivileges(quantity);
	}

	
	
}
