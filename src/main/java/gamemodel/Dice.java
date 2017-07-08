package gamemodel;

import java.io.Serializable;
import java.util.*;

import gamemodel.player.Color;
import gamemodel.player.FamilyMember;

public class Dice implements Serializable 
{

	private static final long serialVersionUID = 1L;
	private Map<Color,Integer>dice= new HashMap<>();
	
	public Dice(){
		dice.put(Color.BLACK, 0);
		dice.put(Color.WHITE, 0);
		dice.put(Color.ORANGE, 0);
	}
	
	public Integer getValue(Color c) {
		return dice.get(c);
	}

	public void setValue(Color color, Integer value) {
		dice.put(color, value);
	}
	
	public void setFMActionPoints(Map<Color,FamilyMember> familyMembers){
		for (Map.Entry<Color, FamilyMember> entry : familyMembers.entrySet())
		{	
			if(entry.getKey()!=Color.UNCOLORED)
				entry.getValue().setActionpoint(dice.get(entry.getKey()));
			
		}
	}

	public void setFMActionPoints(List<FamilyMember> familyMembers){
		for (FamilyMember fm : familyMembers) {
			Color c = fm.getColor();
			if(c !=Color.UNCOLORED)
				fm.setActionpoint(dice.get(c));
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
		return "Black: " + dice.get(Color.BLACK) + " Orange: " + dice.get(Color.ORANGE) + " White: " + dice.get(Color.WHITE);
	}
}
