package gamemodel.permanenteffect;

import gamemodel.Resource;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.card.CardType;

public class StrengthModifyAndDiscount extends PermanentEffect {
	
	private static final long serialVersionUID = 1L;
	private int modStrength;
	private ActionSpaceType atype;
	private CardType ctype;
	private Resource discount;

	public StrengthModifyAndDiscount(int modStrength,ActionSpaceType atype, CardType ctype,Resource discount){
		super(PEffect.MODIFY_STRENGTH);
		super.addTag(PEffect.DISCOUNT);
		this.modStrength = modStrength;
		this.atype = atype;
		this.ctype = ctype;
		this.discount=discount;
	}
	public StrengthModifyAndDiscount(int modStrength, ActionSpaceType atype, CardType ctype) {
		super(PEffect.MODIFY_STRENGTH);
		this.modStrength = modStrength;
		this.atype = atype;
		this.ctype = ctype;
	}
	public StrengthModifyAndDiscount(int modStrength, CardType ctype,Resource discount) {
		super(PEffect.MODIFY_STRENGTH);
		this.modStrength = modStrength;
		this.ctype = ctype;
		this.discount=discount;
	}
	public StrengthModifyAndDiscount(Resource discount, CardType type) {
		super(PEffect.DISCOUNT);
		this.ctype=type;
		this.discount=discount;
	}
	public StrengthModifyAndDiscount(Resource discount) {
		this(discount, CardType.ALL);
	}
	
	public Resource getDiscount() {
		return discount;
	}
	public void setDiscount(Resource discount) {
		this.discount = discount;
	}

	public int getModForza() {
		return modStrength;
	}
	public ActionSpaceType getAtype() {
		return atype;
	}
	public CardType getCtype() {
		return ctype;
	}

	@Override
	public String toString() 
	{
		String string="";
		if(modStrength!=0)
			string=" Whenever you perform an action to take " + ctype + " card increase the value of the action by " + modStrength + ".";
		if(discount!=null)
			string+=" The cost of " +  ctype + " card you take is reduced by" + discount + ".";
		if(atype!=null)
			string+=" Whenever you perform " + atype + " action increase the value of the action by " + modStrength;
		return string;
	}
	
	public String toStringGui() {
		return "StrengthModifyAndDiscount [modStrength=" + modStrength + ", astype=" + atype + ", ctype=" + ctype
				+ ", discount:" + discount + "]";
	}
	
	public static void main(String[] args)
	{
		StrengthModifyAndDiscount nas=new StrengthModifyAndDiscount(2,CardType.ALL,new Resource(2,8,9,7));
		System.out.println(nas.toStringGui());
	}

}
