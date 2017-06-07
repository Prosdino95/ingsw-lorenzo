package gamemodel.card;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;

public class CharactersCard extends RealCard implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<PermanentEffect> permanentEffect;

	public CharactersCard(String name, int period, Resource resourceRequirement, Resource resourcePrice, Point point,
			Point pointPrice, List<IstantEffect> istantEffects,List<PermanentEffect> permanentEffect, CardType type, Map<CardType, Integer> requirementCard) {
		super(name, period, resourceRequirement, resourcePrice, point, pointPrice, istantEffects, type, requirementCard);
		this.permanentEffect=permanentEffect;
	}

	public List<PermanentEffect> getPermanentEffect() {
		return permanentEffect;
	}
	
}
