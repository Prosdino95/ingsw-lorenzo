package gamemodel;

import gamemodel.card.CardType;

public class CardRequirement {
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
		return (p.contCard(CardType.TERRITORIES) >= tc &&
				p.contCard(CardType.CHARACTERS) >= cc &&
				p.contCard(CardType.BUILDINGS) >= bc &&
				p.contCard(CardType.VENTURES) >= vc);
				
	}

}
