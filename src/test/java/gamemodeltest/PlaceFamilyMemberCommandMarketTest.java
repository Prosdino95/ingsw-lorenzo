package gamemodeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gamemodel.*;


public class PlaceFamilyMemberCommandMarketTest {
	
	Board b;
	Player p1;
	RealActionSpace a0,a1;
	Effect e;
	String s;
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
		s=new String("");
		b.addActionSpace(a0);	
		b.addActionSpace(a1);
	}
		
	@Test
	public void testZeroServantsFail() {
		try{p1.placeFamilyMember(id0, Color.BLACK, 0);}
		catch(Exception e){s=e.getMessage();}
		assertEquals("punti azione insufficenti",s);		
	}
		
	@Test
	public void testSomeServants(){
		try{p1.placeFamilyMember(id0, Color.BLACK, 5);}
		catch(Exception e){s=e.getMessage();}
		assertEquals("",s);
		assertEquals(new Resource(5,5,5,0),p1.getResource());
	}
	@Test 
	public void testTooMatchServant(){
		try{p1.placeFamilyMember(id0, Color.BLACK, 7);}
		catch(Exception e){s=e.getMessage();}
		assertEquals("servants non sufficienti",s);
		assertEquals(new Resource(5,5,5,5),p1.getResource());
	}
	
	@Test
	public void testDoublePlaceSamePost(){
		try{p1.placeFamilyMember(id1, Color.BLACK, 0);
			p1.placeFamilyMember(id1, Color.WHITE, 0);}
		catch(Exception e){s=e.getMessage();}
		assertEquals("spazio azione occupato",s);
	}
	
	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 5);
			p1.placeFamilyMember(id1, Color.WHITE, 0);}
		catch(Exception e){s=e.getMessage();}		
		assertEquals("familiare già impiegato",s);		
	}

}
