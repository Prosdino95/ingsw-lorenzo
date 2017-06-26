package gamemodeltest.effects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gamemodel.Color;
import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Resource;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.SetOneColoredFM;

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
