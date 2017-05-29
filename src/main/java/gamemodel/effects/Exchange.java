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
		if(pointsIn!=null)
			player.addPoint(pointsIn);
		if(pointsOut!=null)
			player.subPoint(pointsOut);
		if(resourcesIn!=null)
			player.addResources(resourcesIn);
		if(resourcesOut!=null)
			player.subResources(resourcesOut);
		if(councilPrivilegesIn!=null)
			councilPrivilegesIn.activate(player);
	}		
	@Override
	public String toString() {
		String str ="\n"+ "Exchange: "+"\n";
		str+="give --> ";
		if(this.pointsOut!=null)
		if(this.resourcesOut!=null)
			str+=councilPrivilegesIn;
		if(this.councilPrivilegesIn!=null)
		if(this.pointsIn!=null)
			str+=pointsIn;
			str+=resourcesIn;
		if(this.resourcesIn!=null)
		str+="receive --> ";
			str+=pointsOut+"\n";
			str+=resourcesOut+"\n";
		return str;
		
	}
	
}

