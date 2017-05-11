package gameModel;

public class TestMarco {

	public static void main(String[] args) {
		Board board=new Board();//model
		Effect e=new TestEffects();
		//ho creato questa classe effetto solo per il test poi è da cancellare ovviamente
		//il suo effetto e quello di scrivere una stringa a video indicando il giocatore su cui è attivata
		ActionSpace a1 =new ActionSpace(4,e);
		ActionSpace a2 =new ActionSpace(7,e);
		Controller controller= new Controller(board);
		Resource resource=new Resource(5,5,5,5);
		Player redPlayer=new Player(resource,controller,Team.RED);
		
		board.addActionSpace(a1);
		board.addActionSpace(a2);
		
		redPlayer.setFamilyMember(Color.BLACK,5);
		redPlayer.setFamilyMember(Color.WHITE,3);
		
		redPlayer.printResources();
		System.out.println("familiare nero su spazio azione 1");
		redPlayer.placeFamilyMember(0, Color.BLACK, 0);
		System.out.println("familiare bianco su spazio azione 1");
		redPlayer.placeFamilyMember(0, Color.WHITE, 0);
		System.out.println("familiare bianco su spazio azione 2 senza spesa servitori");
		redPlayer.placeFamilyMember(1, Color.WHITE, 0);
		System.out.println("familiare bianco su spazio azione 2 con spesa di 5 servitori");
		redPlayer.placeFamilyMember(1, Color.WHITE, 5);
		redPlayer.printResources();
		
	}

}
