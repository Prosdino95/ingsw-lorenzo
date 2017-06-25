package gamemodeltest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gamemodel.GameQuestion;
import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Question;
import gamemodel.Resource;
import gamemodel.card.Card;
import gamemodel.card.RealCard;
import gamemodel.command.GameException;

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
		c=new RealCard(0, null, 0, r, r, point, point, new ArrayList<>(), null);
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
