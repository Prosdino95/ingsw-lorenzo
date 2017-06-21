package gamemodeltest.command;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.RealTowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.RealCard;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.ResourceModify;

public class PlaceFamilyMemberCommandTowerTest {
	
	Tower t,t1;
	Board b;
	Player p1,p2,p3;
	RealTowerActionSpace a0,a1,a2;
	IstantEffect e;
	GameError s;
	int id0,id1,id2;
	Card c0,c1;
	List<IstantEffect> effects=new ArrayList<>();
	static Model model;
	
	@BeforeClass
	public static void setUpClass(){
		model=new Model(4);
	}

	@Before
	public void setUp(){
		e=new ResourceModify(new Resource(2,2,2,0));
		t=new Tower();
		b=new Board();
		b.setDice(1, 7, 7);
		c0=new RealCard(0,null,0,new Resource(3,3,3,0), new Resource(3,3,3,0),null, null,effects, CardType.BUILDING);
		c1=new RealCard(1,null,0,new Resource(0,0,0,0), new Resource(0,0,0,0),null, null,effects, CardType.TERRITORY);
		p1=new Player(new Resource(1,1,1,5), b, Team.RED,model);
		p2=new Player(new Resource(5,5,5,5), b, Team.BLUE,model);
		a0=new RealTowerActionSpace(0,5, e, t, ActionSpaceType.TOWER);
		a1=new RealTowerActionSpace(1,0, e, t, ActionSpaceType.TOWER);
		t1=new Tower();
		a2=new RealTowerActionSpace(2,5, e, t1, ActionSpaceType.TOWER);
		p1.prepareForNewRound();
		p2.prepareForNewRound();
		a0.attachDevelopmentCard(c0);
		a1.attachDevelopmentCard(c1);
		a2.attachDevelopmentCard(c1);
	}
	
	@Test
	public void testZeroServant(){
		try{p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.BLACK),0));}
		catch(GameException e){s=e.getType();}		
		assertEquals(null,s);
	}
	@Test
	public void testZeroServantsFail() {
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.BLACK),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.FM_ERR_PA,s);		
	}
	
	@Test
	public void testSomeServants(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.BLACK),5));}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(0,0,0,0),p1.getResource());
	}
	@Test 
	public void testTooMatchServant(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.BLACK),7));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.RESOURCE_ERR_SERVANTS,s);
		assertEquals(new Resource(1,1,1,5),p1.getResource());
	}
	
	@Test
	public void testDoublePlaceSamePost(){
		try{p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.BLACK),0));
			p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.WHITE),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_ERR,s);
	}
	
	@Test
	public void testDoublePlaceInTowerSamePlayer(){
		try{p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.BLACK),5));
			p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.TWR_ERR_FM,s);
	}
	
	@Test
	public void testDoublePlaceInTowerDifferentPlayer(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),5));
			p2.placeFamilyMember(new Action(p2,a1,p2.getFamilyMember(Color.BLACK),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.TWR_ERR_OCCUPY,s);
	}
	
	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),5));
			p1.placeFamilyMember(new Action(p1,a2,p1.getFamilyMember(Color.WHITE),0));}
		catch(GameException e){s=e.getType();}		
		assertEquals(GameError.FM_ERR_USE,s);		
	}
	
	@Test
	public void testCard1(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(0,0,0,5),p1.getResource());		
	}
	
	@Test
	public void testCard2(){
		try{p2.placeFamilyMember(new Action(p2,a0,p2.getFamilyMember(Color.BLACK),5));}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(4,4,4,0),p2.getResource());				
	}
	
	@Test
	public void TestCard3(){
		p3=new Player(new Resource(0,0,0,5), b, Team.GREEN,model);
		b.setDice(7, 0, 0);
		p3.prepareForNewRound();
		try{p3.placeFamilyMember(new Action(p3,a0,p3.getFamilyMember(Color.BLACK),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.RESOURCE_ERR_CARD,s);
		assertEquals(new Resource(0,0,0,5),p3.getResource());
		
	}
}
