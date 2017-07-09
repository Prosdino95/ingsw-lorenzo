package gamemodel.jsonparsing;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the one used to parse the configuration files. 
 * The constructor takes a string that represent the file's path, 
 * and the function to be used for the parsing.
 * The function accepts one String and produces a list of T.
 * The parameter T represent the type of the object 
 * produced by the function.
 * The class CustomizationFileReader has a method that applies the 
 * function and returns a List of T;
 */
public class CustomizationFileReader<T> {
	private Function<String,List<T>>parser;
	private String json;
	
	public CustomizationFileReader(String path,Function<String,List<T>> parser){
		this.parser=parser;
		this.json=reedFile(new File(path));
	}
	
	public List<T> parse(){
		return parser.apply(json);
	}
		
	public static String reedFile(File f){
    	StringBuilder config = new StringBuilder();
    	try {
    	    BufferedReader reader = new BufferedReader(new FileReader(f));
    	    for (String input = reader.readLine(); input != null; input = reader.readLine())
    	         config.append(input);
    	    	 reader.close();
    	} 
    	catch (FileNotFoundException fnfe) {
    		Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", fnfe);
    	} 
    	catch(IOException ioe){
    		Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", ioe);
    	}
    	
    	return config.toString();    	
    }
	
}
