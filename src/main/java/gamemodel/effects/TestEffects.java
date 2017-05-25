package gamemodel.effects;

import gamemodel.Player;

public class TestEffects implements Effect {

	@Override
	public String toString() {
		return "TestEffects ";
	}

	@Override
	public void activate(Player player) {
		System.out.println("effetto attivato su giocatore "+ player.getTeam());

	}

}
