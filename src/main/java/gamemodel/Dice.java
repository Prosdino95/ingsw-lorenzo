package gamemodel;

import java.util.*;

public class Dice 
{
	private Map<Color,Integer>dice= new HashMap<>(3);
	
	public Dice(){
		dice.put(Color.BLACK, 0);
		dice.put(Color.WHITE, 0);
		dice.put(Color.ORANGE, 0);
	}
	

	public int getValue(Color c) {
		return dice.get(c);
	}
	
	public void setFMActionPoints(Map<Color,FamilyMember> familyMembers){
		for (Map.Entry<Color, FamilyMember> entry : familyMembers.entrySet())
		{			
			entry.getValue().setActionpoint(dice.get(entry.getKey()));
			
		}
	}
	

	public void rollDice()
	{
		for (Map.Entry<Color, Integer> entry : dice.entrySet())
		{
			Random random=new Random();
			
			entry.setValue(random.nextInt(6)+1);
		}
	}
	
	@Override
	public String toString() {
		return "Dice [dice=" + dice + "]";
	}
}
