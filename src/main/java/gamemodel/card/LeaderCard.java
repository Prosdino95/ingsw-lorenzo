package gamemodel.card;

import java.util.List;
import java.util.Map;

import gamemodel.CardType;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.effects.Effect;

public class LeaderCard extends RealCard {
	
	private boolean useEffect=false;
	private boolean playCard=false;
	

	
	
	public LeaderCard(String name, Resource resourceRequirement, Resource resourcePrice, Point point,
			Point pointPrice,CardType type,List<Effect> istantEffect, List<Effect> permanentEffect,
			Map<CardType, Integer> requirementCard) {
		super(name,resourceRequirement, resourcePrice, point, pointPrice, istantEffect, permanentEffect, type, requirementCard);
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
