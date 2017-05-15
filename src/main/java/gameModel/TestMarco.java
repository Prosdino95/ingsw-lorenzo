package gameModel;

public class TestMarco {

	public static void main(String[] args) {
		RealBoard board=new RealBoard();//model
		Effect e=new TestEffects();
		//ho creato questa classe effetto solo per il test poi è da cancellare ovviamente
		//il suo effetto e quello di scrivere una stringa a video indicando il giocatore su cui è attivata
		RealActionSpace a1 =new RealActionSpace(4,e);
		RealActionSpace a2 =new RealActionSpace(7,e);
		Command command= new Command(board);
		Resource resource=new Resource(5,5,5,5);
		RealPlayer redPlayer=new RealPlayer(resource,command,Team.RED);
		Tower buildingsTower=new Tower();
		RealTowerActionSpace t1=new RealTowerActionSpace(4,e,buildingsTower);
		RealTowerActionSpace t2=new RealTowerActionSpace(4,e,buildingsTower);
		
		board.addActionSpace(a1);
		board.addActionSpace(a2);
		board.addActionSpace(t1);
		board.addActionSpace(t2);
		
		redPlayer.setFamilyMember(Color.BLACK,5);
		redPlayer.setFamilyMember(Color.WHITE,3);
		System.out.println(redPlayer.getResource());
		System.out.println("familiare nero su spazio azione 0");
		redPlayer.placeFamilyMemberInSpaceActions(0, Color.BLACK, 0);
		System.out.println("familiare bianco su spazio azione 0");
		redPlayer.placeFamilyMemberInSpaceActions(0, Color.WHITE, 0);
		System.out.println("familiare bianco su spazio azione 1 senza spesa servitori");
		redPlayer.placeFamilyMemberInSpaceActions(1, Color.WHITE, 0);
		System.out.println("familiare bianco su spazio azione 1 con spesa di 5 servitori");
		redPlayer.placeFamilyMemberInSpaceActions(1, Color.WHITE, 5);
		System.out.println(redPlayer.getResource());
		
		
		System.out.println("familiare nero su spazio azione 2 di una torre");
		redPlayer.placeFamilyMemberInTower(2, Color.BLACK, 0);
		System.out.println("familiare nero su spazio azione 3 di una torre");
		redPlayer.placeFamilyMemberInTower(3, Color.BLACK, 0);
		
	}

}
