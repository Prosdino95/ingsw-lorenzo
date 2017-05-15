package gameModel;

public class Command {
	
	RealBoard board;

	public Command(RealBoard board) {
		this.board = board;
	}
//al posto di scrivere solleverà eccezioni
	private void placeFamilyMember(RealActionSpace a, FamilyMember f, int servant) {
		if(a.isFree()==false)
			System.out.println("spazio azione occupato");
		if(f.getActionpoint()+servant<a.getActionCost())
			System.out.println("forza non sufficiente");
		else {	a.occupy();
				a.activateEffect(f);
				if(servant>0)
					f.getPlayer().subResources(new Resource(0,0,0,servant));
		}
	}
		
		public void placeFamilyMemberInSpaceActions(int idSpaceAction, FamilyMember f, int servant){
			RealActionSpace a =(RealActionSpace) board.getActionSpaces(idSpaceAction);
			placeFamilyMember(a,f,servant);
			
		}
		
		public void placeFamilyMemberInTower(int idSpaceAction, FamilyMember f, int servant){
			RealTowerActionSpace a = (RealTowerActionSpace) board.getActionSpaces(idSpaceAction);
			if(a.getTower().isFree()){
				a.getTower().occupyTower();
				placeFamilyMember(a,f,servant);
			}
			else 
				System.out.println("torre occupata");
		}
		
		
	
}
