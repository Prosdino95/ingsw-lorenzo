package reti.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private int joinDelay;
	private Timer timer=new Timer();
	private int playerActionDelay;
	
	public GameManager(){
		this.joinDelay=1000;
		this.playerActionDelay=200000;
	}
	
	public GameManager(int delay,int gameDelay){
		this.joinDelay=delay;
		this.playerActionDelay=gameDelay;
	}
		
	private void setupGame()
	{
		System.out.println("creazione partita");      
		Model rl=new Model(hw.size(),playerActionDelay);
		Controller c=new Controller(rl);
		rl.setController(c);
		List<Integer> number=new ArrayList<>();
		for(int i=0;i<rl.getPlayers().size();i++)
			number.add(i);
		Collections.shuffle(number);
		for(int i=0;i<rl.getPlayers().size();i++){			
			Player p = rl.getPlayers().get(number.get(i));
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
		c.giveLeaderCard();
		c.run();
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
					case 2: updateTimer();
							this.wait();
							break;
					case 3: updateTimer();
							this.wait();
							break;
				}
			}
			this.setupGame();
		}
		catch(Exception ex)
		{
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", ex);
			Thread.currentThread().interrupt();
		}		
	}

	private void updateTimer() {
		timer.cancel();
		timer=new Timer();
		timer.schedule(new Task(this), joinDelay);
		
	}

	public synchronized void timerFinishded() {		
		whoWokeMeUp="TimeOut";
		isFull=true;
		timer.cancel();
		notify();		
	}

	public boolean getIsFull() {
		return this.isFull;
	}
}

class Task extends TimerTask
{
	GameManager gm;
	
	public Task(GameManager gm) {
		this.gm = gm;
	}
	
	public void run(){
		gm.timerFinishded();
	}
}

