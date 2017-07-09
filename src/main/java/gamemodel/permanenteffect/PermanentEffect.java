package gamemodel.permanenteffect;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

/**
 * The permanent effects were too general to expose a common activate
 * method in its interface as InstantEffect does.
 * When the player gets a card having a permanent effect, the effect gets
 * registered inside the player.
 * Permanent effects which should get activated at the same time (at the same
 * point in the control flow of the program) carry the same tag.
 * In various places during the execution of an action, the model asks the player
 * if it has a particular permanent effect and acts accordingly if it does.
 * Essentially, what we haven't be able to do is encapsulate the logic of
 * activation in an object, instead it got smeared all over the model.
 */

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
	
	public String toString()
	{
		return tag.toString();
		
	}
}
