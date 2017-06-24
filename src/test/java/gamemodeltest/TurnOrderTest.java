package gamemodeltest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gamemodel.Player;
import gamemodel.Team;
import gamemodel.TurnOrder;
import gamemodel.card.CardType;
import gamemodel.card.CharactersCard;
import gamemodel.card.RealCard;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.permanenteffect.PEffect;
import gamemodel.permanenteffect.PermanentEffect;

public class TurnOrderTest {
	List<Player> players;
	LinkedList<Player> result;
	Player p1,p2,p3;
	List<Player> place;

	@Before
	public void setUpBeforeClass() throws Exception {
		place=new ArrayList<>();
		players=new ArrayList<>();
		result=new LinkedList<>();
		p1=new Player(null, null, Team.BLUE);
		p2=new Player(null, null, Team.RED);
		p3=new Player(null, null, Team.GREEN);
	}
	
	@Test
	public void turnOrderStart() {
		for(int i=0;i<4;i++){
			result.add(p1);
			result.add(p2);
			result.add(p3);
		}	
		players.add(p1);
		players.add(p2);
		players.add(p3);
		TurnOrder to=new TurnOrder(players);
		assertTrue(arrayEquals(result,to));
	}
	
	@Test
	public void nextPlayer() {	
		players.add(p1);
		players.add(p2);
		players.add(p3);
		TurnOrder to=new TurnOrder(players);
		assertEquals(p1,to.getNextPlayer());
		assertEquals(p2,to.getNextPlayer());
		assertEquals(p3,to.getNextPlayer());
		assertEquals(p1,to.getNextPlayer());
		assertEquals(p2,to.getNextPlayer());
		assertEquals(p3,to.getNextPlayer());
		
	}

	@Test
	public void firstRound() {
		for(int i=0;i<4;i++){
			result.add(p2);
			result.add(p1);
			result.add(p3);
		}
		players.add(p1);
		players.add(p2);
		players.add(p3);
		TurnOrder to=new TurnOrder(players);
		place.add(p2);
		place.add(p2);
		place.add(p1);
		place.add(p1);
		to.setupRound(place);
		assertTrue(arrayEquals(result,to));
	}
	
	@Test
	public void secondRound() {
		for(int i=0;i<4;i++){
			result.add(p2);
			result.add(p1);
			result.add(p3);
		}
		players.add(p1);
		players.add(p2);
		players.add(p3);
		TurnOrder to=new TurnOrder(players);
		place.add(p2);
		place.add(p2);
		place.add(p1);
		place.add(p1);
		to.setupRound(place);
		place.clear();
		to.setupRound(place);
		assertTrue(arrayEquals(result,to));
	}
	
	@Test
	public void thirdRound() {
		for(int i=0;i<4;i++){
			result.add(p3);
			result.add(p1);
			result.add(p2);
		}
		players.add(p1);
		players.add(p2);
		players.add(p3);
		TurnOrder to=new TurnOrder(players);
		place.add(p3);
		to.setupRound(place);
		assertTrue(arrayEquals(result,to));
	}
	
	@Test
	public void sledeEffect() throws GameException {
		RealCard c=new CharactersCard(0, null, 0, null, null, null, null, new ArrayList<>(), new PermanentEffect(PEffect.NO_FIRST_ACTION), CardType.CHARACTER);
		for(int i=0;i<4;i++){
			result.add(p1);
			result.add(p2);
			result.add(p3);
		}
		result.removeFirstOccurrence(p1);
		result.addLast(p1);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		TurnOrder to=new TurnOrder(players);
		p1.giveCard(c);
		to.setupRound(place);		
		assertTrue(arrayEquals(result,to));
	}

	private static boolean arrayEquals(List<Player> result, TurnOrder to) {
		int index=0;
		for(Player p:to.getListActionOrder()){
			if(!p.equals(result.get(index)))
				return false;
			index++;
		}
		return true;
	}

}
