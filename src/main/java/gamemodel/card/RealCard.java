package gamemodel.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamemodel.CardType;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.effects.Effect;

public class RealCard implements Card {
	
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
		

	public boolean ControlResource(Player p){
		if(resourceRequirement!=null)
			return p.isEnoughtResource(resourceRequirement);
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
	public void pay(Player p){
		if(resourcePrice!=null)
			p.subResources(resourcePrice);	
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

	
	@Override
	public String toString(){
		String str = "";
		str +="id:"+this.id+" "+this.type+"\n"+this.name+"\n";
		if(this.resourceRequirement!=resourcePrice)
			str +="resource requirement-> "+this.resourceRequirement+ "\n";
		if(this.resourcePrice!=null)
			str +="resource price-> "+this.resourcePrice+ "\n";	
		if(this.pointRequirement!=pointPrice)
			str +="point requirement-> "+this.pointRequirement+ "\n";
		if(this.pointPrice!=null)
			str +="point price-> "+this.pointPrice+ "\n";
		if(this.istantEffect!=null)
			str +="istant effect-> "+this.istantEffect+ "\n";
		if(this.permanentEffect!=null)
			str +="permanent effect-> "+this.permanentEffect+ "\n";
		str+="\n";
		return str;
	}
	

}
