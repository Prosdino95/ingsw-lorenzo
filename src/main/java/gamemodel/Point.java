package gamemodel;

public class Point 
{
	private int military;
	private int faith;
	private int victory;
	
	public Point(int military,int faith,int victory)
	{
		this.military=military;
		this.faith=faith;
		this.victory=victory;
	}
	
	public void addPoint(Point p)
	{
		this.military+=p.military;
		this.faith+=p.faith;
		this.victory+=p.victory;
	}

}
