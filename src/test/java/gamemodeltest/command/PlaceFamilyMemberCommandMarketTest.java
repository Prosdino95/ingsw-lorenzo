package gamemodeltest.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gamemodel.*;
import gamemodel.ActionSpace.ActionSpaceType;
import gamemodel.ActionSpace.RealActionSpace;
import gamemodel.command.GameError;
import gamemodel.command.GameException;


public class PlaceFamilyMemberCommandMarketTest {
	
	Board b;
	Player p1;
	RealActionSpace a0,a1;
	Effect e;
	GameError s;
	int id0,id1;

	@Before
	public void setUp(){
		e=new TestEffects();
		b=new RealBoard();
		p1=new RealPlayer(new Resource(5,5,5,5), b, Team.RED);
		a0=new RealActionSpace(5, e, ActionSpaceType.MARKET);
		id0=a0.getId();
		a1=new RealActionSpace(0, e, ActionSpaceType.MARKET);
		id1=a1.getId();
		p1.setFamilyMember(Color.BLACK, 1);
		p1.setFamilyMember(Color.WHITE, 7);
		b.addActionSpace(a0);	
		b.addActionSpace(a1);
	}
		
	@Test
	public void testZeroServantsFail() {
		try{p1.placeFamilyMember(id0, Color.BLACK, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.FM_ERR_PA,s);		
	}
		
	@Test
	public void testSomeServants(){
		try{p1.placeFamilyMember(id0, Color.BLACK, 5);}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(5,5,5,0),p1.getResource());
	}
	@Test 
	public void testTooMatchServant(){
		try{p1.placeFamilyMember(id0, Color.BLACK, 7);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.RESOURCE_ERR_SERVANTS,s);
		assertEquals(new Resource(5,5,5,5),p1.getResource());
	}
	
	@Test
	public void testDoublePlaceSamePost(){
		try{p1.placeFamilyMember(id1, Color.BLACK, 0);
			p1.placeFamilyMember(id1, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_ERR,s);
	}
	
	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 5);
			p1.placeFamilyMember(id1, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}		
		assertEquals(GameError.FM_ERR_USE,s);		
	}

}
