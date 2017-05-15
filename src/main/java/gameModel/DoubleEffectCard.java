package gameModel;

import java.util.List;

public class DoubleEffectCard extends RealCard {
	
	private List<Effect> immediateEffects;
	private List<Effect> permanentEffects;
	
	
	
	public DoubleEffectCard(Resource requirement, Resource price, CardType type, 
				List<Effect> immediateEffects,List<Effect> permanentEffects) {
		super(requirement, price, type);
		this.immediateEffects = immediateEffects;
		this.permanentEffects = permanentEffects;
	}
	
	public DoubleEffectCard(Resource requirement, Resource price, CardType type, 
					Effect immediateEffects,Effect permanentEffects) {
			super(requirement, price, type);
			this.immediateEffects.add(immediateEffects);
			this.permanentEffects.add(permanentEffects);
		}

	public void activateImmediateEffect(RealPlayer p){
		for(Effect e:immediateEffects)
			e.activate(p);
	}
				
	public void activatePermanentEffects(RealPlayer p){
		for(Effect e:permanentEffects)
			e.activate(p);	
	}
	
}
