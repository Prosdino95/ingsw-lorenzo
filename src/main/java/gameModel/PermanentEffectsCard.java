package gameModel;

import java.util.List;

public class PermanentEffectsCard extends RealCard {
	
	private List<Effect> permanentEffects;
	


	public PermanentEffectsCard(Resource requirement, Resource price, 
			CardType type, List<Effect> permanentEffects) {
		super(requirement, price, type);
		this.permanentEffects = permanentEffects;
	}
	
	public PermanentEffectsCard(Resource requirement, Resource price, 
			CardType type,Effect permanentEffects) {
		super(requirement, price, type);
		this.permanentEffects.add(permanentEffects);
	}



	public void activatePermanentEffects(RealPlayer p){
		for(Effect e:permanentEffects)
			e.activate(p);
	}	

}
