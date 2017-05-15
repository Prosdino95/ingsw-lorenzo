package gameModel;

import java.util.List;

public class LeaderCard extends DoubleEffectCard {
	
	private boolean useEffect=false;
	private boolean playCard=false;
	
	public LeaderCard(Resource requirement, Resource price, CardType type, 
			List<Effect> immediateEffects,List<Effect> permanentEffects) {
		super(requirement, price, type, immediateEffects, permanentEffects);
	}
	
	public LeaderCard(Resource requirement, Resource price, CardType type, 
			Effect immediateEffects,Effect permanentEffects) {
		super(requirement, price, type, immediateEffects, permanentEffects);
	}


	public boolean isUseEffect() {
		return useEffect;
	}

	public boolean isPlayCard() {
		return playCard;
	}
	
	
		
}
