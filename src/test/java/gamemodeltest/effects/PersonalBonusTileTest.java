package gamemodeltest.effects;

import static org.junit.Assert.*;


import org.junit.Test;

import gamemodel.Board;
import gamemodel.Model;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.command.GameException;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;
import gamemodel.player.Team;

/**
* The PersonalBonusTileTest class tests the activation of personal bonus tile
*
* 
*
*/


public class PersonalBonusTileTest 
{
	Player player;
	Board board;
	Model model;
	
	@Test
	public void test() throws GameException 
	{
		board=new Board();
		model=new Model(2);
		player=new Player(new Resource(1,2,3,4),board,Team.RED,model);
		player.getPersonalBonusTile().activate(player,ActionSpaceType.HARVEST);
		assertEquals(new Resource(1,3,4,5),player.getResource());
		player.getPersonalBonusTile().activate(player, ActionSpaceType.PRODUCTION);
		assertEquals(new Resource(3,3,4,5),player.getResource());
		assertEquals(new Point(1,0,0),player.getPoint());
	}

}
