package gamemodel;

import java.util.*;
import java.util.stream.Collectors;


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
			return;
		}	
		list.addAll(playerInGame);
		List<Player>players=
		list.stream()
		.distinct()
		.collect(Collectors.toList());		
		generateActionOrder(players);
	}
	
	private void generateActionOrder(List<Player> list) {
		this.actionOrder=new ActionOrder(list);
		this.iterator=actionOrder.iterator();
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
	//TODO il mega main poi va via, lasciamolo ora lo usiamo per il debug
	public static void main(String[] args){
		List<Player> players=new ArrayList<>();
		Player p=new Player(null, null, Team.BLUE);
		Player p1=new Player(null, null, Team.RED);
		Player p2=new Player(null, null, Team.GREEN);
		Player p3=new Player(null, null, Team.YELLOW);
		players.add(p);
		players.add(p1);	
		players.add(p2);
		players.add(p3);
		TurnOrder to=new TurnOrder(players);
		System.out.println("turni iniziali"+to);
		System.out.println("primo "+to.getNextPlayer());
		System.out.println("secondo "+to.getNextPlayer());
		System.out.println("terzo "+to.getNextPlayer());
		System.out.println("... ");
		List<Player>place=new ArrayList<>();
		place.add(p1);
		place.add(p1);
		place.add(p);
		place.add(p);
		System.out.println("palazzo del consiglio: "+place);
		to.setupRound(place);
		System.out.println("nuovi turni: "+to);
		System.out.println("primo "+to.getNextPlayer());
		System.out.println("secondo "+to.getNextPlayer());
		System.out.println("terzo "+to.getNextPlayer());
		System.out.println("... ");
		place=new ArrayList<>();
		place.add(p3);
		System.out.println("palazzo del consiglio: "+place);
		to.setupRound(place);
		System.out.println("nuovi turni: "+to);
		place=new ArrayList<>();
		System.out.println("palazzo del consiglio: "+place);
		to.setupRound(place);
		System.out.println("nuovi turni: "+to);
		System.out.println("primo "+to.getNextPlayer());
		System.out.println("secondo "+to.getNextPlayer());
		System.out.println("terzo "+to.getNextPlayer());
		System.out.println("... ");
		System.out.println("test effetto giocatore Red");
		System.out.println("turni: "+to);
		System.out.println();
		to.actionOrder.slide(p);
		System.out.println("turni: "+to);
	}
}


class ActionOrder {
	
	private LinkedList<Player> playerActionOrder;
	
	public ActionOrder(List<Player> playerInGame) {
		this.playerActionOrder=new LinkedList<>();
		for(int i=0;i<playerInGame.size();i++)
			this.playerActionOrder.addAll(playerInGame);
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
	

