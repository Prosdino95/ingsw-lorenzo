package gameModel;

public class RealCard implements Card {
		
	private Resource requirement;
	private Resource price;
	private CardType type;
		

	public RealCard(Resource requirement, Resource price, CardType type) {
		this.requirement = requirement;
		this.price = price;
		this.type = type;
	}

	@Override
	public void pay(Player p){
		p.subResources(price);		
	}

	@Override
	public CardType getType() {
		return type;
	}

	@Override
	public Resource getRequirement() {
		return requirement;
	}

}
