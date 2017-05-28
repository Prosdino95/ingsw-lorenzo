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
	private Effect councilPrivilegesIn;
	
	public Exchange(Point pointsIn,Point pointsOut,Resource resourcesIn,Resource resourcesOut,Effect councilPrivilegesIn)
	{
		this.resourcesIn=resourcesIn;
		this.resourcesOut=resourcesOut;
		this.pointsIn=pointsIn;
		this.pointsOut=pointsOut;
		this.councilPrivilegesIn=councilPrivilegesIn;
	}
	
	
	@Override
	public void activate(Player player)
	{
		if(this.pointsIn==null && this.pointsOut==null && this.councilPrivilegesIn==null)
		{
			player.subResources(this.resourcesOut);
			player.addResources(this.resourcesIn);
		}
		if(this.resourcesIn==null && this.resourcesOut==null && this.councilPrivilegesIn==null)
		{
			player.subPoint(this.pointsOut);
			player.addPoint(this.pointsIn);
		}
		if(this.pointsOut==null && this.resourcesIn==null && this.councilPrivilegesIn==null)
		{
			player.subResources(this.resourcesOut);
			player.addPoint(this.pointsIn);
		}
		if(this.pointsIn==null && this.resourcesOut==null && this.councilPrivilegesIn==null)
		{
			player.subPoint(this.pointsOut);
			player.addResources(this.resourcesIn);
		}
		if(this.pointsIn==null && this.resourcesOut==null && this.resourcesIn==null)
		{
			player.subPoint(this.pointsOut);
			this.councilPrivilegesIn.activate(player);
		}
		if(this.pointsIn==null && this.pointsOut==null && this.resourcesIn==null)
		{
			player.subResources(this.resourcesOut);
			this.councilPrivilegesIn.activate(player);
		}
		
		if(this.pointsIn==null && this.pointsOut==null && this.resourcesIn==null)
		{
			player.subResources(this.resourcesOut);
			this.councilPrivilegesIn.activate(player);
		}
	}
	@Override
	public String toString() {
		String str ="\n"+ "Exchange: "+"\n";
		str+="give --> ";
		if(this.resourcesOut!=null)
			str+=resourcesOut+"\n";
		if(this.pointsOut!=null)
			str+=pointsOut+"\n";
		str+="receive --> ";
		if(this.resourcesIn!=null)
			str+=resourcesIn;
		if(this.pointsIn!=null)
			str+=pointsIn;
		if(this.councilPrivilegesIn!=null)
			str+=councilPrivilegesIn;
		return str;
		
	}
}

