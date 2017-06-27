package gamemodel;

import java.io.Serializable;

import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.PointModify;
import gamemodel.effects.ResourceModify;

public class PersonalBonusTile  implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	IstantEffect harvest;
	IstantEffect[] production=new IstantEffect[2];
	
	public PersonalBonusTile(Resource resourceH,Point pointP,Resource resourceP)
	{
		harvest=new ResourceModify(resourceH);
		production[0]=new PointModify(pointP);
		production[1]=new ResourceModify(resourceP);
	}
	
	public void activate(Player player,ActionSpaceType type) throws GameException
	{
			if(type==ActionSpaceType.HARVEST)
				harvest.activate(player);
			if(type==ActionSpaceType.PRODUCTION)
			{
				production[0].activate(player);
				production[1].activate(player);
			}
	}		
}
