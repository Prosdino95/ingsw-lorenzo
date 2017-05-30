package gamemodel;

import gamemodel.effects.Effect;

public abstract class PermanentEffect implements Effect
{
	
	public String tag;
		
	public abstract void activate(Player p);

	public PermanentEffect(String tag) 
	{
		super();
		this.tag = tag;
	}
	

}
