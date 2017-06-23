package reti.server;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gamemodel.Model;
import gamemodel.Player;
import reti.ServerResponse;

public class GameManager implements Runnable 
{
	private ExecutorService pool = Executors.newCachedThreadPool();
	Map<Player,HandlerView> playerToHV=new HashMap<>();
	List<HandlerView> hw = new ArrayList<>();
	protected String whoWokeMeUp="";
	private boolean isFull=false;
	final int delay=1000;
	
	
	private void setupGame()
	{
		System.out.println("creazione partita");      
		Model rl=new Model(hw.size());
		Controller c=new Controller(rl);
		rl.setController(c);
		for(int i=0;i<rl.getPlayers().size();i++){
			Player p = rl.getPlayers().get(i);
			HandlerView hv = hw.get(i);
			playerToHV.put(p, hv);    //TODO get random player
			hv.setController(c);
			hv.setPlayer(p);
			if(hv instanceof HandlerViewSocket)
				pool.execute((Runnable) hv);
		}
		c.setPlayerToHV(playerToHV);
		System.out.println("game partito con " + hw.size());
		c.notifyNewModel();
		for(HandlerView h: hw){
			h.sendResponse(new ServerResponse(h.getPlayer()));
		}
		rl.giveLeaderCard();
		c.run();
	}
	
	private synchronized void checkWait()
	{
		try 
		{
			this.wait();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void addHV(HandlerView hv)
	{
		hw.add(hv);
		System.out.println("aggiunto in"+this);
		if(hw.size()==4)
			isFull=true;
		whoWokeMeUp="Add";
		this.notify();
		
	}
	
	@Override
	public synchronized void run() 
	{
		try
		{
			while(!isFull)
			{
				switch(hw.size())
				{
					case 0: this.wait();break;
					case 1: this.wait();break;
					case 2: new Timer().schedule(new T(this),delay);this.checkWait();break;
					case 3: new Timer().schedule(new T(this),delay);this.checkWait();break;
				}
			}
			this.setupGame();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}		
	}

	public synchronized void timerFinishded() {		
		whoWokeMeUp="TimeOut";
		isFull=true;
		notify();		
	}

	public boolean getIsFull() {
		return this.isFull;
	}
}

class T extends TimerTask
{
	GameManager gm;
	
	public T(GameManager gm) {
		this.gm = gm;
	}
	
	public void run(){
		gm.timerFinishded();
	}
}

