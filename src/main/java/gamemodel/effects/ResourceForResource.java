package gamemodel.effects;

import java.io.Serializable;
import java.lang.reflect.Array;

import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.card.CardType;

public class ResourceForResource implements IstantEffect,Serializable
{
	private static final long serialVersionUID = 1L;
	private CardType cardType;
	private Resource resourcesIn;
	private Point pointsIn;
	private Resource resourcesOut;
	private Point pointsOut;
	private int forEach;
	
	public static IstantEffect constructor(String typeIn,Resource resourcesIn,Point pointsIn,Resource resourcesOut,Point pointsOut,int forEach)
	{
		IstantEffect effect=null;
		CardType[] cardTypeArray=CardType.values();
		int length=Array.getLength(cardTypeArray);
		String[] cTypeArray=new String[length];
		int c;
		for (c=0;c<length;c++) 
			cTypeArray[c]=cardTypeArray[c].toString();
		for (c=0;c<length;c++) 
		{
			if(typeIn.equals(cTypeArray[c])==true)
			{
				if(resourcesOut==null)
					effect=new ResourceForResource(cardTypeArray[c],pointsOut,forEach);
				if(pointsOut==null)
					effect=new ResourceForResource(cardTypeArray[c],resourcesOut,forEach);
			}
		}
		if(typeIn.equals("Point")==true)	
		{
			if(resourcesOut==null)
					effect=new ResourceForResource(pointsIn,pointsOut,forEach);
			if(pointsOut==null)
					effect=new ResourceForResource(pointsIn,resourcesOut,forEach);
		}
		if(typeIn.equals("Resource")==true)	
		{
			if(resourcesOut==null)
					effect=new ResourceForResource(resourcesIn,pointsOut,forEach);
			if(pointsOut==null)
					effect=new ResourceForResource(resourcesIn,resourcesOut,forEach);
		}
		return effect;
	}

	private ResourceForResource(CardType cardType,Point pointsOut,int forEach)
	{
		this.cardType=cardType;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.pointsIn=null;
		this.resourcesIn=null;
		this.resourcesOut=null;
	}
	private ResourceForResource(CardType cardType,Resource resourcesOut,int forEach)
	{
		this.cardType=cardType;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.pointsIn=null;
		this.resourcesIn=null;
		this.pointsOut=null;
	}
	private ResourceForResource(Point pointsIn,Point pointsOut,int forEach)
	{
		this.pointsIn=pointsIn;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.cardType=null;
		this.resourcesOut=null;
		this.resourcesIn=null;
	}
	private ResourceForResource(Point pointsIn,Resource resourcesOut,int forEach)
	{
		this.pointsIn=pointsIn;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.cardType=null;
		this.pointsOut=null;
		this.resourcesIn=null;
	}
	private ResourceForResource(Resource resourcesIn,Point pointsOut,int forEach)
	{
		this.resourcesIn=resourcesIn;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.cardType=null;
		this.resourcesOut=null;
		this.pointsIn=null;
	}
	private ResourceForResource(Resource resourcesIn,Resource resourcesOut,int forEach)
	{
		this.resourcesIn=resourcesIn;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.pointsOut=null;
		this.cardType=null;
		this.pointsIn=null;
	}
	
	private int count(Point points,int forEach)
	{
			if(this.pointsIn.getFaith()!=0)
				return points.getFaith()/forEach;
			if(this.pointsIn.getMilitary()!=0)
				return points.getMilitary()/forEach;
			if(this.pointsIn.getVictory()!=0)
				return points.getVictory()/forEach;
			return 0;
	}
	private int count(Resource resources,int forEach)
	{
		if(this.resourcesIn.getGold()!=0)
			return resources.getGold()/forEach;
		if(this.resourcesIn.getServant()!=0)
			return resources.getServant()/forEach;
		if(this.resourcesIn.getWood()!=0)
			return resources.getWood()/forEach;
		if(this.resourcesIn.getStone()!=0)
			return resources.getStone()/forEach;
		return 0;
	}
	
/*	@Override
	public String toString() {
		return "ResourceForResource [cardType=" + cardType + ", resourcesIn=" + resourcesIn + ", pointsIn=" + pointsIn
				+ ", resourcesOut=" + resourcesOut + ", pointsOut=" + pointsOut + ", forEach=" + forEach + "]";
	}*/

	@Override
	public void activate(Player player)
	{
		if(cardType!=null)
		{
			int c;
			if(pointsOut!=null)
			{
				for(c=0;c<player.countCard(cardType)/forEach;c++)
					player.addPoint(pointsOut);
			}
			if(resourcesOut!=null)
			{
				for(c=0;c<player.countCard(cardType)/forEach;c++)
					player.addResources(resourcesOut);
			}
		}
		if(pointsIn!=null)
		{
			int c;
			if(pointsOut!=null)
			{
				for(c=0;c<count(player.getPoint(),forEach);c++)
					player.addPoint(pointsOut);
			}
			if(resourcesOut!=null)
			{
				for(c=0;c<count(player.getPoint(),forEach);c++)
					player.addResources(resourcesOut);
			}
		}
		if(resourcesIn!=null)
		{
			int c;
			if(pointsOut!=null)
			{
				for(c=0;c<count(player.getResource(),forEach);c++)
					player.addPoint(pointsOut);
			}
			if(resourcesOut!=null)
			{
				for(c=0;c<count(player.getResource(),forEach);c++)
					player.addResources(resourcesOut);
			}
		}
	}
	
	
	public String toString() {
		String str ="\n"+ "Reasource for Resource: "+"\n";
		str+="receve --> ";
		if(this.pointsOut!=null)
			str+=pointsOut+"\n";
		if(this.resourcesOut!=null)
			str+=resourcesOut+"\n";
		str+="for "+this.forEach+" ";
		if(this.pointsIn!=null)
			str+=pointsIn;			
		if(this.resourcesIn!=null)		
			str+=resourcesIn;
		if(this.cardType!=null)	
			str+=cardType;
		return str;
		
	}
}

