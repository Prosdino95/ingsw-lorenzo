package gamemodel.jsonparsing;

import java.io.*;
import java.util.*;

public class CustomizationFileReader<T> {
	
	private Parser<T> pars;
	private String json;
	
	public CustomizationFileReader(String path,Parser<T> pars){
		this.pars=pars;
		this.json=reedFile(new File(path));
	}
	
	public List<T> parse(){
		return pars.pars(json);
	}
		
	private static String reedFile(File f){
    	StringBuilder config = new StringBuilder();
    	try {
    	    BufferedReader reader = new BufferedReader(new FileReader(f));
    	    for (String input = reader.readLine(); input != null; input = reader.readLine())
    	         config.append(input);
    	    	 reader.close();
    	} 
    	catch (FileNotFoundException fnfe) {
    	    fnfe.printStackTrace();
    	} 
    	catch(IOException ioe){
    	    ioe.printStackTrace();
    	}
    	
    	return config.toString();    	
    }
	
}
