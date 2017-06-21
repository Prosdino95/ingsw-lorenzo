package gamemodel.permanenteffect;


import gamemodel.actionSpace.ActionSpaceType;

public class NoActionSpace extends PermanentEffect 
{
	private static final long serialVersionUID = 1L;
	private ActionSpaceType atype;

	public NoActionSpace(PEffect s,ActionSpaceType atype)
	{
		super(s);
		this.atype=atype;
	}
	
	public ActionSpaceType getAType()
	{
		return this.atype;
	}
	
}
