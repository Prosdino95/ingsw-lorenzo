package gamemodel.effects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.GameQuestion;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Question;
import gamemodel.Resource;
import gamemodel.command.GameError;
import gamemodel.command.GameException;

public class CouncilPrivileges implements IstantEffect,Serializable
{

	private static final long serialVersionUID = 1L;
	private int numberOfCouncilPrivileges;
	private ArrayList<Object> choice;
	
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
		List<Integer>selectionHistory=new ArrayList<>();
		int counter;
		for(counter=0;counter<this.numberOfCouncilPrivileges;counter++)
		{
			System.out.println(counter);
			System.out.println(numberOfCouncilPrivileges);
			
			int selection = 0;
			try {
				try {
					selection = player.answerToQuestion(new Question(GameQuestion.SELECT_COUNCIL_PRIVILEGE, choice));
				} catch (GameException e) {
					if (e.getType() == GameError.NOT_PLAYING_ONLINE)
						selection = 0;
				}
				while(selectionHistory.contains(selection) || selection>4)
					selection = player.answerToQuestion(new Question(GameQuestion.SELECT_A_DIFFERENT_COUNCIL_PRIVILEGE, choice));
				effect(selection,player);
				selectionHistory.add(selection);
				
			} catch (GameException e) {
				effect(0,player);
				// Perche' la dovrebbe throware?
				// throw new GameException(GameError.PLAYER_DEAD);				
			}
		}
		selectionHistory.clear();
	}
	
	private void effect(int selection,Player player){
		System.out.println("Effetto: selezionato:"+selection);
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
		return " CP=" + numberOfCouncilPrivileges;
	}
}
