package gamemodel;

import gamemodel.ActionSpace.ActionSpaceType;

public class ModForza extends PEffect {
	
	private int modForza;
	private ActionSpaceType atype;
	private CardType ctype;	
	//sconto


	public int getModForza() {
		return modForza;
	}

	public ModForza(int modForza, ActionSpaceType atype, CardType ctype) {
		super("MOD_FORZA");
		this.modForza = modForza;
		this.atype = atype;
		this.ctype = ctype;
	}

	public ActionSpaceType getAtype() {
		return atype;
	}

	public CardType getCtype() {
		return ctype;
	}

	@Override
	public void activate(Player player) {
		// TODO Auto-generated method stub
		
	}

}
