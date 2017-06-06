package gamemodel.card;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamemodel.CardType;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.command.GameException;
import gamemodel.effects.Effect;

public class RealCard implements Card,Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String name;
	private int period;
	protected Resource resourceRequirement;
	protected Resource resourcePrice;
	protected Point pointRequirement;
	protected Point pointPrice;
	protected List<Effect> istantEffect;
	protected List<Effect> permanentEffect;
	protected CardType type;
	protected final int id;
	private static int identifier=0;
	protected Map<CardType,Integer> requirementCard=new HashMap<>();
	
	public RealCard(String name,int period,Resource resourceRequirement, Resource resourcePrice, Point point, 
			Point pointPrice, List<Effect> istantEffects,List<Effect> permanentEffect, CardType type,
			Map<CardType, Integer> requirementCard) {	
		this.name=name;
		this.period=period;
		this.id=identifier;
		identifier++;
		this.resourceRequirement = resourceRequirement;
		this.resourcePrice = resourcePrice;
		this.pointRequirement = point;
		this.pointPrice = pointPrice;
		this.type = type;
		this.requirementCard = requirementCard;
		this.istantEffect=istantEffects;
		this.permanentEffect=permanentEffect;
	}
		

	public boolean controlResource(Player p,Resource discount){
		if(resourceRequirement!=null)
			return p.isEnoughtResource(resourceRequirement.minus(discount));
		if(pointRequirement!=null)
			return p.isEnoughtPoint(pointRequirement);
		if(!requirementCard.isEmpty())
			return(requirementCard.get(CardType.BUILDINGS)>=p.contCard(CardType.BUILDINGS)) 
					&&(requirementCard.get(CardType.CHARACTERS)>=p.contCard(CardType.CHARACTERS))
					&&(requirementCard.get(CardType.VENTURES)>=p.contCard(CardType.VENTURES))
					&&(requirementCard.get(CardType.TERRITORIES)>=p.contCard(CardType.TERRITORIES));
		return true;		
	}

	@Override
	public void pay(Player p,Resource discount){
		if(resourcePrice!=null)
			p.subResources(resourcePrice.minus(discount));	
		if(pointPrice!=null)
			p.subPoint(pointPrice);
	}
	
	public Resource getResourceRequirement() {
		return resourceRequirement;
	}

	public Resource getResourcePrice() {
		return resourcePrice;
	}

	public Point getPointRequirement() {
		return pointRequirement;
	}

	public Point getPointPrice() {
		return pointPrice;
	}

	public CardType getType() {
		return type;
	}
	
	public int getPeriod(){
		return this.period;
	}
	
	public int getId() {
		return id;
	}

	public Map<CardType, Integer> getRequirementCard() {
		return requirementCard;
	}

	
//	@Override
//	public String toString(){
//		String str = "RealCard [\n";
//		str +="id:"+this.id+" "+this.type+"\n"+this.name+"\n";
//		if(this.resourceRequirement!=resourcePrice)
//			str +="resource requirement-> "+this.resourceRequirement+ "\n";
//		if(this.resourcePrice!=null)
//			str +="resource price-> "+this.resourcePrice+ "\n";	
//		if(this.pointRequirement!=pointPrice)
//			str +="point requirement-> "+this.pointRequirement+ "\n";
//		if(this.pointPrice!=null)
//			str +="point price-> "+this.pointPrice+ "\n";
//		if(this.istantEffect!=null)
//			str +="istant effect-> "+this.istantEffect+ "\n";
//		if(this.permanentEffect!=null)
//			str +="permanent effect-> "+this.permanentEffect+ "\n";
//		str+="\n";
//		str+="]";
//		return str;
//	}


	@Override
	public void activeIstantEffect(Player p) throws GameException {
		for(Effect e:this.istantEffect)
			e.activate(p);		
	}
	
	public String toString() {
		return "RealCard [name=" + name + ", resourceRequirement=" + resourceRequirement + ", resourcePrice="
				+ resourcePrice + ", pointRequirement=" + pointRequirement + ", pointPrice=" + pointPrice
				+ ", istantEffect=" + istantEffect + ", permanentEffect=" + permanentEffect + ", type=" + type + ", id="
				+ id + ", requirementCard=" + requirementCard + "]";
	}


	@Override
	public void activePermanentEffect(Player p) throws GameException {
		for(Effect e:this.permanentEffect)
			e.activate(p);	
	}


	@Override
	public Collection<Effect> getPermanentEffects() {
		return this.permanentEffect;
	}




}
