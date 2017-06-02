package gamemodel.permanenteffect;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Player;
import gamemodel.effects.Effect;

public class PermanentEffect implements Effect
{
	
	private List<String> tag= new ArrayList<>();
	
	@Override
	public void activate(Player p){
		
	}

	public PermanentEffect(String tag) 
	{
		this.tag.add(tag);
	}
	
	public PermanentEffect(List<String> tag) 
	{
		this.tag=tag;
	}
	
	public boolean hasTag(String tag){
		return this.tag.contains(tag);
	}
	

}
