package gamemodel.command;

public class GameException extends Exception {
	private static final long serialVersionUID = 1L;
	private GameError type;
	
	public GameException(GameError type) {
		this.type = type;
	}

	public GameError getType() {
		return type;
	}

}
