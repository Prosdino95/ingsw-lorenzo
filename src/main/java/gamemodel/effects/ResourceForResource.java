package gamemodel.effects;

import java.lang.reflect.Array;

import gamemodel.CardType;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;

public class ResourceForResource implements Effect 
{
	private CardType cardType;
	private Resource resourcesIn;
	private Point pointsIn;
	private Resource resourcesOut;
	private Point pointsOut;
	private int forEach;
	
	public static Effect constructor(String typeIn,Resource resourcesIn,Point pointsIn,Resource resourcesOut,Point pointsOut,int forEach)
	{
		Effect effect=null;
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

	public ResourceForResource(CardType cardType,Point pointsOut,int forEach)
	{
		this.cardType=cardType;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.pointsIn=null;
		this.resourcesIn=null;
		this.resourcesOut=null;
	}
	public ResourceForResource(CardType cardType,Resource resourcesOut,int forEach)
	{
		this.cardType=cardType;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.pointsIn=null;
		this.resourcesIn=null;
		this.pointsOut=null;
	}
	public ResourceForResource(Point pointsIn,Point pointsOut,int forEach)
	{
		this.pointsIn=pointsIn;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.cardType=null;
		this.resourcesOut=null;
		this.resourcesIn=null;
	}
	public ResourceForResource(Point pointsIn,Resource resourcesOut,int forEach)
	{
		this.pointsIn=pointsIn;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.cardType=null;
		this.pointsOut=null;
		this.resourcesIn=null;
	}
	public ResourceForResource(Resource resourcesIn,Point pointsOut,int forEach)
	{
		this.resourcesIn=resourcesIn;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.cardType=null;
		this.resourcesOut=null;
		this.pointsIn=null;
	}
	public ResourceForResource(Resource resourcesIn,Resource resourcesOut,int forEach)
	{
		this.resourcesIn=resourcesIn;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.pointsOut=null;
		this.cardType=null;
		this.pointsIn=null;
	}
	
	@Override
	public void activate(Player player)
	{
		/*int cardNumber;
		if(this.typeIn.equals(CardType.TERRITORIES))
		{
			if(this.resources==null)
			{
				for(cardNumber=0;cardNumber<player.contCard(CardType.TERRITORIES);cardNumber++)											
					player.addPoint(this.points);					
			}
			if(this.points==null)
			{
				for(cardNumber=0;cardNumber<player.contCard(CardType.TERRITORIES);cardNumber++)
					player.addResources(this.resources);
			}
		}
		if(this.typeIn.equals(CardType.BUILDINGS))
		{
			if(this.resources==null)
			{
				for(cardNumber=0;cardNumber<player.contCard(CardType.BUILDINGS);cardNumber++)
					player.addPoint(this.points);
			}
			if(this.points==null)
			{
				for(cardNumber=0;cardNumber<player.contCard(CardType.BUILDINGS);cardNumber++)
					player.addResources(this.resources);
			}
		}
		if(this.typeIn.equals(CardType.CHARACTERS))
		{
			if(this.resources==null)
			{
				for(cardNumber=0;cardNumber<player.contCard(CardType.CHARACTERS);cardNumber++)
					player.addPoint(this.points);
			}
			if(this.points==null)
			{
				for(cardNumber=0;cardNumber<player.contCard(CardType.CHARACTERS);cardNumber++)
					player.addResources(this.resources);
			}
		}
		if(this.typeIn.equals(CardType.VENTURES))
		{
			if(this.resources==null)
			{
				for(cardNumber=0;cardNumber<player.contCard(CardType.VENTURES);cardNumber++)
					player.addPoint(this.points);
			}
			if(this.points==null)
			{
				for(cardNumber=0;cardNumber<player.contCard(CardType.VENTURES);cardNumber++)
					player.addResources(this.resources);
			}
		}
	*/return ;}
}

