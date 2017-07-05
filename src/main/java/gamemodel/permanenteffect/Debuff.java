package gamemodel.permanenteffect;

import gamemodel.Point;
import gamemodel.Resource;

public class Debuff extends PermanentEffect 
{
	private static final long serialVersionUID = 1L;
	private Resource resources;
	private Point points;
	
	public Debuff(Resource resources)
	{
		super(PEffect.DEBUFF_RESOURCE);
		this.resources=resources;
	}
	public Debuff(Point points)
	{
		super(PEffect.DEBUFF_POINT);
		this.points=points;
	}
	
	public Resource getResources() {
		return resources;
	}
	public Point getPoints() {
		return points;
	}
	@Override
	public String toString() {
		String string=" Debuff:";
		if(resources!=null)
			string+=resources;
		if(points!=null)
			string+=points;
		return string;
	}	
}
