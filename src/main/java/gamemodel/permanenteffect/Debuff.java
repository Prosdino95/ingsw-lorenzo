package gamemodel.permanenteffect;

import gamemodel.Point;
import gamemodel.Resource;

public class Debuff extends PermanentEffect 
{
	private Resource resources;
	private Point points;
	
	public Debuff(Resource resources)
	{
		super("DEBUFF_RESOURCE");
		this.resources=resources;
	}
	public Debuff(Point points)
	{
		super("DEBUFF_POINT");
		this.points=points;
	}
	
	public Resource getResources() {
		return resources;
	}
	public Point getPoints() {
		return points;
	}
	
	
	
}
