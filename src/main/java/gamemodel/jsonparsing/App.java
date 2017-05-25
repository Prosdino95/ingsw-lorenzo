package gamemodel.jsonparsing;

import java.util.*;

import gamemodel.ActionSpace.*;

public class App{
	
    public static void main( String[] args )
    {
    	List<RealActionSpace> AS=new CustomizationFileReader("Config/SpaceAction.json").getActionSpaces();
    	for(RealActionSpace a:AS)
    		System.out.println(a);
    }    
}
