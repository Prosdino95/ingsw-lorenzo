package gamemodel;

import java.util.*;
import java.util.stream.Collectors;

import gamemodel.permanenteffect.PEffect;


/**
 * The TurnOrder object contains the turn rotation logic. 
 */
public class TurnOrder{
	
	private ActionOrder actionOrder;
	private List<Player> playerInGame;
	private Iterator<Player> iterator;
	
	public TurnOrder(List<Player> players){	
		this.playerInGame=new ArrayList<>(players);
		this.actionOrder=new ActionOrder(players);
		this.iterator=actionOrder.iterator();
	}
	
	public void setupRound(List<Player> list){
		if(list.isEmpty()){
			this.iterator=actionOrder.iterator();
			controlSlide(playerInGame);
			return;
		}	
		list.addAll(playerInGame);
		List<Player>players=
		list.stream()
		.distinct()
		.collect(Collectors.toList());		
		generateActionOrder(players);
		controlSlide(playerInGame);
	}
	
	private void controlSlide(List<Player> players){
		for(Player p:players)
			if(!p.getPEffects(PEffect.NO_FIRST_ACTION).isEmpty())
				actionOrder.slide(p);
	}
	
	public List<Player> getPlayerInGame() 
	{
		return playerInGame;
	}

	private void generateActionOrder(List<Player> list) {
		this.actionOrder=new ActionOrder(list);
		this.iterator=actionOrder.iterator();
	}
	
	public boolean hasNext(){
		return iterator.hasNext();
	}
	
	public Player getNextPlayer(){
		if(iterator.hasNext())
			return iterator.next();
		return null;
		}
		
	@Override
	public String toString() {
		return "" + actionOrder + "";
	}
	
	public List<Player> getListActionOrder(){
		return actionOrder.getList();
	}
}	

class ActionOrder {
	
	private LinkedList<Player> playerActionOrder;
	
	public ActionOrder(List<Player> playerInGame) {
		this.playerActionOrder=new LinkedList<>();
		for(int i=0;i<4;i++)
			this.playerActionOrder.addAll(playerInGame);
	}
	
	public List<Player> getList() {
		return playerActionOrder;
	}

	public void slide(Player p){
		this.playerActionOrder.removeFirstOccurrence(p);
		this.playerActionOrder.addLast(p);
		
	}
	
	public Iterator<Player> iterator(){
		return this.playerActionOrder.iterator();
	}
	
	@Override
	public String toString() {
		return "" + playerActionOrder + "";
	}	
}
	

