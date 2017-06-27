package gamemodeltest.command;

import static org.junit.Assert.*;

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
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.TestEffects;

public class PlaceFamilyMemberCommandHAndPLittleTest {
	
	Board b;
	Player p1,p2;
	MemoryActionSpace a0;
	IstantEffect e;
	GameError s;
	static Model model;

	@BeforeClass
	public static void setUpClass(){
		model=new Model(2);
	}
	
	@Before
	public void setUp(){
		e=new TestEffects();
		b=new Board();
		b.setDice(1, 7, 7);
		p1=new Player(new Resource(5,5,5,5), b, Team.RED,model);
		p2=new Player(new Resource(5,5,5,5), b, Team.BLUE,model);
		a0=new MemoryActionSpace(0,5, e, ActionSpaceType.HARVEST);
		p1.prepareForNewRound();
		p2.prepareForNewRound();
		b.addActionSpace(a0);	
	}

	@Test
	public void testDoublePlaceSamePostDifferentPlayer(){
		int pointWhite1=p1.getFamilyMember(Color.WHITE).getActionpoint();
		Action a1=new Action(p1,a0,p1.getFamilyMember(Color.WHITE),0);
		Action a2=new Action(p2,a0,p2.getFamilyMember(Color.WHITE),0);
		try {
			p1.placeFamilyMember(a1);
			model.setCurretPlayer(p2);
			p2.placeFamilyMember(a2);
		} catch (GameException e){s=e.getType();}		
		assertEquals(a1.getFm().getActionpoint(),pointWhite1);
		assertEquals(GameError.SA_ERR,s);		
	}

}
