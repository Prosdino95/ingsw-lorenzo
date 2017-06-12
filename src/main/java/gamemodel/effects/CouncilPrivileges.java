package gamemodel.effects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Question;
import gamemodel.Resource;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import server.GameQuestion;

public class CouncilPrivileges implements IstantEffect,Serializable
{

	private static final long serialVersionUID = 1L;
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
	public void activate(Player player) throws GameException
	{
		List<Integer>selectionHistory=new ArrayList<>();
		
		
		
		//TODO  fare un test apena socket torna a funzionare
		
		int counter;
		for(counter=0;counter<this.numberOfCouncilPrivileges;counter++)
		{
			int selection;
			try {
				selection = Integer.parseInt(player.answerToQuestion(new Question(GameQuestion.SELECT_COUNCIL_PRIVILEGE, choice)));
				if(selectionHistory.contains(selection))
					selection = Integer.parseInt(player.answerToQuestion(new Question(GameQuestion.SELECT_A_DIFFERENT_COUNCIL_PRIVILEGE, choice)));
				effect(selection,player);
				selectionHistory.add(selection);
				
			} catch (NumberFormatException | GameException e) {
				effect(0,player);
				throw new GameException(GameError.PLAYER_DEAD);				
			}
		}
		selectionHistory.clear();
	}
	
	private void effect(int selection,Player player){
		System.out.println("selezionato:"+selection);
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

	@Override
	public String toString() {
		return "CouncilPrivileges=" + numberOfCouncilPrivileges;
	}
	
	
}
