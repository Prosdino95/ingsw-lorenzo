package gamemodel.effects;

import gamemodel.Player;
import gamemodel.Resource;

public class ResourceModify implements Effect 
{
	private Resource resources;
	
	public ResourceModify(Resource resources)
	{
		this.resources=resources;
	}
	
	@Override
	public void activate(Player player)
	{
		player.addResources(resources);
	}
}



