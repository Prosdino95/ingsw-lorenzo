package gamemodel.effects;

import gamemodel.Player;
import gamemodel.Point;

public class PointModify implements Effect,EffectRollBack
{
	private Point points;
	
	public PointModify(Point points)
	{
		this.points=points;
	}
	
	@Override
	public void activate(Player player) 
	{
		player.addPoint(points);
	}
	@Override
	public void rollBack(Player player)
	{
		player.subPoint(points);
	}

}
