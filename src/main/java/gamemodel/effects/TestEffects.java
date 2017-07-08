package gamemodel.effects;

import java.io.Serializable;

import gamemodel.player.Player;

public class TestEffects implements IstantEffect,Serializable {


	private static final long serialVersionUID = 1L;

	@Override
	public void activate(Player player) {
		System.out.println("effetto attivato su giocatore "+ player.getTeam());

	}
	

	@Override
	public String toString() {
		return "TestEffects ";
	}
}
