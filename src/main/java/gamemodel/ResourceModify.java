package gamemodel;

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



