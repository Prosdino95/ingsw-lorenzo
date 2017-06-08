package gamemodel;

import java.util.List;

public class TurnOrder 
{
	private int index=-1;
	List<Player> order;
	
	public TurnOrder(List<Player> order)
	{
		this.order=order;
	}
	
	public Player getNextPlayer()
	{
		index++;
		return this.order.get(index);
	}
	
	
	
	
	public void setupRound()
	{
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*public void start() {
		for (roundNumber = 1; roundNumber < 7; roundNumber++) {
			System.out.println("Round number " + roundNumber);
			setupRound();
			System.out.println(board);
			
			for (int familiare = 0; familiare < 4; familiare++) 
			{
				for (Player p : turnOrder) 
					p.playRound();
				
				if (roundNumber % 2 == 0) 
				{
					for (Player p : players) 
						p.vaticanReport(roundNumber/2,faithPointsRequirement.get(roundNumber/2),victoryPointsBoundedTofaithPointsRequirement.get(faithPointsRequirement.get(roundNumber/2)));
				}
			}
		}
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
