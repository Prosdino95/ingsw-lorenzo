package gamemodel;


public enum GameQuestion {
	SELECT_COUNCIL_PRIVILEGE,
	SELECT_A_DIFFERENT_COUNCIL_PRIVILEGE, 
	VATICAN_SUPPORT, 
	SELECT_PERMANENT_EFFECT,
	LEADER,
	SELECT_ACTION_SPACE, 
	HOW_MANY_FMS, 
	SELECT_PAY_METOD,
	CHOOSE_FAMILY_MEMBER,
	SELECT_EXCHANGE;
	
	public String toString()
	{
		switch(this)
		{
		case SELECT_COUNCIL_PRIVILEGE:
			return" choose your bonus";
		case SELECT_A_DIFFERENT_COUNCIL_PRIVILEGE:
			return" choose a different one";
		case VATICAN_SUPPORT:
			return" do you want support the vatican ?";
		case SELECT_PERMANENT_EFFECT:
			return" choose permanent effect";
		case LEADER:
			return"choose leader card";
		case SELECT_ACTION_SPACE:
			return" choose action space";
		case HOW_MANY_FMS:
			return" how many servants";
		case SELECT_PAY_METOD:
			return" choose payment method";
		case CHOOSE_FAMILY_MEMBER:
			return" choose family member";
		case SELECT_EXCHANGE:
			return" choose an exchange";
		default:
			return"";
		}
	}
}
