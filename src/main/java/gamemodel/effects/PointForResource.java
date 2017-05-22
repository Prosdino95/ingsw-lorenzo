package gamemodel.effects;

import gamemodel.CardType;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;

public class PointForResource implements Effect 
{
	private CardType typeIn;
	private Resource resources;
	private Point points;
	
	public PointForResource(CardType typeIn,Resource resources,Point points)
	{
		this.typeIn=typeIn;
		this.resources=resources;
		this.points=points;
	}
	
	@Override
	public void activate(Player player)
	{
		int cardNumber;
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
	}
}

