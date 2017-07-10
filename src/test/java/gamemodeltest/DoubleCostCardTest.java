package gamemodeltest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import gamemodel.Model;
import gamemodel.card.Card;
import gamemodel.command.GameException;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;

/**
* The DoubleCostCardTest class tests if the double card cost effect works fine
* 
*
*/
public class DoubleCostCardTest {
	Card c;
	Player p;
	Model m;
	Point point;
	Resource r;

	@Before
	public void setUpBeforeClass() throws Exception {
		r=new Resource(1,1,1,1);
	    point=new Point(1,1,1);
		m=new Model(2);
		c=new Card(0, null, 0, r, r, point, point, new ArrayList<>(), null);
		p=new Player(new Resource(1,1,1,1), null,null, m);
		p.addPoint(new Point(1,1,1));
	}

	@Test
	public void testResourceAndPayCard() throws GameException {
		p.controlResourceAndPay(c);
		assertEquals(new Resource(0,0,0,0),p.getResource());
		assertEquals(new Point(1,1,1),p.getPoint());
	}
	

}
