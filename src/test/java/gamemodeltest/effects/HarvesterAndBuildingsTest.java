package gamemodeltest.effects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import gamemodel.Model;
import gamemodel.card.CardType;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.command.GameException;
import gamemodel.effects.Exchange;
import gamemodel.effects.IstantEffect;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;

public class HarvesterAndBuildingsTest {

	HarvesterAndBuildings card;
	Player player;
	
	@Before
	public void setUp() throws Exception 
	{
		player=new Player(new Resource(5,5,5,5),null,null,new Model(2));
	}

	@Test
	public void activePermanentEffectTest() 
	{
		List<IstantEffect> permanentEffects=new ArrayList<>(2);
		card=new HarvesterAndBuildings(2,"carta di prova",1,new Resource(0,0,0,0),new Resource(0,0,0,0),new Point(0,0,0),new Point(0,0,0),null,permanentEffects,CardType.BUILDING,4);
		IstantEffect ex1=new Exchange(new Point(0,0,0),new Point(0,0,0),new Resource(3,1,0,0),new Resource(0,0,1,1),null);
		IstantEffect ex2=new Exchange(new Point(0,0,0),new Point(0,0,0),new Resource(5,0,0,0),new Resource(0,0,2,0),null);
		permanentEffects.add(ex1);
		permanentEffects.add(ex2);
		System.out.println(ex1);
		try {
			card.activePermanentEffect(player);
		} catch (GameException e) {
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
		assertEquals(new Resource(8,6,4,4),player.getResource());
		
	}

}





