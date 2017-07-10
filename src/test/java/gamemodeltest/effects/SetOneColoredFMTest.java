package gamemodeltest.effects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gamemodel.Model;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.SetOneColoredFM;
import gamemodel.player.Color;
import gamemodel.player.Player;
import gamemodel.player.Resource;

/**
* The SetOneColoredFMTest class tests the following effect:"one of your colored family member has value of 6,no
* matter dice value 
* 
*
*/

public class SetOneColoredFMTest {
	IstantEffect e;
	Player p;

	@Before
	public void setUp() throws Exception {
		p=new Player(new Resource(1,1,1,1),null,null,new Model(2));
		e=new SetOneColoredFM(6);
	}

	@Test
	public void test() throws GameException {
		e.activate(p);
		//black is the first index=0	
		assertEquals(6,p.getFamilyMember(Color.BLACK).getActionpoint());
	}

}
