package gamemodel.command;

public enum GameError {
	
	RESOURCE_ERR_SERVANTS,//"servants non sufficienti"			
	RESOURCE_ERR_CARD,//"carta troppo costosa"
	RESOURCE_ERR_EFFECT,//non hai abbastanza risorse per attivare l'effetto(exchange).
	FM_ERR_PA,//"punti azione insufficenti"	
	FM_ERR_USE,//"familiare già impiegato"
	TWR_ERR_OCCUPY,//"torre occupata"
	TWR_ERR_FM,//"torre occupata già da un tuo familiare"
	SA_ERR_FM,//"spazio già occupato da un tuo familiare"
	SA_ERR,//"spazio azione occupato"
	SA_MAX_FM,//spazio azione palazzo ha già 4 giocatori
	PLAYER_DEAD,//view disconnessa 
	ERR_NOT_TURN, 
	LEADER_CARD_USED, 
	LEADER_CARD_NOT_ENOUGH_MONEY, 
	MILITARY_POINT, 
	TOO_CARD, 
	NOT_ENOUGH_MONEY, 
	VATICAN_FAIL, VATICAN_NOOO, NOT_PLAYING_ONLINE, ALREADY_PLACE_FM, LEADER_CARD_STILL_NOT_PLAYED;
	
	@Override
	public String toString() {
		switch(this) {
		case RESOURCE_ERR_SERVANTS: return "not enough servants";		
		case RESOURCE_ERR_CARD: return "card too much expensive";
		case FM_ERR_PA: return "not enough action points";
		case FM_ERR_USE: return "family member already busy";
		case TWR_ERR_FM: return "tower occupied by family member of yours";
		case SA_ERR_FM: return "action space occupied by family member of yours";
		case SA_ERR: return "action space occupied";
		case ERR_NOT_TURN: return "that's not your turn";
		case MILITARY_POINT:return"not enough military points";
		case TOO_CARD:return"reached maximum of this type card";
		case NOT_ENOUGH_MONEY:return"not enough gold to occupy tower";
		default: return this.name().toString();
		}
	}
}
