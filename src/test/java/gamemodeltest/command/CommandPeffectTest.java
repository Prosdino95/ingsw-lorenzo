package gamemodeltest.command;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gamemodel.Action;
import gamemodel.Board;
import gamemodel.Color;
import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Resource;
import gamemodel.Team;
import gamemodel.Tower;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.CharactersCard;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.TestEffects;
import gamemodel.permanenteffect.NoActionSpace;
import gamemodel.permanenteffect.PermanentEffect;
import gamemodel.permanenteffect.StrengthModifyAndDiscount;

public class CommandPeffectTest {
	 Player p;
	 GameError s;
	 PermanentEffect e,e1,e2,e3;
	 IstantEffect is;
	 Action action, action1,action2;	 
	 static Model model;
		
	@BeforeClass
	public static void setUpClass(){
		model=new Model(4);
	}

	@Before
	public void setUpBeforeClass() throws GameException{
		is=new TestEffects();
		e=new StrengthModifyAndDiscount(2, ActionSpaceType.TOWER, CardType.BUILDING);
		e1=new StrengthModifyAndDiscount(3, ActionSpaceType.HARVEST, null);
		e2=new StrengthModifyAndDiscount(new Resource(4,0,0,0), CardType.BUILDING);
		e3=new NoActionSpace(ActionSpaceType.MARKET);
		Card c=new CharactersCard(1,null, 0, new Resource(0,0,0,0), null, null, null, new ArrayList<IstantEffect>(), e,CardType.CHARACTER);
		Card c1 = new CharactersCard(0,null, 0, new Resource(0,0,0,0),null, null,null, new ArrayList<IstantEffect>(),e1, CardType.CHARACTER);		
		Card c2=new CharactersCard(2,null, 0, new Resource(5,0,0,0), new Resource(5,0,0,0), null, null, new ArrayList<IstantEffect>(), e2,CardType.CHARACTER);
		Card c3=new CharactersCard(2,null, 0, new Resource(5,0,0,0), new Resource(5,0,0,0), null, null, new ArrayList<IstantEffect>(), e3,CardType.CHARACTER);
		Tower t=new Tower(CardType.BUILDING);
		Tower t2=new Tower(CardType.BUILDING);
		Board b=new Board();
		b.setDice(2, 1, 9);
		p=new Player(new Resource(1,1,1,5), b, Team.RED,model);
		p.doAction();
		p.giveCard(c);
		p.giveCard(c1);
		p.giveCard(c2);
		p.giveCard(c3);
		TowerActionSpace a=new TowerActionSpace(0,3, is, t, ActionSpaceType.TOWER);
		MemoryActionSpace a1=new MemoryActionSpace(1,3, is, ActionSpaceType.HARVEST);
		TowerActionSpace a2=new TowerActionSpace(2,3, is, t2, ActionSpaceType.TOWER);
		p.prepareForNewRound();
		a.attachDevelopmentCard(c);
		a2.attachDevelopmentCard(c2);
		
		action = new Action();
		action.setFm(p.getFamilyMember(Color.BLACK));
		action.setPlayer(p);
		action.setSpaceAction(a);
		action.setServants(0);
		
		action1 = new Action();
		action1.setFm(p.getFamilyMember(Color.WHITE));
		action1.setPlayer(p);
		action1.setSpaceAction(a1);
		action1.setServants(0);

		action2 = new Action();
		action2.setFm(p.getFamilyMember(Color.ORANGE));
		action2.setPlayer(p);
		action2.setSpaceAction(a2);
		action2.setServants(0);
		model.setCurretPlayer(p);
		

		
	}
	
	@Test
	public void test() {
		try{p.placeFamilyMember(action);}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);		
	}
	
	@Test
	public void test2() {
		try{p.placeFamilyMember(action1);}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);		
	}
	
	@Test
	public void test3()
	{
		try{p.placeFamilyMember(action2);}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(0,1,1,5),p.getResource());
	}
	
	@Test
	public void test4()
	{
		try{p.placeFamilyMember(new Action(p,new ActionSpace(3,0,is,ActionSpaceType.MARKET),p.getFamilyMember(Color.ORANGE),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_ERR,s);
	}
	
	
	
	
	
	
	
	

}
