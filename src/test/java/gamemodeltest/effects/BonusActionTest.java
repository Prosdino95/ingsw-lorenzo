package gamemodeltest.effects;



import org.junit.Before;
import org.junit.Test;

import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Resource;
import gamemodel.card.CardType;
import gamemodel.command.GameException;
import gamemodel.effects.BonusAction;

/*
 * Sono tutti test finti! Dovrei mettermi dietro a farli bene,
 * per ora mi basta sapere che si arriva alla fine di activate
 * senza exception che non siano GameException.  
 */

public class BonusActionTest {
	Model m;
	Player p;
	
	@Before
	public void setUp() throws Exception {
		m = new Model(4);
		m.setupRound();
		p = m.getCurrentPlayer();
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

	

}
