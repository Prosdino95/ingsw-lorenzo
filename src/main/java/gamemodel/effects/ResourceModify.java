package gamemodel.effects;

import java.io.Serializable;

import gamemodel.Player;
import gamemodel.Resource;

public class ResourceModify implements Effect,EffectRollBack,Serializable
{

	private static final long serialVersionUID = 1L;
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
	@Override
	public void rollBack(Player player)
	{
		player.subResources(resources);
	}

	@Override
	public String toString() {
		return "ResourceModify [resources=" + resources + "]";
	}

//	@Override
//	public String toString() {
//		return  ""+resources;
//	}
	
	
	
}



