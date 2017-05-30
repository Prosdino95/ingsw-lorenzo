package gamemodel;

import gamemodel.ActionSpace.ActionSpaceType;

public class StrengthModifyAndDiscount extends PEffect {
	
	private int modForza;
	private ActionSpaceType atype;
	private CardType ctype;
	private Resource discount;


	public Resource getDiscount() {
		return discount;
	}

	public void setDiscount(Resource discount) {
		this.discount = discount;
	}

	public int getModForza() {
		return modForza;
	}

	public StrengthModifyAndDiscount(int modForza, ActionSpaceType atype, CardType ctype) {
		super("MOD_FORZA");
		this.modForza = modForza;
		this.atype = atype;
		this.ctype = ctype;
	}

	public StrengthModifyAndDiscount(Resource resource, CardType type) {
		super("Discount");
		this.ctype=type;
		this.discount=resource;
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
