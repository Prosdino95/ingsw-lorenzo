package gameModel;

public class Controller {
	
	Board board;

	public Controller(Board board) {
		this.board = board;
	}
//al posto di scrivere solleverà eccezioni
	public void placeFamilyMember(int idSpaceAction, FamilyMember f, int servant) {
		ActionSpace a =board.getActionSpaces(idSpaceAction);
		if(a.isFree()==false)
			System.out.println("occupato");
		if(f.getActionpoint()+servant<a.getActionCost())
			System.out.println("forza non sufficiente");
		else {	a.occupy();
				a.ActivateEffect(f);
				if(servant>0)
					f.getPlayer().getResource().subResources(new Resource(0,0,0,servant));
					//sta roba è veramente ridicola dobbiamo trovare un modo migliore di gestire le risorse
		}
		
	}
}
