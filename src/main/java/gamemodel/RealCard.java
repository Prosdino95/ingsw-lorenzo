package gamemodel;

import java.util.HashMap;
import java.util.Map;

public class RealCard implements Card {
		
	private Resource resourceRequirement;
	private Resource resourcePrice;
	private Point pointRequirement;
	private Point pointPrice;
	private CardType type;
	private final int id;
	private static int identifier=0;
	private Map<CardType,Integer> requirementCard=new HashMap<>();
		

	public boolean ControlResource(Player p){
		if(resourceRequirement instanceof Resource==true)
			return p.isEnoughtResource(resourceRequirement);
		if(pointRequirement instanceof Point==true)
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
		if(resourcePrice instanceof Resource==true)
		p.subResources(resourcePrice);	
		if(pointPrice instanceof Point==true)
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

	public RealCard(Resource resourceRequirement, Resource resourcePrice, Point point, 
			Point pointPrice, CardType type,Map<CardType, Integer> requirementCard) {
		this.id=identifier;
		identifier++;
		this.resourceRequirement = resourceRequirement;
		this.resourcePrice = resourcePrice;
		this.pointRequirement = point;
		this.pointPrice = pointPrice;
		this.type = type;
		this.requirementCard = requirementCard;
	}

}
