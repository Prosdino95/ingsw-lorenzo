package gameModel;

public class Controller {
	
	Board board;

	public Controller(Board board) {
		this.board = board;
	}
//al posto di scrivere solleverà eccezioni
	private void placeFamilyMember(ActionSpace a, FamilyMember f, int servant) {
		if(a.isFree()==false)
			System.out.println("spazio azione occupato");
		if(f.getActionpoint()+servant<a.getActionCost())
			System.out.println("forza non sufficiente");
		else {	a.occupy();
				a.ActivateEffect(f);
				if(servant>0)
					f.getPlayer().getResource().subResources(new Resource(0,0,0,servant));
					//sta roba è veramente ridicola dobbiamo trovare un modo migliore di gestire le risorse
		}
	}
		
		public void placeFamilyMemberInSpaceActions(int idSpaceAction, FamilyMember f, int servant){
			ActionSpace a =board.getActionSpaces(idSpaceAction);
			placeFamilyMember(a,f,servant);
			
		}
		
		public void placeFamilyMemberInTower(int idSpaceAction, FamilyMember f, int servant){
			TowerActionSpace a = (TowerActionSpace) board.getActionSpaces(idSpaceAction);
			if(a.getTower().isFree()){
				a.getTower().occupyTower();
				placeFamilyMember(a,f,servant);
			}
			else 
				System.out.println("torre occupata");
		}
		
		
	
}
