package gamemodeltest.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gamemodel.*;
import gamemodel.ActionSpace.ActionSpaceType;
import gamemodel.ActionSpace.RealTowerActionSpace;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.Effect;
import gamemodel.effects.ResourceModify;

public class PlaceFamilyMemberCommandTowerTest {
	
	Tower t,t1;
	Board b;
	Player p1,p2,p3;
	RealTowerActionSpace a0,a1,a2;
	Effect e;
	GameError s;
	int id0,id1,id2;
	Card c0,c1;

	@Before
	public void setUp(){
		e=new ResourceModify(new Resource(2,2,2,0));
		t=new Tower();
		b=new RealBoard();
		c0=new RealCard(new Resource(3,3,3,0), new Resource(3,3,3,0), null, null, null, null);
		c1=new RealCard(new Resource(0,0,0,0), new Resource(0,0,0,0), null, null, null, null);
		p1=new RealPlayer(new Resource(1,1,1,5), b, Team.RED);
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
		a0.attachDevelopmentCard(c0);
		a1.attachDevelopmentCard(c1);
		a2.attachDevelopmentCard(c1);
		b.addActionSpace(a0);
		b.addActionSpace(a1);
		b.addActionSpace(a2);
		
	}
	@Test
	public void testZeroServant(){
		try{p1.placeFamilyMember(id1, Color.BLACK, 0);}
		catch(GameException e){s=e.getType();}		
		assertEquals(null,s);
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
		assertEquals(new Resource(0,0,0,0),p1.getResource());
	}
	@Test 
	public void testTooMatchServant(){
		try{p1.placeFamilyMember(id0, Color.BLACK, 7);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.RESOURCE_ERR_SERVANTS,s);
		assertEquals(new Resource(1,1,1,5),p1.getResource());
	}
	
	@Test
	public void testDoublePlaceSamePost(){
		try{p1.placeFamilyMember(id1, Color.BLACK, 0);
			p1.placeFamilyMember(id1, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_ERR,s);
	}
	
	@Test
	public void testDoublePlaceInTowerSamePlayer(){
		try{p1.placeFamilyMember(id1, Color.BLACK, 5);
			p1.placeFamilyMember(id0, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.TWR_ERR_FM,s);
	}
	
	@Test
	public void testDoublePlaceInTowerDifferentPlayer(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 5);
			p2.placeFamilyMember(id1, Color.BLACK, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.TWR_ERR_OCCUPY,s);
	}
	
	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 5);
			p1.placeFamilyMember(id2, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}		
		assertEquals(GameError.FM_ERR_USE,s);		
	}
	
	@Test
	public void testCard1(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(0,0,0,5),p1.getResource());		
	}
	
	@Test
	public void testCard2(){
		try{p2.placeFamilyMember(id0, Color.BLACK, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(4,4,4,5),p2.getResource());				
	}
	
	@Test
	public void TestCard3(){
		p3=new RealPlayer(new Resource(0,0,0,5), b, Team.GREEN);
		p3.setFamilyMember(Color.BLACK, 7);
		try{p3.placeFamilyMember(id0, Color.BLACK, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.RESOURCE_ERR_CARD,s);
		//TODO assertEquals(new Resource(0,0,0,5),p3.getResource());
		
	}
}
