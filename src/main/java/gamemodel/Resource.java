package gamemodel;

public class Resource {
	private int gold;
	private int stone;
	private int wood;
	private int servant;
	
	public Resource(int gold, int stone, int wood, int servant) {
		this.gold = gold;
		this.stone = stone;
		this.wood = wood;
		this.servant = servant;
	}
	
	public void addResources(Resource r){
		this.gold+=r.gold;
		this.stone+=r.stone;
		this.wood+=r.wood;
		this.servant+=r.servant;
	}
	public void subResources(Resource r){
		this.gold-=r.gold;
		this.stone-=r.stone;
		this.wood-=r.wood;
		this.servant-=r.servant;
	}
	
	public boolean isEnought(Resource r){
		return(this.gold>=r.gold &&
			this.stone>=r.stone &&
			this.wood>=r.wood &&
			this.servant>=r.servant);
			
	}
	public int getGold() {
		return gold;
	}
	public int getStone() {
		return stone;
	}
	public int getWood() {
		return wood;
	}
	public int getServant() {
		return servant;
	}
	@Override
	public String toString() {
		return "Resource \n"+
				"Gold=" + gold + "\n"+
				"Stone=" + stone +"\n"+
				"Wood=" + wood +"\n"+
				"Servant=" + servant;
	}		
}
