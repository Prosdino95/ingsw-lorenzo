package gamemodel;

public class TestEffects implements Effect {

	@Override
	public void activate(Player player) {
		System.out.println("effetto attivato su giocatore "+ player.getTeam());

	}

}
