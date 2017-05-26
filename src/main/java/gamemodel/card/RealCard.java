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
	protected Resource resourceRequirement;
	protected Resource resourcePrice;
	protected Point pointRequirement;
	protected Point pointPrice;
	protected List<Effect> istantEffect;
	protected CardType type;
	protected final int id;
	private static int identifier=0;
	protected Map<CardType,Integer> requirementCard=new HashMap<>();
	
	public RealCard(String name,Resource resourceRequirement, Resource resourcePrice, Point point, 
			Point pointPrice, List<Effect> istantEffects, CardType type,
			Map<CardType, Integer> requirementCard) {		
		this.name=name;
		this.id=identifier;
		identifier++;
		this.resourceRequirement = resourceRequirement;
		this.resourcePrice = resourcePrice;
		this.pointRequirement = point;
		this.pointPrice = pointPrice;
		this.type = type;
		this.requirementCard = requirementCard;
		this.istantEffect=istantEffects;
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
	
	public int getId() {
		return id;
	}

	public Map<CardType, Integer> getRequirementCard() {
		return requirementCard;
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
		str+="\n";
		return str;
	}
	

}
