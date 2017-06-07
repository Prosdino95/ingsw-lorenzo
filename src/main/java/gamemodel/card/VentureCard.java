package gamemodel.card;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;

public class VentureCard extends RealCard {

	private static final long serialVersionUID = 1L;
	private List<IstantEffect> activateEffect;

	public VentureCard(String name, int period, Resource resourceRequirement, Resource resourcePrice, Point point,
			Point pointPrice, List<IstantEffect> istantEffects,List<IstantEffect> activateEffects, CardType type, Map<CardType, Integer> requirementCard, List<IstantEffect> activateEffect) {
		super(name, period, resourceRequirement, resourcePrice, point, pointPrice, istantEffects, type, requirementCard);
		this.activateEffect=activateEffect;
	}
	
	public void activePermanentEffect(Player p) throws GameException {
		for(IstantEffect e:this.activateEffect)
			e.activate(p);	
	}


	public Collection<IstantEffect> getActivateEffects() {
		return this.activateEffect;
	}

	@Override
	public String toString() {
		return "HarvesterAndBuildings [name=" + name + ", resourceRequirement="
				+ resourceRequirement + ", resourcePrice=" + resourcePrice + ", pointRequirement=" + pointRequirement
				+ ", pointPrice=" + pointPrice + ", istantEffect=" + istantEffect + ", type=" + type + ", id=" + id + ", requirementCard=" + requirementCard + "]";
	}

}
