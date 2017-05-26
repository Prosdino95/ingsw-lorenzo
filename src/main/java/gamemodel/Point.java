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
	
	public void subPoint(Point p)
	{
		this.military-=p.military;
		this.faith-=p.faith;
		this.victory-=p.victory;
	}

	public boolean isEnought(Point p) {
			return(this.military>=p.military &&
				this.faith>=p.faith &&
				this.victory>=p.victory);
				
		}

	@Override
	public String toString() {
		String srt="";
		if(military!=0)
			srt+=" military= "+this.military;
		if(faith!=0)
			srt+=" faith= "+this.faith;
		if(victory!=0)
			srt+=" victory= "+this.victory;
		return srt;
	}
	
}
