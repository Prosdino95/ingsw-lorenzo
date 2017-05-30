package gamemodeltest.effects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gamemodel.Board;
import gamemodel.CardType;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.RealBoard;
import gamemodel.RealPlayer;
import gamemodel.Resource;
import gamemodel.Team;
import gamemodel.effects.Effect;
import gamemodel.effects.ResourceForResource;

public class ResourceForResourceTest 
{
	Board b;
	Player p1;
	

	@Before
	public void setUp() throws Exception 
	{
		b=new RealBoard();
		p1=new RealPlayer(new Resource(5,5,5,5), b, Team.RED);
		
	}

	@Test
	public void testActivate() 
	{
		Effect effect=new ResourceForResource(new Point(0,1,0),new Point(0,0,5),2);
		effect.activate(p1);
		assertEquals(new Point(0,7,15),p1.getPoint());
	
	}

}
