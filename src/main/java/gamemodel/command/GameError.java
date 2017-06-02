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
	SA_MAX_FM;//spazio azione palazzo ha già 4 giocatori
	
}
