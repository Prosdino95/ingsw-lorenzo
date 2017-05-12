package gameModel;

public class TestEffects implements Effect {

	@Override
	public void activate(RealPlayer g) {
		System.out.println("effetto attivato su giocatore "+ g.getTeam());

	}

}
