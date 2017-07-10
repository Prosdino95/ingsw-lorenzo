package gamemodeltest;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gamemodel.Board;
import gamemodel.card.CardRequirement;
import gamemodel.card.CardType;
import gamemodel.card.LeaderCard;
import gamemodel.card.Requirement;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.PointModify;
import gamemodel.permanenteffect.FamilyMemberModify;
import gamemodel.permanenteffect.PermanentEffect;
import gamemodel.player.Color;
import gamemodel.player.FamilyMember;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;

/**
* The LeaderCardTest class tests the activation of leader card's effects".
* 
*
*/



public class LeaderCardTest {
	LeaderCard girolamo;
	LeaderCard ludovico;
	
	@Before
	public void setUp() throws Exception {
		Requirement req;
		List<IstantEffect> ie = new ArrayList<>();
		PermanentEffect pe;
		
		req = new Requirement(new Resource(18, 0, 0, 0));
		ie.add(new PointModify(new Point(0, 1, 0)));
		girolamo = new LeaderCard(0, null, req, ie);
		
		req = new Requirement(new CardRequirement(2, 2, 2, 2));
		pe = FamilyMemberModify.coloredMembersSet(5);
		ludovico = new LeaderCard(0, null, req, pe);
		
		
	}

	@Test
	public void savonarola() throws GameException {
		Player p = new Player(new Resource(20, 0, 0, 0), new Point(0, 0, 0));
		p.giveLeaderCard(girolamo);
		p.play(girolamo);
		p.activateLC(girolamo);
		assertEquals(p.getPoint(),new Point(0, 1, 0));
	}

	@Test
	public void ludovico() throws GameException {
		Board b = new Board();
		b.setDice(2, 4, 1);
		Player p = new Player(new Resource(0, 0, 0, 0), new Point(0, 0, 0)) {
			private static final long serialVersionUID = 1L;
			public int countCard(CardType type) {
				return 3;
			}
		};
		p.setBoard(b);
		p.giveLeaderCard(ludovico);
		p.play(ludovico);
		p.prepareForNewRound();

		for (FamilyMember fm : p.getFamilyMembers())
			if(fm.getColor()!= Color.UNCOLORED)
				assertEquals(fm.getActionpoint(), 5);
	}

}
