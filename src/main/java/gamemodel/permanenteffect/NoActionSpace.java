package gamemodel.permanenteffect;


import gamemodel.actionSpace.ActionSpaceType;

public class NoActionSpace extends PermanentEffect 
{
	private ActionSpaceType atype;

	public NoActionSpace(String s,ActionSpaceType atype)
	{
		super(s);
		this.atype=atype;
	}
	
	public ActionSpaceType getAType()
	{
		return this.atype;
	}
	
}
