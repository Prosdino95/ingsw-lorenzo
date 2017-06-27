package gamemodel.permanenteffect;

import gamemodel.Resource;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.card.CardType;

public class StrengthModifyAndDiscount extends PermanentEffect {
	
	private static final long serialVersionUID = 1L;
	private int modForza;
	private ActionSpaceType atype;
	private CardType ctype;
	private Resource discount;

	public StrengthModifyAndDiscount(int modForza,ActionSpaceType atype, CardType ctype,Resource resource){
		super(PEffect.MOD_FORZA);
		super.addTag(PEffect.DISCOUNT);
		this.modForza = modForza;
		this.atype = atype;
		this.ctype = ctype;
		this.discount=resource;
	}

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
		super(PEffect.MOD_FORZA);
		this.modForza = modForza;
		this.atype = atype;
		this.ctype = ctype;
	}

	public StrengthModifyAndDiscount(Resource resource, CardType type) {
		super(PEffect.DISCOUNT);
		this.ctype=type;
		this.discount=resource;
	}

	public StrengthModifyAndDiscount(Resource resource) {
		this(resource, CardType.ALL);
	}

	public ActionSpaceType getAtype() {
		return atype;
	}

	public CardType getCtype() {
		return ctype;
	}

	@Override
	public String toString() {
		return "StrengthModifyAndDiscount [modForza=" + modForza + ", atype=" + atype + ", ctype=" + ctype
				+ ", discount=" + discount + "]";
	}
	
	

}
