package gamemodel.card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.GameQuestion;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Question;
import gamemodel.Resource;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.ResourceModify;

public class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String name;
	protected int period;
	protected Resource resourceRequirement;
	protected Resource resourcePrice;
	protected Point pointRequirement;
	protected Point pointPrice;
	protected List<IstantEffect> istantEffect = new ArrayList<>();
	protected CardType type;
	protected final int id;
	private Integer choose=-1;
	
	public Card(int id,String name,int period,Resource resourceRequirement, Resource resourcePrice, 
			Point point,Point pointPrice, List<IstantEffect> istantEffects, CardType type) {
		this.name=name;
		this.period=period;
		this.id=id;
		this.resourceRequirement = resourceRequirement;
		this.resourcePrice = resourcePrice;
		this.pointRequirement = point;
		this.pointPrice = pointPrice;
		this.type = type;
		this.istantEffect=istantEffects;
	}
	public List<IstantEffect> getIstantEffect()
	{
		return istantEffect;
	}
	public boolean isInstanceOfResourceModify(List<IstantEffect> istantEffects)
	{
		for(IstantEffect istantEffect:istantEffects)
			if(istantEffect instanceof ResourceModify)
				return true;
		return false;
	}
	public List<IstantEffect> getResourceModifyInstantEffects(List<IstantEffect> istantEffects)
	{
		List<IstantEffect> ResourceModifyInstantEffects=new ArrayList<>();
		for(IstantEffect istantEffect:istantEffects)
			if(istantEffect instanceof ResourceModify)
				ResourceModifyInstantEffects.add(istantEffect);
		return ResourceModifyInstantEffects;
	}	
	

	public boolean controlResource(Player p,Resource discount){
		if(resourceRequirement!=null && pointRequirement!=null){
			List<Object> prices=new ArrayList<>();
			prices.add(resourceRequirement);
			prices.add(pointRequirement);
			try {
				choose=p.answerToQuestion(new Question(GameQuestion.SELECT_PAY_METOD,prices));
			} catch (GameException e) {
				choose=0;
			}
			if(choose==0)
				return p.isEnoughtResource(resourceRequirement.minus(discount));
			if(choose==1)
				return p.isEnoughtPoint(pointRequirement);
		}			
		if(resourceRequirement!=null)
			return p.isEnoughtResource(resourceRequirement.minus(discount));
		if(pointRequirement!=null)
			return p.isEnoughtPoint(pointRequirement);
		return true;				
	}

	public void pay(Player p,Resource discount){
		if(resourcePrice!=null && choose==-1)
			p.subResources(resourcePrice.minus(discount));	
		if(pointPrice!=null && choose==-1)
			p.subPoint(pointPrice);
		if(resourcePrice!=null && choose==0)
			p.subResources(resourcePrice.minus(discount));
		if(pointPrice!=null && choose==1)
			p.subPoint(pointPrice);
		
	}
	
	public Resource getResourceRequirement() {
		return resourceRequirement;
	}

	public Resource getResourcePrice() {
		return resourcePrice;
	}

	public Point getPointRequirement() {
		return pointRequirement;
	}

	public Point getPointPrice() {
		return pointPrice;
	}

	public CardType getType() {
		return type;
	}
	
	public int getPeriod(){
		return this.period;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName(){
		return name;
	}



	public void activeIstantEffect(Player p) throws GameException {
		for(IstantEffect e:this.istantEffect)
			e.activate(p);		
	}
	
	@Override
	public String toString() {
		return "RealCard [name=" + name + ", resourceRequirement=" + resourceRequirement + ", resourcePrice="
				+ resourcePrice + ", pointRequirement=" + pointRequirement + ", pointPrice=" + pointPrice
				+ ", istantEffect=" + istantEffect +", type=" + type + ", id="
				+ id + ", requirementCard=" +"]";
	}


}
