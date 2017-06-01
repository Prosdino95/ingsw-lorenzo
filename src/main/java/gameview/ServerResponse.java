package gameview;

import gamemodel.command.GameError;

public class ServerResponse {
	private ServerQuestion question;
	private GameError error;
	private String type;
	
	public ServerResponse() {
		type = "O";
	}
	
	@Override
	public String toString() {
		return "ServerResponse [question=" + question + ", error=" + error + ", type=" + type + "]";
	}

	public ServerResponse(ServerQuestion question) {
		type = "Q";
		this.question = question;
	}
	
	public boolean isItOk() {
		return type.equals("O");
	}

	public boolean isThereAQuestion() {
		return type.equals("Q");
	}

	public ServerQuestion getQuestion() {
		return question;
	}

	public GameError getError() {
		return error;
	}

	public void setError(GameError err) {
		type = "E";
		error = err;
	}

	public boolean isThereAnError() {
		return type.equals("E");
	}

}
