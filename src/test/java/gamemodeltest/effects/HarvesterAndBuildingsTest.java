package gamemodeltest.effects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gamemodel.GameQuestion;
import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Question;
import gamemodel.Resource;
import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.command.GameException;
import gamemodel.effects.CouncilPrivileges;
import gamemodel.effects.Exchange;
import gamemodel.effects.IstantEffect;

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
		try {
			card.activePermanentEffect(player);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(new Resource(8,6,4,4),player.getResource());
		
	}

}





