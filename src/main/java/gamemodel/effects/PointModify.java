package gamemodel.effects;

import java.io.Serializable;

import gamemodel.Player;
import gamemodel.Point;

public class PointModify implements Effect,EffectRollBack,Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	@Override
	public String toString() {
		return "PointModify [points=" + points + "]";
	}

//	@Override
//	public String toString() {
//		return "" + points;
//	}
	
	

}
