package gamemodel.effects;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;

public class Exchange implements Effect 
{
	private Point pointsIn;
	private Point pointsOut;
	private Resource resourcesIn;
	private Resource resourcesOut;
	
	
	public Exchange(Resource resourcesIn,Resource resourcesOut)
	{
		this.resourcesIn=resourcesIn;
		this.resourcesOut=resourcesOut;
		this.pointsIn=null;
		this.pointsOut=null;
	}
	public Exchange(Point pointsIn,Point pointsOut)
	{
		this.pointsIn=pointsIn;
		this.pointsOut=pointsOut;
		this.resourcesIn=null;
		this.resourcesOut=null;
		
	}
	public Exchange(Point pointsIn,Resource resourcesOut)
	{
		this.pointsIn=pointsIn;
		this.pointsOut=null;
		this.resourcesIn=null;
		this.resourcesOut=resourcesOut;
		
	}
	public Exchange(Resource resourcesIn,Point pointsOut)
	{
		this.pointsIn=null;
		this.pointsOut=pointsOut;
		this.resourcesIn=resourcesIn;
		this.resourcesOut=null;
		
	}
	
	@Override
	public void activate(Player player)
	{
		if(this.pointsIn==null && this.pointsOut==null)
		{
			player.subResources(this.resourcesOut);
			player.addResources(this.resourcesIn);
		}
		if(this.resourcesIn==null && this.resourcesOut==null)
		{
			player.subPoint(this.pointsOut);
			player.addPoint(this.pointsIn);
		}
		if(this.pointsOut==null && this.resourcesIn==null)
		{
			player.subResources(this.resourcesOut);
			player.addPoint(this.pointsIn);
		}
		if(this.pointsIn==null && this.resourcesOut==null)
		{
			player.subPoint(this.pointsOut);
			player.addResources(this.resourcesIn);
		}
		
		
	}
	@Override
	public String toString() {
		String str = "";
		str+="In --> ";
		str+="out --> ";
		
		return "Exchange [pointsIn=" + pointsIn + ", pointsOut=" + pointsOut + ", resourcesIn=" + resourcesIn
				+ ", resourcesOut=" + resourcesOut + "]";
	}
}

