package gamemodel;

import java.io.Serializable;
import java.util.*;

public class Dice implements Serializable 
{

	private static final long serialVersionUID = 1L;
	private Map<Color,Integer>dice= new HashMap<>();
	
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
			if(entry.getKey()!=Color.UNCOLORED)
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
