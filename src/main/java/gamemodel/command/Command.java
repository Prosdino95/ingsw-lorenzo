package gamemodel.command;

/**
 * The Command object is the object which controls the legality of a place
 * family member action, and that which actually changes the model when it is.
 * The different type of action spaces have their own logic for checking
 * legality, so Command is an interface implemented by the actual Command objects.
 * 
 */
public interface Command {
	public void isLegal() throws GameException;
}