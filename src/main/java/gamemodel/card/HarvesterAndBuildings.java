package gamemodel.card;

import java.util.List;
import java.util.Map;

import gamemodel.CardType;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.effects.Effect;

public class HarvesterAndBuildings extends RealCard
{
	private int actionCost;
	
	public HarvesterAndBuildings(String name, Resource resourceRequirement, Resource resourcePrice, 
			Point point,Point pointPrice, List<Effect> istantEffects,List<Effect> permanentEffect, 
			CardType type,Map<CardType, Integer> requirementCard,int actionCost)
	{
		super(name,resourceRequirement,resourcePrice,point,pointPrice, istantEffects,permanentEffect, type,requirementCard);
		this.actionCost=actionCost;
	}
	
	@Override
	public String toString(){
		String str = "";
		str +=this.type+"\n";
		str +="id:"+this.id+" "+this.name+"\n";
		if(resourceRequirement!=resourcePrice)
			str +="resource requirement-> "+this.resourceRequirement+ "\n";
		if(resourcePrice!=null)
			str +="resource price-> "+this.resourcePrice+ "\n";	
		if(pointRequirement!=pointPrice)
			str +="point requirement-> "+this.pointRequirement+ "\n";
		if(pointPrice!=null)
			str +="point price-> "+this.pointPrice+ "\n";
		if(this.istantEffect!=null)
			str +="istant effect-> "+this.istantEffect+ "\n";		
		str +="Action Cost-> "+this.actionCost+ "\n";
		if(this.permanentEffect!=null)
			str +="Action effect-> "+this.istantEffect+ "\n";		
		str+="\n";
		return str;
	}
// TODO implementare gli override delle funzioni della sopra-classe	
}
