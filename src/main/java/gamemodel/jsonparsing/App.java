package gamemodel.jsonparsing;

import java.util.*;


import gamemodel.ActionSpace.*;

public class App{
	
    public static void main( String[] args )
    {
    	List<ActionSpace> AS= new CustomizationFileReader<ActionSpace>("Config/ActionSpace.json",new ASParsing()::parsing).parse();
    	List<TowerActionSpace> AST=new CustomizationFileReader<TowerActionSpace>("Config/TowerActionSpace.json",new TowerASParsing()::parsing).parse();
    	for(ActionSpace a:AS)
    		System.out.println(a);
    	for(TowerActionSpace a:AST)
    		System.out.println(a);
    }    
}
