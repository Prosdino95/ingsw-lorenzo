package gamemodel.jsonparsing;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

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
