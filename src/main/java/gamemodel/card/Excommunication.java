package gamemodel.card;


import java.io.Serializable;

import gamemodel.permanenteffect.PermanentEffect;

public class Excommunication implements Serializable
{
	private static final long serialVersionUID = 1L;
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

	@Override
	public String toString() {
		return "Excommunication [period=" + period + ", permanentEffect=" + permanentEffect + "]";
	}
	
	

}


