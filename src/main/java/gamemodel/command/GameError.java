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
	ERR_NOT_TURN;//azione fuori dal turno
	
	@Override
	public String toString() {
		switch(this) {
		case RESOURCE_ERR_SERVANTS: return "servants non sufficienti";		
		case RESOURCE_ERR_CARD: return "carta troppo costosa";
		case FM_ERR_PA: return "punti azione insufficenti";
		case FM_ERR_USE: return "familiare già impiegato";
		case TWR_ERR_OCCUPY: return "torre occupata";
		case TWR_ERR_FM: return "torre occupata già da un tuo familiare";
		case SA_ERR_FM: return "spazio già occupato da un tuo familiare";
		case SA_ERR: return "spazio azione occupato";
		case SA_MAX_FM: return "spazio azione palazzo ha già 4 giocatori";
		case ERR_NOT_TURN: return "azione fuori dal turno";
		default: throw new IllegalArgumentException();
		}
	}
}
