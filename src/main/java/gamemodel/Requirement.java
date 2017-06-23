package gamemodel;

import java.io.Serializable;

public class Requirement implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Resource resource;
	private CardRequirement cardReq;
	private Point point;

	public Requirement(Resource resource) {
		this.resource = resource;
	}
	
	public Requirement(Point point){
		this.point=point;
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

	@Override
	public String toString() {
		return "Requirement [resource=" + resource + ", cardReq=" + cardReq + ", point=" + point + "]";
	}
	
}
