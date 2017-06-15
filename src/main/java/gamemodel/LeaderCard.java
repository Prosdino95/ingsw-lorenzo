package gamemodel;

import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.CouncilPrivileges;
import gamemodel.effects.IstantEffect;
import gamemodel.permanenteffect.PermanentEffect;

public class LeaderCard {
	Requirement requirement;

	PermanentEffect pe;
	IstantEffect oncePerRound;

	boolean usedOPR = false;
	boolean played = false;
	
	Player owner;
	
	public LeaderCard(Requirement req2, PermanentEffect pe2) {
		requirement = req2;
		pe = pe2;
	}

	public LeaderCard(Requirement req2, IstantEffect opr2) {
		requirement = req2;
		oncePerRound = opr2;
	}
	
	public void activateOPT() throws GameException {
		if (oncePerRound == null) {
			System.out.println("Why did you call activate??? This card has only a permanent effect");
			return;
		}
		
		if (usedOPR)
			throw new GameException(GameError.LEADER_CARD_USED);

		oncePerRound.activate(owner);
	}

	public void discard() {
		new CouncilPrivileges(1).activate(owner);
	}
	
	public void play() throws GameException {
		if (requirement.isSatisfiedBy(owner)) {
			played = true;
		} else {
			throw new GameException(GameError.LEADER_CARD_NOT_ENOUGH_MONEY);
		}
	}
	
	public void prepareForNewRound() {
		usedOPR = true;
	}

	public PermanentEffect getPermanentEffect() {
		return pe;
	}
	
	@Override
	public String toString() {
		return "LeaderCard [requirement=" + requirement + ", pe=" + pe + ", oncePerRound=" + oncePerRound + ", usedOPR="
				+ usedOPR + ", played=" + played + "]";
	}

	public void setOwner(Player player) {
		owner = player;
	}
}
