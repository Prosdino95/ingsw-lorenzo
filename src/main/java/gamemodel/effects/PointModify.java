package gamemodel.effects;

import java.io.Serializable;

import gamemodel.player.Player;
import gamemodel.player.Point;

public class PointModify implements IstantEffect,EffectRollBack,Serializable
{
	private static final long serialVersionUID = 1L;
	private Point points;
	
	public PointModify(Point points)
	{
		this.points=points;
	}
	
	public Point getPoints() 
	{
		return points;
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
		return "" + points;
	}
}
