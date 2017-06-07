package gamemodel.card;

import java.io.Serializable;


public class LeaderCard  implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private boolean useEffect=false;
	private boolean playCard=false;
	

	
	
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
	
	
		
}
