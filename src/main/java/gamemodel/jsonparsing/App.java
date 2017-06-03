package gamemodel.jsonparsing;

import java.util.*;

import gamemodel.actionSpace.*;
import gamemodel.card.*;

/*
  	-->Improving the Canals venture card con effetto immendiato non implementato
	-->repire the cathedral stesso come sopra
	-->improving roads stesso come sopra
*/

public class App{
	
    public static void main( String[] args )
    {	
    	//testAS();
    	//testBCard();
    	//testTCard();
    	testCCard();
    	//testVCard();
    	//test();
    } 
    
    private static void test() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/test.json",new CardParsing()::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
		
	}

	private static void testCCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/CharacterCards.json",new CardParsing()::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    private static void testVCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/VentureCards.json",new CardParsing()::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    private static void testTCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/TerritoryCards.json",new CardParsing()::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    
    private static void testBCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/BuildingCards.json",new CardParsing()::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}


	private static void testAS(){
    	List<ActionSpace> AS= new CustomizationFileReader<ActionSpace>("Config/ActionSpace.json",new ASParsing()::parsing).parse();
    	List<TowerActionSpace> AST=new CustomizationFileReader<TowerActionSpace>("Config/TowerActionSpace.json",new TowerASParsing()::parsing).parse();
    	for(ActionSpace a:AS)
    		System.out.println(a);
    	for(TowerActionSpace a:AST)
    		System.out.println(a);
    	
    }
}
