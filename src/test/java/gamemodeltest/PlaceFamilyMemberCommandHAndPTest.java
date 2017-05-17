package gamemodeltest;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import gamemodel.*;

public class PlaceFamilyMemberCommandHAndPTest {
	
	Board b;
	Player p1,p2;
	HarvestProductionActionSpace a0,a1;
	Effect e;
	String s;
	int id0,id1;
	
	@Before
	public void setUp(){
		e=new TestEffects();
		b=new RealBoard();
		p1=new RealPlayer(new Resource(5,5,5,5), b, Team.RED);
		p2=new RealPlayer(new Resource(5,5,5,5), b, Team.BLUE);
		a0=new HarvestProductionActionSpace(5, e, ActionSpaceType.HARVEST);
		id0=a0.getId();
		a1=new HarvestProductionActionSpace(0, e, ActionSpaceType.PRODUCTION);
		id1=a1.getId();
		p1.setFamilyMember(Color.BLACK, 1);
		p1.setFamilyMember(Color.WHITE, 7);
		p2.setFamilyMember(Color.WHITE,7);
		s=new String("");
		b.addActionSpace(a0);	
		b.addActionSpace(a1);
	}

	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 5);
			p1.placeFamilyMember(id1, Color.WHITE, 0);}
		catch(Exception e){s=e.getMessage();}		
		assertEquals("familiare già impiegato",s);		
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
	public void testDoublePlaceSamePostDifferentPlayer() throws Exception{
		try{p1.placeFamilyMember(id0, Color.BLACK, 5);
			p1.placeFamilyMember(id0, Color.WHITE, 0);}
		catch(Exception e){s=e.getMessage();}
		assertEquals("spazio già occupato da un tuo familiare",s);		
	}
	
	@Test
	public void testDoublePlaceSamePost() throws Exception{
		int pointBlack=p1.getFamilyMember(Color.WHITE).getActionpoint();
		int pointWhite=p1.getFamilyMember(Color.WHITE).getActionpoint();
		p1.placeFamilyMember(id0, Color.WHITE, 0);
		p2.placeFamilyMember(id0, Color.WHITE, 0);
		assertEquals(p1.getFamilyMember(Color.WHITE).getActionpoint(),pointBlack);
		assertEquals(p2.getFamilyMember(Color.WHITE).getActionpoint(),pointWhite-3);		
	}

}
