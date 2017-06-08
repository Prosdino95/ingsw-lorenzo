package gamemodel.card;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;


public class LeaderCard extends RealCard  implements Serializable{

	private List<IstantEffect> activateEffect;
	private List<PermanentEffect> permanentEffect;
	private static final long serialVersionUID = 1L;
	private boolean useEffect=false;
	private boolean playCard=false;
	private Map<CardType,Integer> requirementCard=new HashMap<>();
	
	public LeaderCard(int id,String name, int period, Resource resourceRequirement, Point point,
			 Map<CardType, Integer> requirementCard,List<IstantEffect> activateEffect,List<PermanentEffect> permanentEffect) {
		super(id,name, period, resourceRequirement, null, point, null, null, null);
		this.permanentEffect=permanentEffect;
		this.activateEffect=activateEffect;
		this.requirementCard=requirementCard;
	}	
	
	public void useEffect(){
		useEffect=true;
	}
	
	public void playCard(){
		playCard=true;		
	}
	
	public void precapeForNewRound(){
		playCard=false;
	}

	public boolean isUseEffect() {
		return useEffect;
	}

	public boolean isPlayCard() {
		return playCard;
	}
	
	@Override
	public String toString(){
		String str = "Leader:";
		str +="id:"+this.id+" "+this.name+"\n";
		if(resourceRequirement!=null)
			str +="resource requirement-> "+this.resourceRequirement+ "\n";	
		if(pointRequirement!=null)
			str +="point requirement-> "+this.pointRequirement+ "\n";
		if(this.activateEffect!=null)
			str +="istant effect-> "+this.activateEffect+ "\n";		
		if(this.permanentEffect!=null)
			str +="Action effect-> "+this.permanentEffect+ "\n";		
		return str;
	}
	
	
		
}
