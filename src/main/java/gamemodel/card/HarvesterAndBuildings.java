package gamemodel.card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.GameQuestion;
import gamemodel.Question;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.Exchange;
import gamemodel.effects.IstantEffect;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;

/**
 * This object may represent a Territory or a Building card, the logic in common
 * that made this class possible was the activation of its (not-so-)permanent effect
 * when occupying respectively a Harvest or a Production action space.
 */
public class HarvesterAndBuildings extends Card implements Serializable
{

	private static final long serialVersionUID = 1L;
	private Integer actionCost;
	private List<IstantEffect> permanentEffects;
	
	public HarvesterAndBuildings(int id,String name,int period, Resource resourceRequirement, Resource resourcePrice, 
			Point point,Point pointPrice, List<IstantEffect> istantEffects,List<IstantEffect> permanentEffects, 
			CardType type,int actionCost)
	{
		super(id,name,period,resourceRequirement,resourcePrice,point,pointPrice, istantEffects,type);
		this.actionCost=actionCost;
		this.permanentEffects=permanentEffects;
	}
	
	public HarvesterAndBuildings(int id,String name,int period, Resource resourceRequirement, Resource resourcePrice, 
			Point point,Point pointPrice, List<IstantEffect> istantEffects,IstantEffect permanentEffect, 
			CardType type,int actionCost)
	{
		super(id,name,period,resourceRequirement,resourcePrice,point,pointPrice, istantEffects,type);
		this.actionCost=actionCost;
		this.permanentEffects.add(permanentEffect);
	}
	
	public Integer getActionCost(){
		return this.actionCost;
	}
	
	public void activePermanentEffect(Player p) throws GameException 
	{
		int selection = 0;
		if(getExchangeEffects(p).size()>1){
			try {
				selection = p.answerToQuestion(new Question(GameQuestion.SELECT_EXCHANGE,getExchangeEffects(p)));
				} 
			catch (GameException e) {
				if (e.getType() == GameError.NOT_PLAYING_ONLINE)
					selection = 0;
			}
			((Exchange) getExchangeEffects(p).get(selection)).activate(p);
		}
		else
			for(IstantEffect e:this.permanentEffects)
			e.activate(p);	
	}


	public List<IstantEffect> getPermanentEffects() {
		return this.permanentEffects;
	}
	
	private List<Object> getExchangeEffects (Player p)
	{
		List<Object> exchangeEffects=new ArrayList<>();
		for(IstantEffect permanentEffect:permanentEffects)
			if(permanentEffect instanceof Exchange)
				if(((Exchange) permanentEffect).canExchange(p))
					exchangeEffects.add((Exchange) permanentEffect);
		return exchangeEffects;
	}	
	
	
	@Override
	public String toString(){
		String str = "";
		str +=this.type+"\n";
		str +="id:"+this.id+" "+this.name+"\n";
		if(resourceRequirement!=resourcePrice)
			str +="resource req-> "+this.resourceRequirement+ "\n";
		if(resourcePrice!=null)
			str +="resource price-> "+this.resourcePrice+ "\n";			
		if(pointRequirement!=pointPrice)
			str +="point req-> "+this.pointRequirement+ "\n";
		if(pointPrice!=null)
			str +="point price-> "+this.pointPrice+ "\n";
		if(this.istantEffect!=null)
			str +="istant effect-> "+this.istantEffect+ "\n";		
		str +="Action Cost-> "+this.actionCost+ "\n";
		if(this.permanentEffects!=null)
			str +="Action effect-> "+this.permanentEffects+ "\n";		
		return str;
	}

}
