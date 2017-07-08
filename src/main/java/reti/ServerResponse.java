package reti;

import java.io.Serializable;

import gamemodel.*;
import gamemodel.command.GameError;

public class ServerResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Question question;
	private GameError error;
//	private String removeMeType;
//	private ModelShell removeMems;
	private String message;
	private ResponseType type;
	private Model model;
	private Team playerTeam;
	
	public ServerResponse(Question gq, ResponseType type) {
		this.type=type;
		this.question=gq;		
	}

	public ServerResponse() {
		type = ResponseType.OK;
//		removeMeType = "O";
	}

	public ServerResponse(Question question) {
		type = ResponseType.QUESTION;
//		removeMeType = "Q";
		this.question = question;
	}

	public ServerResponse(Model model) {
		type = ResponseType.NEW_MODEL;
//		removeMeType = "Q";
		this.model = model;
	}

	public ServerResponse(String message) {
		type = ResponseType.MESSAGE;
//		removeMeType = "MESS";
		this.message = message;
	}

	public ServerResponse(GameError error) {
		type = ResponseType.ERROR;
//		removeMeType="E";
		this.error=error;
	}

	public ServerResponse(Player player) {
		type = ResponseType.PLAYER_ASSIGNED;
//		removeMeType="E";
		this.playerTeam = player.getTeam();
	}

//	public ServerResponse(ModelShell modelShell) {
//		removeMeType = "NM";
//		this.removeMems = modelShell;
//	}

	public ResponseType getType() {
		return type;
	}

	
//	public ModelShell getModel() {
//		return removeMems;
//	}

//	public void setModel(ModelShell model) {
//		removeMeType = "NM";
//		this.removeMems = model;
//	}

	
	@Override
	public String toString() {
		return "ServerResponse [question=" + question + ", error=" + error + ", message=" + message + ", type=" + type
				+ ", model=" + model + "]";
	}

	public boolean isItOk() {
		return type == ResponseType.OK;
//		return removeMeType.equals("O");
	}

	public boolean isThereAQuestion() {
		return type == ResponseType.QUESTION;
//		return removeMeType.equals("Q");
	}

	public Question getQuestion() {
		return question;
	}

	public GameError getError() {
		return error;
	}

	public void setError(GameError err) {
		type = ResponseType.ERROR;
//		removeMeType = "E";
		error = err;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String mess) {
		type = ResponseType.MESSAGE;
//		removeMeType = "MESS";
		message = mess;
	}

	public boolean isThereAMessage() {
		return type == ResponseType.MESSAGE;
//		return removeMeType.equals("MESS");
	}

	
	public boolean isThereAnError() {
		return type == ResponseType.ERROR;
//		return removeMeType.equals("E");
	}

	public boolean isThereANewModel() {
		return type == ResponseType.NEW_MODEL;
//		return removeMeType.equals("NM");
	}

	public boolean assignedPlayer() {
		return type == ResponseType.PLAYER_ASSIGNED;
	}

	public Model getModel() {
		return model;
	}

	public Team getPlayerTeam() {
		return playerTeam;
	}
}
