package gamemodel.card;

import java.io.Serializable;
import java.util.List;

import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;
import gamemodel.player.Point;
import gamemodel.player.Resource;

public class CharactersCard extends Card implements Serializable {

	private static final long serialVersionUID = 1L;
	private PermanentEffect permanentEffect;

	public CharactersCard(int id,String name, int period, Resource resourceRequirement, Resource resourcePrice, Point point,
			Point pointPrice, List<IstantEffect> istantEffects,PermanentEffect permanentEffect, CardType type) {
		super(id,name, period, resourceRequirement, resourcePrice, point, pointPrice, istantEffects, type);
		this.permanentEffect=permanentEffect;
	}


	public PermanentEffect getPermanentEffects() {
		return permanentEffect;
	}
	
	@Override
	public String toString(){
		String str = "";
		str +=this.type+"\n";
		str +="id:"+this.id+" "+this.name+" ("+period+")"+"\n";
		if(resourceRequirement!=resourcePrice)
			str +="resource requirement-> "+this.resourceRequirement+ "\n";
		if(resourcePrice!=null)
			str +="resource price-> "+this.resourcePrice+ "\n";	
		if(pointRequirement!=pointPrice)
			str +="point requirement-> "+this.pointRequirement+ "\n";
		if(pointPrice!=null)
			str +="point price-> "+this.pointPrice+ "\n";
		if(this.istantEffect!=null)
			str +="istant effect-> "+this.istantEffect+ "\n";		
		if(this.permanentEffect!=null)
			str +="Permanent effect-> "+this.permanentEffect+ "\n";		
		return str;
	}

	
}
