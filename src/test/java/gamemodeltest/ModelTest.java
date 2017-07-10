package gamemodeltest;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import gamemodel.GameState;
import gamemodel.Model;
import gamemodel.player.Point;

/**
* The ModelTest class tests the whole final part of a match,in particular the final scoring and the announcement ".
* of the winner
*
*/

public class ModelTest 
{
	Model model;
	Model model2;
	
	
	
	@Before
	public void setUp() throws Exception 
	{
		model=new Model(3);
		model2=new Model(3);
		
		
	}

	@Test
	public void whoIsWinnerTest() 
	{
		
		model2.getPlayers().get(0).addPoint(new Point(0,0,7));
		model2.getPlayers().get(1).addPoint(new Point(7,0,5));
		model2.getPlayers().get(2).addPoint(new Point(7,0,10));
		model2.whoIsWinner(model2.getPlayers());
		assertEquals(new Point(7,0,17),model2.getPlayers().get(0).getPoint());
		assertEquals(new Point(7,0,12),model2.getPlayers().get(1).getPoint());
		assertEquals(new Point(0,0,9),model2.getPlayers().get(2).getPoint());
	}
	
	@Test
	public void finiteStateMachineTest() throws InterruptedException {
		Model m = new Model(4, 0);
		while (m.getState() != GameState.GAME_FINISH) {
			m.updateState();
			System.out.println(m.getState());
		}
	}

}
