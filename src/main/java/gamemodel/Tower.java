package gamemodel;

public class Tower {
	private boolean towerFree=true;
	private CardType type;

	public Tower() {
	}
	
	public Tower(CardType type) {
		this.type = type;
	}
	
	public boolean isFree(){
		return towerFree;
	}
	
	public void occupyTower(){
		towerFree=false;
	}
	
	public CardType getType() {
		return type;
	}

}
