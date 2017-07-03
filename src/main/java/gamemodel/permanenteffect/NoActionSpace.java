package gamemodel.permanenteffect;


import gamemodel.actionSpace.ActionSpaceType;

public class NoActionSpace extends PermanentEffect 
{
	private static final long serialVersionUID = 1L;
	private ActionSpaceType atype;

	public NoActionSpace(ActionSpaceType atype)
	{
		super(PEffect.NO_ACTION_SPACE);
		this.atype=atype;
	}
	
	public ActionSpaceType getAType()
	{
		return this.atype;
	}
	
	@Override
	public String toString()
	{
		return " You can’t place your family members in the " + atype + " action space";
	}
	public String toStringGui() {
		return " No Action Space: " + atype;
	}
}
