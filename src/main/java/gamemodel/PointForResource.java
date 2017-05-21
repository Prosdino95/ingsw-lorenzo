package gamemodel;


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
		if(this.typeIn.equals(CardType.TERRITORIES))
		{
			if(this.resources==null)
			{
				for(;;)												//TODO completare i cicli for,bisogna sapere il numero
					player.addPoint(this.points);					//TODO delle tipologie di carte in mano al giocatore	
			}
			if(this.points==null)
			{
				for(;;)
					player.addResources(this.resources);
			}
		}
		if(this.typeIn.equals(CardType.BUILDINGS))
		{
			if(this.resources==null)
			{
				for(;;)
					player.addPoint(this.points);
			}
			if(this.points==null)
			{
				for(;;)
					player.addResources(this.resources);
			}
		}
		if(this.typeIn.equals(CardType.CHARACTERS))
		{
			if(this.resources==null)
			{
				for(;;)
					player.addPoint(this.points);
			}
			if(this.points==null)
			{
				for(;;)
					player.addResources(this.resources);
			}
		}
		if(this.typeIn.equals(CardType.VENTURES))
		{
			if(this.resources==null)
			{
				for(;;)
					player.addPoint(this.points);
			}
			if(this.points==null)
			{
				for(;;)
					player.addResources(this.resources);
			}
		}
	}
}

