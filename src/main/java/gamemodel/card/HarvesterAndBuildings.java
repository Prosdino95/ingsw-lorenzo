package gamemodel.card;

import java.util.Map;

import gamemodel.CardType;
import gamemodel.Point;
import gamemodel.Resource;

public class HarvesterAndBuildings extends RealCard
{
	private int actionCost;
	
	public HarvesterAndBuildings(Resource resourceRequirement, Resource resourcePrice, Point point, 
			Point pointPrice, CardType type,Map<CardType, Integer> requirementCard,int actionCost)
	{
		super(resourceRequirement,resourcePrice,point,pointPrice,type,requirementCard);
		this.actionCost=actionCost;
	}
	
}
