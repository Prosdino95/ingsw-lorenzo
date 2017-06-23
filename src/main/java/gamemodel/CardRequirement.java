package gamemodel;

import java.io.Serializable;

import gamemodel.card.CardType;

public class CardRequirement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int tc;
	private int cc;
	private int bc;
	private int vc;

	public CardRequirement(int tc, int cc, int bc, int vc) {
		this.tc = tc;
		this.cc = cc;
		this.bc = bc;
		this.vc = vc;
	}
	
	public boolean isSatisfiedBy(Player p) {
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
