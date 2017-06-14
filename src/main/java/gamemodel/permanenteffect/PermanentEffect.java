package gamemodel.permanenteffect;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

public class PermanentEffect implements Serializable
{

	private static final long serialVersionUID = 1L;
	private List<String> tag= new ArrayList<>();
	

	public PermanentEffect(String tag) 
	{
		this.tag.add(tag);
	}
	
	public PermanentEffect(List<String> tag) 
	{
		this.tag=tag;
	}
	
	public boolean hasTag(String tag){
		return this.tag.contains(tag);
	}
	

}
