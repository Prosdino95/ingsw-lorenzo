package gamemodeltest.command;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gamemodel.Action;
import gamemodel.Board;
import gamemodel.Model;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.Tower;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.CardType;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.ResourceModify;
import gamemodel.player.Color;
import gamemodel.player.Player;
import gamemodel.player.Resource;
import gamemodel.player.Team;


/**
* The PlaceFamilyMemberCommandTowerTest class represents the move checker for  tower action
* space and tests the rule game set related to it
*
*/


public class PlaceFamilyMemberCommandTowerTest {
	
	Tower t,t1;
	Board b;
	Player p1,p2,p3;
	TowerActionSpace a0,a1,a2;
	IstantEffect e;
	GameError s;
	int id0,id1,id2;
	HarvesterAndBuildings c0,c1;
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
		c0=new HarvesterAndBuildings(0,null,0,new Resource(3,3,3,0), new Resource(3,3,3,0),null, null,effects,effects, CardType.BUILDING,0);
		c1=new HarvesterAndBuildings(1,null,0,new Resource(0,0,0,0), new Resource(0,0,0,0),null, null,effects,effects, CardType.TERRITORY,0);
		p1=new Player(new Resource(1,1,1,5), b, Team.RED,model);
		p2=new Player(new Resource(2,5,5,5), b, Team.BLUE,model);
		a0=new TowerActionSpace(0,5, e, t, ActionSpaceType.TOWER);
		a1=new TowerActionSpace(1,0, e, t, ActionSpaceType.TOWER);
		t1=new Tower();
		a2=new TowerActionSpace(2,5, e, t1, ActionSpaceType.TOWER);
		p1.prepareForNewRound();
		p2.prepareForNewRound();
		a0.attachDevelopmentCard(c0);
		a1.attachDevelopmentCard(c1);
		a2.attachDevelopmentCard(c1);
		model.setCurretPlayer(p1);

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
			p1.setAlradyPlaceFM(false);
			p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.WHITE),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_ERR,s);
	}
	
	@Test
	public void testDoublePlaceInTowerSamePlayer(){
		try{p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.BLACK),5));
			p1.setAlradyPlaceFM(false);
			p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.TWR_ERR_FM,s);
	}
	
	@Test
	public void testDoublePlaceInTowerDifferentPlayer(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),5));
			model.setCurretPlayer(p2);
			p2.placeFamilyMember(new Action(p2,a1,p2.getFamilyMember(Color.BLACK),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.NOT_ENOUGH_MONEY,s);
	}
	
	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),5));
			p1.setAlradyPlaceFM(false);
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
		model.setCurretPlayer(p2);
		try{p2.placeFamilyMember(new Action(p2,a0,p2.getFamilyMember(Color.BLACK),5));}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(1,4,4,0),p2.getResource());				
	}
	
	@Test
	public void TestCard3(){
		p3=new Player(new Resource(0,0,0,5), b, Team.GREEN,model);
		b.setDice(7, 0, 0);
		p3.prepareForNewRound();
		model.setCurretPlayer(p3);
		try{p3.placeFamilyMember(new Action(p3,a0,p3.getFamilyMember(Color.BLACK),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.RESOURCE_ERR_CARD,s);
		assertEquals(new Resource(0,0,0,5),p3.getResource());
		
	}
	
}
