package gamemodel;

public class Requirement {
	private Resource resource;
	private CardRequirement cardReq;

	public Requirement(Resource resource) {
		this.resource = resource;
	}
	
	public Requirement(CardRequirement cr) {
		this.cardReq = cr;
	}

	public boolean isSatisfiedBy(Player player) {
		boolean satisfies = true;

		if (resource != null) {
			satisfies = player.getResource().isEnought(resource);
		}
		
		if (cardReq != null) {
			satisfies = cardReq.isSatisfiedBy(player);
		}
		
		return satisfies;
	}
}
