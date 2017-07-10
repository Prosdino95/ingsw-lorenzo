package gamemodeltest;
import java.util.List;


import org.junit.Before;
import org.junit.Test;

import gamemodel.Board;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.Card;
import gamemodel.card.Excommunication;
import gamemodel.card.LeaderCard;
import gamemodel.jsonparsing.ASParsing;
import gamemodel.jsonparsing.CardParsing;
import gamemodel.jsonparsing.CustomizationFileReader;
import gamemodel.jsonparsing.ExcommunicationParsing;
import gamemodel.jsonparsing.FaithRequirements;
import gamemodel.jsonparsing.LeaderCardParsing;
import gamemodel.jsonparsing.TowerASParsing;

/**
* The ParsingTest class tests the parsing for all the customizable things we have,like cards,action spaces 
* and so on".
* 
*
*/





public class ParsingTest {
	static Board board;

	@Before
	public void test() {
		board = new Board();
	}
	
	@Test
	public void testPoints() {
    	List<Integer> Bcard= new CustomizationFileReader<Integer>("Config/FaithRequirements.json",new FaithRequirements()::parsing).parse();
    	for(Integer a:Bcard)
    		System.out.println(a);
		
	}

	@Test
	public void testEx() {
    	List<Excommunication> Bcard= new CustomizationFileReader<Excommunication>("Config/Excommunication.json",new ExcommunicationParsing()::parsing).parse();
    	for(Excommunication a:Bcard)
    		System.out.println(a);
		
	}

	@Test
	public void testCCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/CharacterCards.json",new CardParsing(board)::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
	@Test
    public void testVCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/VentureCards.json",new CardParsing(board)::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
	@Test
	public void testTCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/TerritoryCards.json",new CardParsing(board)::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    
	@Test
    public void testBCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/BuildingCards.json",new CardParsing(board)::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
	@Test
    public void testLCard() {
    	List<LeaderCard> Bcard= new CustomizationFileReader<LeaderCard>("Config/LeaderCards.json",new LeaderCardParsing(board)::parsing).parse();
    	for(LeaderCard a:Bcard)
    		System.out.println(a);
	}

	@Test
	public void testAS(){
    	List<ActionSpace> AS= new CustomizationFileReader<ActionSpace>("Config/ActionSpace.json",new ASParsing(board)::parsing).parse();
    	List<TowerActionSpace> AST=new CustomizationFileReader<TowerActionSpace>("Config/TowerActionSpace.json",new TowerASParsing(board)::parsing).parse();
    	for(ActionSpace a:AS)
    		System.out.println(a);
    	for(TowerActionSpace a:AST)
    		System.out.println(a);
    	
    }

}
