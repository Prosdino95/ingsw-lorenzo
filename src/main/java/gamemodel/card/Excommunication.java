package gamemodel.card;


import gamemodel.permanenteffect.PermanentEffect;

public class Excommunication
{
	private int period;
	private PermanentEffect permanentEffect;
	
	public PermanentEffect getPermanentEffect()
	{
		return this.permanentEffect;
	}
	
	public Excommunication(int period,PermanentEffect permanentEffect)
	{
		this.period=period;
		this.permanentEffect=permanentEffect;
	}

}


