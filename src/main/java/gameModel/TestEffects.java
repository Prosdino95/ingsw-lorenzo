package gameModel;

public class TestEffects implements Effect {

	@Override
	public void activate(Player g) {
		System.out.println("effetto attivato su giocatore "+ g.getTeam());

	}

}
