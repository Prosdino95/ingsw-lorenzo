package gamemodel.card;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;

public class HarvesterAndBuildings extends RealCard implements Serializable
{

	private static final long serialVersionUID = 1L;
	private int actionCost;
	private List<IstantEffect> activateEffect;
	
	public HarvesterAndBuildings(String name,int period, Resource resourceRequirement, Resource resourcePrice, 
			Point point,Point pointPrice, List<IstantEffect> istantEffects,List<IstantEffect> activateEffect, 
			CardType type,Map<CardType, Integer> requirementCard,int actionCost)
	{
		super(name,period,resourceRequirement,resourcePrice,point,pointPrice, istantEffects,type,requirementCard);
		this.actionCost=actionCost;
		this.activateEffect=activateEffect;
	}
	
	public int getActionCost(){
		return this.actionCost;
	}
	
	public void activePermanentEffect(Player p) throws GameException {
		for(IstantEffect e:this.activateEffect)
			e.activate(p);	
	}


	public Collection<IstantEffect> getActivateEffects() {
		return this.activateEffect;
	}

	@Override
	public String toString() {
		return "HarvesterAndBuildings [actionCost=" + actionCost + ", name=" + name + ", resourceRequirement="
				+ resourceRequirement + ", resourcePrice=" + resourcePrice + ", pointRequirement=" + pointRequirement
				+ ", pointPrice=" + pointPrice + ", istantEffect=" + istantEffect + ", type=" + type + ", id=" + id + ", requirementCard=" + requirementCard + "]";
	}
	
	
//	@Override
//	public String toString(){
//		String str = "HB Card [\n";
//		str +=this.type+"\n";
//		str +="id:"+this.id+" "+this.name+"\n";
//		if(resourceRequirement!=resourcePrice)
//			str +="resource requirement-> "+this.resourceRequirement+ "\n";
//		if(resourcePrice!=null)
//			str +="resource price-> "+this.resourcePrice+ "\n";	
//		if(pointRequirement!=pointPrice)
//			str +="point requirement-> "+this.pointRequirement+ "\n";
//		if(pointPrice!=null)
//			str +="point price-> "+this.pointPrice+ "\n";
//		if(this.istantEffect!=null)
//			str +="istant effect-> "+this.istantEffect+ "\n";		
//		str +="Action Cost-> "+this.actionCost+ "\n";
//		if(this.permanentEffect!=null)
//			str +="Action effect-> "+this.permanentEffect+ "\n";		
//		str+="]";
//		return str;
//	}

}
