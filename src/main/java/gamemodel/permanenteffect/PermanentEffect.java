package gamemodel.permanenteffect;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

public class PermanentEffect implements Serializable
{

	private static final long serialVersionUID = 1L;
	private List<PEffect> tag= new ArrayList<>();
	

	public PermanentEffect(PEffect tag) 
	{
		this.tag.add(tag);
	}
	
	public PermanentEffect(List<PEffect> tag) 
	{
		this.tag=tag;
	}
	
	public boolean hasTag(PEffect tag){
		return this.tag.contains(tag);
	}

	public void addTag(PEffect tag) {
		this.tag.add(tag);		
	}
	

}
