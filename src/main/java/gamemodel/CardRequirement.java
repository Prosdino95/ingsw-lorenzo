package gamemodel;

import java.io.Serializable;

import gamemodel.card.CardType;

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
		if(all=true)
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
		return "CardRequirement [tc=" + tc + ", cc=" + cc + ", bc=" + bc + ", vc=" + vc + "]";
	}
	
	

}
