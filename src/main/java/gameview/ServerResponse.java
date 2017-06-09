package gameview;

import java.io.Serializable;

import gamemodel.Question;
import gamemodel.command.GameError;
import server.GameQuestion;

public class ServerResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Question question;
	private GameError error;
	private String type;
	private ModelShell ms;
	private String message;
	
	public ModelShell getModel() {
		return ms;
	}

	public void setModel(ModelShell model) {
		type = "NM";
		this.ms = model;
	}

	public ServerResponse() {
		type = "O";
	}
	
	@Override
	public String toString() {
		return "ServerResponse [question=" + question + ", error=" + error + ", type=" + type + "]";
	}

	public ServerResponse(Question question) {
		type = "Q";
		this.question = question;
	}

	public ServerResponse(String question) {
		type = "MESS";
		this.message = question;
	}

	public ServerResponse(GameError error) {
		type="E";
		this.error=error;
	}

	public ServerResponse(ModelShell modelShell) {
		type = "NM";
		this.ms = modelShell;
	}

	public boolean isItOk() {
		return type.equals("O");
	}

	public boolean isThereAQuestion() {
		return type.equals("Q");
	}

	public Question getQuestion() {
		return question;
	}

	public GameError getError() {
		return error;
	}

	public void setError(GameError err) {
		type = "E";
		error = err;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String mess) {
		type = "MESS";
		message = mess;
	}

	public boolean isThereAMessage() {
		return type.equals("MESS");
	}

	
	public boolean isThereAnError() {
		return type.equals("E");
	}

	public boolean isThereANewModel() {
		return type.equals("NM");
	}

}
