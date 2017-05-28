package gamemodel.effects;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;

public class CouncilPrivileges implements Effect 
{
	private int numberOfCouncilPrivileges;
	private List<Object> choice;
	
	public CouncilPrivileges(int numberOfCouncilPrivileges)
	{
		this.numberOfCouncilPrivileges=numberOfCouncilPrivileges;
		this.choice = new ArrayList<Object>();
		this.choice.add(new Resource(0,1,1,0));
		this.choice.add(new Resource(0,0,0,2));
		this.choice.add(new Resource(2,0,0,0));
		this.choice.add(new Point(2,0,0));
		this.choice.add(new Point(0,1,0));
	}
	
	@Override
	public void activate(Player player)
	{
		
		// TODO modificare la questione della scelta, ora decisa dalla variabile selection
		int counter;
		for(counter=0;counter<this.numberOfCouncilPrivileges;counter++)
		{
			int selection=0;
			switch (selection)
			{
				case 0: player.addResources((Resource) this.choice.get(0));
						break;
				case 1: player.addResources((Resource) this.choice.get(1));
						break;		
				case 2: player.addResources((Resource) this.choice.get(2));
						break;
				case 3: player.addPoint((Point) this.choice.get(3));
						break;
				case 4: player.addPoint((Point) this.choice.get(4));
						break;
			}		
		}	
	}
}
