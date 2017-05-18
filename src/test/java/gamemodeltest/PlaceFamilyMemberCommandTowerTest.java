package gamemodeltest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gamemodel.*;
import gamemodel.ActionSpace.ActionSpaceType;
import gamemodel.ActionSpace.RealTowerActionSpace;

public class PlaceFamilyMemberCommandTowerTest {
	
	Tower t;
	Tower t1;
	Board b;
	Player p1;
	Player p2;
	RealTowerActionSpace a0;
	RealTowerActionSpace a1;
	RealTowerActionSpace a2;
	Effect e;
	String s;
	int id0,id1,id2;

	@Before
	public void setUp(){
		e=new TestEffects();
		t=new Tower();
		b=new RealBoard();
		p1=new RealPlayer(new Resource(5,5,5,5), b, Team.RED);
		p2=new RealPlayer(new Resource(5,5,5,5), b, Team.BLUE);
		a0=new RealTowerActionSpace(5, e, t, ActionSpaceType.TOWER);
		id0=a0.getId();
		a1=new RealTowerActionSpace(0, e, t, ActionSpaceType.TOWER);
		id1=a1.getId();
		t1=new Tower();
		a2=new RealTowerActionSpace(5, e, t1, ActionSpaceType.TOWER);
		id2=a2.getId();
		p1.setFamilyMember(Color.BLACK, 1);
		p1.setFamilyMember(Color.WHITE, 7);
		p2.setFamilyMember(Color.BLACK, 7);
		s=new String("");
		b.addActionSpace(a0);
		b.addActionSpace(a1);
		b.addActionSpace(a2);
		
	}
	@Test
	public void testZeroServant(){
		try{p1.placeFamilyMember(id1, Color.BLACK, 0);}
		catch(Exception e){s=e.getMessage();}		
		assertEquals("",s);
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
	public void testDoublePlaceInTowerSamePlayer(){
		try{p1.placeFamilyMember(id1, Color.BLACK, 5);
			p1.placeFamilyMember(id0, Color.WHITE, 0);}
		catch(Exception e){s=e.getMessage();}
		assertEquals("torre occupata già da un tuo familiare",s);
	}
	
	@Test
	public void testDoublePlaceInTowerDifferentPlayer(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 5);
			p2.placeFamilyMember(id1, Color.BLACK, 0);}
		catch(Exception e){s=e.getMessage();}
		assertEquals("torre occupata",s);
	}
	
	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 5);
			p1.placeFamilyMember(id2, Color.WHITE, 0);}
		catch(Exception e){s=e.getMessage();}		
		assertEquals("familiare già impiegato",s);		
	}
}
