package gamemodel.jsonparsing;


import com.eclipsesource.json.JsonValue;

import gamemodel.Resource;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.card.CardType;
import gamemodel.permanenteffect.*;

public class PermanentEffectParsing {
	
	PermanentEffect effects;

	public PermanentEffect parsing(JsonValue item) {
		String type;	
		type=item.asObject().getString("type", null);
		switch(type){
		case"dubuff-point":return new Debuff(ParsingHelper.pointParsing(item));
		case"dubuff-resource":return new Debuff(ParsingHelper.resourceParsing(item));
		case"strength-mod":return strenfthModify(item);
		case"discount":return discount(item);
		case"family-member-set":return FamilyMemberModify.coloredMembersSet(item.asObject().getInt("set", 0));
		case"family-member-debuff":return FamilyMemberModify.coloredMembersDebuff(item.asObject().getInt("debuff", 0));
		case"family-member-buff":return familiMemberBuff(item);
		case"no-action-space":return new NoActionSpace(getActionSpaceType(item));
		case"halve-servants":return new PermanentEffect(PEffect.HALVE_SERVANTS);
		case"no-first-action":return new PermanentEffect(PEffect.NO_FIRST_ACTION);
		case"victory-mod":return victoryEffect(item);
		case"no-bonus":return new PermanentEffect(PEffect.NO_BONUS);
		case"no-matter-if-occupied":return new PermanentEffect(PEffect.NO_MATTER_IF_OCCUPIED); 
		case"no-need-to-pay-3-coins":return new PermanentEffect(PEffect.NO_NEED_TO_PAY_3_COINS);
		case"five_addictional-victory-points-when-support-the-church":return new PermanentEffect(PEffect.FIVE_ADDITIONAL_VICTORY_POINTS_WHEN_SUPPORT_THE_CHURCH);
		case"resoutce-twice-from-development-cards-istamt-effect":return new PermanentEffect(PEffect.RESOURCES_TWICE_FROM_DEVELOPEMENT_CARDS_ISTANT_EFFECT);
		default: throw new RuntimeException("effetto sconosciuto");				
		}
	}
	
	private PermanentEffect familiMemberBuff(JsonValue item){
		String type=item.asObject().getString("family-type", null);
		if(type==null)
			return FamilyMemberModify.coloredMembersBuff(item.asObject().getInt("buff", 0));
		else 
			return FamilyMemberModify.oneMembersBuff(item.asObject().getInt("buff", 0),ParsingHelper.getColor(type));
	}

	private PermanentEffect strenfthModify(JsonValue item) {
		Resource discount;
		int modPower=item.asObject().getInt("value", 0);
		ActionSpaceType asType=getActionSpaceType(item);
		CardType cType=ParsingHelper.getCardType(item);
		if(item.asObject().get("discount")!=null){
			discount=ParsingHelper.resourceParsing(item.asObject().get("discount"));
			return new StrengthModifyAndDiscount(modPower,asType,cType,discount);
		}
		return new StrengthModifyAndDiscount(modPower,asType,cType);			 
	}
	
	private PermanentEffect discount(JsonValue item){
		CardType cType=ParsingHelper.getCardType(item);
		Resource discount=ParsingHelper.resourceParsing(item);
		return new StrengthModifyAndDiscount(discount,cType);
	}
	
	private ActionSpaceType getActionSpaceType(JsonValue item){
		String s=item.asObject().getString("action-type","");
		switch(s){
		case"harvest":return ActionSpaceType.HARVEST;
		case"production":return ActionSpaceType.PRODUCTION;
		case"council-place":return ActionSpaceType.COUNCIL_PALACE;	
		case"market":return ActionSpaceType.MARKET;
		case"tower":return ActionSpaceType.TOWER;
		default: return null;	
		}
	}

	
	private PermanentEffect victoryEffect(JsonValue item){
		String s=item.asObject().getString("mod","");
		switch(s){
		case"no-victory-points-bounded-to-character-cards":return new PermanentEffect(PEffect.NO_VICTORY_POINTS_BOUNDED_TO_CHARACTER_CARDS);
		case"no-victory-points-bounded-to-venture-cards":return new PermanentEffect(PEffect.NO_VICTORY_POINTS_BOUNDED_TO_VENTURE_CARDS);
		case"no-victory-points-bounded-to-territory-cards":return new PermanentEffect(PEffect.NO_VICTORY_POINTS_BOUNDED_TO_TERRITORY_CARDS) ;
		case"lose-one-victory-point-for-every-five-victory-point":return new PermanentEffect(PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_FIVE_VICTORY_POINT) ;
		case"lose-one-victory-point-for-every-military-point":return new PermanentEffect(PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_MILITARY_POINT) ;
		case"lose-one-victory-point-for-every-wood-and-stone-on-your-buildings-cards-cost":return new PermanentEffect(PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_WOOD_AND_STONE_ON_YOUR_BUILDINGS_CARDS_COST);
		case"lose-one-victory-point-for-every-resource":return new PermanentEffect(PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_RESOURCE);
		default:return null;
		}
	}

}
