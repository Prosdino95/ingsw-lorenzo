package gamemodeltest.command;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import gamemodel.*;
import gamemodel.actionSpace.*;
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

	@Before
	public void setUpBeforeClass() throws GameException{
		is=new TestEffects();
		e=new StrengthModifyAndDiscount(2, ActionSpaceType.TOWER, CardType.BUILDINGS);
		e1=new StrengthModifyAndDiscount(3, ActionSpaceType.HARVEST, null);
		e2=new StrengthModifyAndDiscount(new Resource(4,0,0,0), CardType.BUILDINGS);
		e3=new NoActionSpace("NO_ACTION_SPACE",ActionSpaceType.MARKET);
		List<PermanentEffect> le=new ArrayList<>();
		List<PermanentEffect> le2=new ArrayList<>();
		le.add(e);
		le.add(e1);
		le2.add(e2);
		le2.add(e3);
		Card c1 = new CharactersCard(0,null, 0, new Resource(0,0,0,0),null, null,null, new ArrayList<IstantEffect>(),le, CardType.CHARACTERS);
		Card c=new CharactersCard(1,null, 0, new Resource(0,0,0,0), null, null, null, new ArrayList<IstantEffect>(), le,CardType.CHARACTERS);
		Card c2=new CharactersCard(2,null, 0, new Resource(5,0,0,0), new Resource(5,0,0,0), null, null, new ArrayList<IstantEffect>(), le2,CardType.CHARACTERS);
		Tower t=new Tower(CardType.BUILDINGS);
		Tower t2=new Tower(CardType.BUILDINGS);
		Board b=new Board();
		b.setDice(2, 1, 9);
		p=new Player(new Resource(1,1,1,5), b, Team.RED);
		p.giveCard(c);
		p.giveCard(c1);
		p.giveCard(c2);
		RealTowerActionSpace a=new RealTowerActionSpace(0,3, is, t, ActionSpaceType.TOWER);
		MemoryActionSpace a1=new MemoryActionSpace(1,3, is, ActionSpaceType.HARVEST);
		RealTowerActionSpace a2=new RealTowerActionSpace(2,3, is, t2, ActionSpaceType.TOWER);
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
		try{p.placeFamilyMember(new Action(p,new RealActionSpace(3,0,is,ActionSpaceType.MARKET),p.getFamilyMember(Color.ORANGE),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_ERR,s);
	}
	
	
	
	
	
	
	
	

}
