package gamemodel.jsonparsing;

import java.io.*;

import java.util.*;

import com.eclipsesource.json.*;

import gamemodel.ActionSpace.*;


public class CustomizationFileReader {
	
	String path;
	List<RealActionSpace> AS=new ArrayList<>();
	
	CustomizationFileReader(String path){
		this.path=path;
	}
	
	
	public List<RealActionSpace> getActionSpaces() {		
    	File config=new File(path);
    	String json=reedFile(config);
    	JsonArray items = Json.parse(json).asObject().get("ActionSpace").asArray();
    	AS=new ASParsing().parsing(items);
		return AS;
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
