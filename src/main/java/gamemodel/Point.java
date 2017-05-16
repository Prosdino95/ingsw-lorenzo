package gamemodel;

public class Point {
	private int military;
	private int faith;
	private int victory;
	
	public void addPoint(Point p){
		this.military+=p.military;
		this.faith+=p.faith;
		
	}

}
