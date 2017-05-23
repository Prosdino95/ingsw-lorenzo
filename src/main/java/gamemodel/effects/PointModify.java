package gamemodel.effects;

import gamemodel.Player;
import gamemodel.Point;

public class PointModify implements Effect 
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

}
