package gamemodel;

import gamemodel.effects.Effect;

public abstract class PEffect implements Effect{
	
	public String tag;
		
	public abstract void activate(Player p);

	public PEffect(String tag) {
		super();
		this.tag = tag;
	}
	

}
