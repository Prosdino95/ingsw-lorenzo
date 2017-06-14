package gamemodel.permanenteffect;

import java.util.ArrayList;

import java.util.List;

public class PermanentEffect
{
	
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
