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
		String srt="";
		if(gold!=0)
			srt+="gold= "+this.gold;
		if(stone!=0)
			srt+="stone= "+this.stone;
		if(wood!=0)
			srt+="wood= "+this.wood;
		if(servant!=0)
			srt+="servants= "+this.servant;
		return srt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gold;
		result = prime * result + servant;
		result = prime * result + stone;
		result = prime * result + wood;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (gold != other.gold)
			return false;
		if (servant != other.servant)
			return false;
		if (stone != other.stone)
			return false;
		if (wood != other.wood)
			return false;
		return true;
	}

}
