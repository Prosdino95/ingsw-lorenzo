package gamemodel.player;

import java.io.Serializable;

public class Resource implements Serializable {

	private static final long serialVersionUID = 1L;
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
		if(r==null)
			return;
		this.gold+=r.gold;
		this.stone+=r.stone;
		this.wood+=r.wood;
		this.servant+=r.servant;
	}
	public void subResources(Resource r){
		if(r==null)
			return;
		this.gold-=r.gold;
		this.stone-=r.stone;
		this.wood-=r.wood;
		this.servant-=r.servant;
	}
	
	public boolean isEnought(Resource r){
		if(r == null) return true;
		
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
	
	public Resource minus(Resource r)
	{
		if (r == null) return this;
		return new Resource(this.gold-r.gold,
				this.stone-r.stone,
				this.wood-r.wood,
				this.servant-r.servant);
	}
	
	public void normalize() {
		if(this.gold<0)
			this.gold=0;
		if(this.stone<0)
			this.stone=0;
		if(this.wood<0)
			this.wood=0;
		if(this.servant<0)
			this.servant=0;		
	}
	
	@Override
	public String toString() {
		String srt="";
		if(gold!=0)
			srt+=" G="+this.gold;
		if(stone!=0)
			srt+=" St="+this.stone;
		if(wood!=0)
			srt+=" W="+this.wood;
		if(servant!=0)
			srt+=" Se="+this.servant;
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
