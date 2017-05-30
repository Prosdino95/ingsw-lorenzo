package gamemodeltest.command;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import gamemodel.*;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.Effect;
import gamemodel.effects.TestEffects;

public class PlaceFamilyMemberCommandHAndPTest {
	
	Board b;
	Player p1,p2;
	MemoryActionSpace a0,a1;
	Effect e;
	GameError s;
	int id0,id1;
	
	@Before
	public void setUp(){
		e=new TestEffects();
		b=new RealBoard();
		p1=new RealPlayer(new Resource(5,5,5,5), b, Team.RED);
		p2=new RealPlayer(new Resource(5,5,5,5), b, Team.BLUE);
		a0=new MemoryActionSpace(5, e, ActionSpaceType.HARVEST);
		id0=a0.getId();
		a1=new MemoryActionSpace(0, e, ActionSpaceType.PRODUCTION);
		id1=a1.getId();
		p1.setFamilyMember(Color.BLACK, 1);
		p1.setFamilyMember(Color.WHITE, 7);
		p2.setFamilyMember(Color.WHITE,7);
		b.addActionSpace(a0);	
		b.addActionSpace(a1);
	}

	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(id0, Color.WHITE, 5);
			p1.placeFamilyMember(id1, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}		
		assertEquals(GameError.FM_ERR_USE,s);		
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
		try{p1.placeFamilyMember(id0, Color.BLACK, 5);
			p1.placeFamilyMember(id0, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_ERR_FM,s);		
	}
	
	@Test
	public void testDoublePlaceSamePostDifferentPlayer() throws GameException{
		int pointBlack=p1.getFamilyMember(Color.WHITE).getActionpoint();
		int pointWhite=p1.getFamilyMember(Color.WHITE).getActionpoint();
		p1.placeFamilyMember(id0, Color.WHITE, 0);
		p2.placeFamilyMember(id0, Color.WHITE, 0);
		assertEquals(p1.getFamilyMember(Color.WHITE).getActionpoint(),pointBlack);
		assertEquals(p2.getFamilyMember(Color.WHITE).getActionpoint(),pointWhite-3);		
	}

}
