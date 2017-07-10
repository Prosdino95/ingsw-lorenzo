package gamemodeltest.effects;



import org.junit.Before;
import org.junit.Test;

import gamemodel.Model;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.card.CardType;
import gamemodel.command.GameException;
import gamemodel.effects.BonusAction;
import gamemodel.player.Player;
import gamemodel.player.Resource;
import gamemodel.player.Team;

/*
 * The BonusActionTest tests if the bonus action effect works fine  
 */

public class BonusActionTest {
	Model m;
	Player p;
	
	@Before
	public void setUp() throws Exception {
		m = new Model(4);
		m.setupRound();
		p = new Player(new Resource(50,50,50,50), m.getBoard(), Team.RED, m);
		m.setCurretPlayer(p);
	}

	@Test
	public void architect() throws GameException {
		BonusAction ba = 
				new BonusAction(m.getBoard(),
					6, 
					CardType.BUILDING, 
					new Resource(0, 1, 1, 0));
			
		ba.activate(p);
	}

	@Test(expected =GameException.class)
	public void abbess() throws GameException {
		BonusAction ba = 
				new BonusAction(m.getBoard(),
					4,
					CardType.ALL, 
					new Resource(0, 0, 0, 0));
			
		ba.activate(p);
	}

	@Test
	public void bishop() throws GameException {
		BonusAction ba = 
				new BonusAction(m.getBoard(),
					4,
					ActionSpaceType.PRODUCTION);
		ba.activate(p);
	}
	

}
