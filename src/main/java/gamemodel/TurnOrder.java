package gamemodel;

import java.util.*;
import java.util.stream.Collectors;


public class TurnOrder{
	
	private PlayerMatrix actionOrder;
	private List<Player> playerInGame;
	private PlayerIterator iterator;
	
	public TurnOrder(List<Player> players){	
		this.playerInGame=new ArrayList<>(players);
		this.actionOrder=new PlayerMatrix(players);
		this.iterator=new PlayerIterator(actionOrder);						
	}
	
	public void setupRound(List<Player> list){
		list.addAll(playerInGame);
		List<Player>players=
		list.stream()
		.distinct()
		.collect(Collectors.toList());		
		generateActionOrder(players);
	}
	
	private void generateActionOrder(List<Player> list) {
		this.actionOrder=new PlayerMatrix(list);
		this.iterator=new PlayerIterator(actionOrder);
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
		System.out.println("primo "+to.getNextPlayer());
		System.out.println("secondo "+to.getNextPlayer());
		System.out.println("terzo "+to.getNextPlayer());
		System.out.println("... ");
	}
}




class PlayerMatrix implements Iterable<Player> {
	
	private List<List<Player>> playerMatrix;
	
	public PlayerMatrix(List<Player> playerInGame) {
		this.playerMatrix=new ArrayList<>(new ArrayList<>());
		for(int i=0;i<playerInGame.size();i++)
			this.playerMatrix.add(playerInGame);
	}
	
	public Player getPlayer(int r,int c){
		return this.playerMatrix.get(r).get(c);
	}
	
	public int rows(){
		return this.playerMatrix.size();
	}
	
	public int columns(){
		return this.playerMatrix.get(0).size();
	}

	@Override
	public Iterator<Player> iterator() {
		return new PlayerIterator(this);
	}

	@Override
	public String toString() {
		return "" + playerMatrix + "";
	}	
}



class PlayerIterator implements Iterator<Player>{
	
	private PlayerMatrix playerMatrix;
	private int currentRow=0,currentColumns=0;
	
	public PlayerIterator(PlayerMatrix m){
		this.playerMatrix=m;
	}

	@Override
	public boolean hasNext() {
		return currentRow<=playerMatrix.rows();
	}
	
	@Override
	public Player next() {
		Player p=playerMatrix.getPlayer(currentRow, currentColumns);
		currentColumns++;
		if(currentColumns>playerMatrix.columns()){
			currentColumns=0;
			currentRow++;
		}		
		return p;
	}
	
}

