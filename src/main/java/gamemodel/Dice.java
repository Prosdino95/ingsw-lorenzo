package gamemodel;

import java.util.Random;

public class Dice 
{
	private Color color;
	private int value;
	
	public Dice(Color color)
	{
		this.color=color;
	}
	
	public void rollDice()
	{
		Random random=new Random();
		this.value=random.nextInt(6)+1;
	}
	
	@Override
	public String toString()
	{
		String str="The value of " + this.color +  " dice is " + this.value;
		return str;
	}
}
