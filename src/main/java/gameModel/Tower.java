package gameModel;

public class Tower {
	private boolean towerFree=true;

	public boolean isFree(){
		return towerFree;
	}
	
	public void occupyTower(){
		towerFree=false;
	}		
}
