package gamemodeltest.command;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gamemodel.Board;
import gamemodel.CardType;
import gamemodel.Color;
import gamemodel.ModForza;
import gamemodel.PEffect;
import gamemodel.Player;
import gamemodel.RealBoard;
import gamemodel.RealPlayer;
import gamemodel.Resource;
import gamemodel.Team;
import gamemodel.Tower;
import gamemodel.ActionSpace.ActionSpace;
import gamemodel.ActionSpace.ActionSpaceType;
import gamemodel.ActionSpace.MemoryActionSpace;
import gamemodel.ActionSpace.RealActionSpace;
import gamemodel.ActionSpace.RealTowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.RealCard;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.Effect;

public class CommandPeffectTest {
	static RealPlayer p;
	static GameError s;
	static PEffect e,e1;

	@BeforeClass
	public static void setUpBeforeClass(){
		e=new ModForza(0, ActionSpaceType.TOWER, CardType.BUILDINGS);
		e1=new ModForza(3, ActionSpaceType.HARVEST, null);
		List<Effect> le=new ArrayList<>();
		le.add(e);
		le.add(e1);
		Card c=new RealCard(null, 0, new Resource(0,0,0,0), null, null, null, new ArrayList<Effect>(), le,CardType.BUILDINGS, null);
		Card c2=new RealCard(null, 0, new Resource(0,0,0,0), null, null, null, new ArrayList<Effect>(), le,CardType.BUILDINGS, null);
		Tower t=new Tower(CardType.BUILDINGS);
		Board b=new RealBoard();
		p=new RealPlayer(new Resource(1,1,1,5), b, Team.RED);
		p.giveCard(c);
		p.giveCard(c2);
		RealTowerActionSpace a=new RealTowerActionSpace(3, e, t, ActionSpaceType.TOWER);
		MemoryActionSpace a1=new MemoryActionSpace(3, e, ActionSpaceType.HARVEST);
		b.addActionSpace(a);
		b.addActionSpace(a1);
		p.setFamilyMember(Color.BLACK, 5);
		p.setFamilyMember(Color.WHITE, 1);
		a.attachDevelopmentCard(c);
	}
	
	@Test
	public void test() {
		try{p.placeFamilyMember(0, Color.BLACK, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);		
	}
	
	@Test
	public void test2() {
		try{p.placeFamilyMember(1, Color.WHITE, 0);}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);		
	}

}
