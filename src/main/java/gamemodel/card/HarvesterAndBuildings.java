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

public class HarvesterAndBuildings extends Card implements Serializable
{

	private static final long serialVersionUID = 1L;
	private Integer actionCost;
	private List<IstantEffect> activateEffect;
	
	public HarvesterAndBuildings(int id,String name,int period, Resource resourceRequirement, Resource resourcePrice, 
			Point point,Point pointPrice, List<IstantEffect> istantEffects,List<IstantEffect> activateEffect, 
			CardType type,int actionCost)
	{
		super(id,name,period,resourceRequirement,resourcePrice,point,pointPrice, istantEffects,type);
		this.actionCost=actionCost;
		this.activateEffect=activateEffect;
	}
	
	public Integer getActionCost(){
		return this.actionCost;
	}
	
	public void activePermanentEffect(Player p) throws GameException {
		for(IstantEffect e:this.activateEffect)
			e.activate(p);	
	}


	public Collection<IstantEffect> getActivateEffects() {
		return this.activateEffect;
	}

	/*@Override
	public String toString() {
		return "HarvesterAndBuildings [actionCost=" + actionCost + ", name=" + name + ", resourceRequirement="
				+ resourceRequirement + ", resourcePrice=" + resourcePrice + ", pointRequirement=" + pointRequirement
				+ ", pointPrice=" + pointPrice + ", istantEffect=" + istantEffect + ", type=" + type + ", id=" + id + ", requirementCard=" + requirementCard + "]";
	}*/
	
	
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
		if(this.activateEffect!=null)
			str +="Action effect-> "+this.activateEffect+ "\n";		
		return str;
	}

}
