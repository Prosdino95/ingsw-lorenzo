package gamemodel.card;

import java.util.Map;

import gamemodel.CardType;
import gamemodel.Point;
import gamemodel.Resource;

public class LeaderCard extends RealCard {
	
	private boolean useEffect=false;
	private boolean playCard=false;
	

	
	
	public LeaderCard(Resource resourceRequirement, Resource resourcePrice, Point point, Point pointPrice,
			CardType type, Map<CardType, Integer> requirementCard) {
		super(resourceRequirement, resourcePrice, point, pointPrice, type, requirementCard);
	}
	
	public void useEffect(){
		useEffect=true;
	}
	
	public void playCard(){
		playCard=true;		
	}
	
	public void precapeForNewRound(){
		playCard=false;
	}

	public boolean isUseEffect() {
		return useEffect;
	}

	public boolean isPlayCard() {
		return playCard;
	}
	
	
		
}
