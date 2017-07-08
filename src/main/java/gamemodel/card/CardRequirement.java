package gamemodel.card;

import java.io.Serializable;

import gamemodel.player.Player;

public class CardRequirement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int tc;
	private int cc;
	private int bc;
	private int vc;
	private boolean all;
	private int quantity;

	public CardRequirement(int tc, int cc, int bc, int vc) {
		this.tc = tc;
		this.cc = cc;
		this.bc = bc;
		this.vc = vc;
	}
	
	public CardRequirement(int quantity){
		this.all=true;
		this.quantity=quantity;
	}
	
	public boolean isSatisfiedBy(Player p) {
		if(all==true)
			return(p.countCard(CardType.TERRITORY) >= quantity ||
					p.countCard(CardType.CHARACTER) >= quantity ||
					p.countCard(CardType.BUILDING) >= quantity ||
					p.countCard(CardType.VENTURE) >= quantity);
		return (p.countCard(CardType.TERRITORY) >= tc &&
				p.countCard(CardType.CHARACTER) >= cc &&
				p.countCard(CardType.BUILDING) >= bc &&
				p.countCard(CardType.VENTURE) >= vc);
				
	}

	@Override
	public String toString() {
		String s="";
		if(all)
			s+="BC/TC/CC/VC: "+quantity;
		if(bc!=0)
			s+="BC: "+bc;
		if(cc!=0)
			s+="CC: "+cc;
		if(vc!=0)
			s+="VC: "+vc;
		if(tc!=0)
			s+="TC: "+tc;
		return s;
	}
}
