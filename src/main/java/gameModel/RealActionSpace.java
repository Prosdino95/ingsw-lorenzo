package gameModel;

import java.util.*;

public class RealActionSpace implements ActionSpace {
	private final int id;
	private static int identifier=0;
	private boolean free=true;
	private final int actionCost;
	private List<Effect> effects=new ArrayList<>();

	public RealActionSpace(int actionCost, List<Effect> effects) {
		this.id=identifier;
		identifier++;
		this.actionCost = actionCost;
		this.effects = effects;
	}

	public RealActionSpace(int actionCost, Effect effect) {
		this.id=identifier;
		identifier++;
		this.actionCost = actionCost;
		this.effects.add(effect);
	}
	
	@Override
	public void activateEffect(FamilyMember f){
		System.out.println("attivato effetto di Spazio azione n "+id);
		for(Effect e:effects)
			e.activate(f.getPlayer());
	}
	
	@Override
	public void occupy(){
		free=false;
	}

	@Override
	public boolean isFree() {
		return free;
	}

	@Override
	public int getActionCost() {
		return actionCost;
	}

	@Override
	public int getId() {
		return id;
	}
}
