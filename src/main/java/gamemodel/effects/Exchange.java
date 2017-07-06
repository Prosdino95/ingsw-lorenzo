package gamemodel.effects;

import java.io.Serializable;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.command.GameError;
import gamemodel.command.GameException;

public class Exchange implements IstantEffect,Serializable 
{

	private static final long serialVersionUID = 1L;
	private Point pointsIn;
	private Point pointsOut;
	private Resource resourcesIn;
	private Resource resourcesOut;
	private IstantEffect councilPrivilegesIn;
	
	public Exchange(Point pointsIn,Point pointsOut,Resource resourcesIn,Resource resourcesOut,IstantEffect councilPrivilegesIn)
	{
		this.resourcesIn=resourcesIn;
		this.resourcesOut=resourcesOut;
		this.pointsIn=pointsIn;
		this.pointsOut=pointsOut;
		this.councilPrivilegesIn=councilPrivilegesIn;
	}
	
	public boolean canExchange(Player player){
		return (player.isEnoughtPoint(this.pointsOut) && player.isEnoughtResource(this.resourcesOut));
	}
	
	@Override
	public void activate(Player player) throws GameException
	{
		if(!player.isEnoughtPoint(this.pointsOut))
			return;
			//throw new GameException(GameError.RESOURCE_ERR_EFFECT);
		if(!player.isEnoughtResource(this.resourcesOut))
			return;
			//throw new GameException(GameError.RESOURCE_ERR_EFFECT);
		player.addPoint(pointsIn);
		player.subPoint(pointsOut);
		player.addResources(resourcesIn);
		player.subResources(resourcesOut);
		if(councilPrivilegesIn!=null)
			councilPrivilegesIn.activate(player);
	}

	@Override
	public String toString() {
		//String str ="Exchange:\n";
		String str="";
		//str+="give-->";
		if(this.pointsOut!=null)
			str+=pointsOut;
		if(this.resourcesOut!=null)
			str+=resourcesOut;
		str+=" -->";
		if(this.councilPrivilegesIn!=null)
			str+=councilPrivilegesIn;
		if(this.pointsIn!=null)
			str+=pointsIn;			
		if(this.resourcesIn!=null)		
			str+=resourcesIn;
		return str;
	}
}

