package gamemodeltest.command;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gamemodel.Action;
import gamemodel.Board;
import gamemodel.CardType;
import gamemodel.Color;
import gamemodel.Player;
import gamemodel.RealBoard;
import gamemodel.RealPlayer;
import gamemodel.Resource;
import gamemodel.Team;
import gamemodel.Tower;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.actionSpace.RealActionSpace;
import gamemodel.actionSpace.RealTowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.RealCard;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.Effect;
import gamemodel.permanenteffect.PermanentEffect;
import gamemodel.permanenteffect.StrengthModifyAndDiscount;

public class CommandPeffectTest {
	 RealPlayer p;
	 GameError s;
	 PermanentEffect e,e1,e2;
	 Action action, action1,action2;

	@Before
	public void setUpBeforeClass(){
		e=new StrengthModifyAndDiscount(2, ActionSpaceType.TOWER, CardType.BUILDINGS);
		e1=new StrengthModifyAndDiscount(3, ActionSpaceType.HARVEST, null);
		e2=new StrengthModifyAndDiscount(new Resource(4,0,0,0), CardType.BUILDINGS);
		List<Effect> le=new ArrayList<>();
		List<Effect> le2=new ArrayList<>();
		le.add(e);
		le.add(e1);
		le2.add(e2);
		Card c=new RealCard(null, 0, new Resource(0,0,0,0), null, null, null, new ArrayList<Effect>(), le,CardType.BUILDINGS, null);
		Card c1=new RealCard(null, 0, new Resource(0,0,0,0), null, null, null, new ArrayList<Effect>(), le,CardType.BUILDINGS, null);
		Card c2=new RealCard(null, 0, new Resource(5,0,0,0), new Resource(5,0,0,0), null, null, new ArrayList<Effect>(), le2,CardType.BUILDINGS, null);
		Tower t=new Tower(CardType.BUILDINGS);
		Tower t2=new Tower(CardType.BUILDINGS);
		Board b=new RealBoard();
		p=new RealPlayer(new Resource(1,1,1,5), b, Team.RED);
		p.giveCard(c);
		p.giveCard(c1);
		p.giveCard(c2);
		RealTowerActionSpace a=new RealTowerActionSpace(3, e, t, ActionSpaceType.TOWER);
		MemoryActionSpace a1=new MemoryActionSpace(3, e, ActionSpaceType.HARVEST);
		RealTowerActionSpace a2=new RealTowerActionSpace(3, e, t2, ActionSpaceType.TOWER);
		p.setFamilyMember(Color.BLACK, 2);
		p.setFamilyMember(Color.WHITE, 1);
		p.setFamilyMember(Color.ORANGE, 9);
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
	
	
	
	
	
	
	
	
	

}
