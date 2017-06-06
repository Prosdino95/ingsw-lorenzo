package server;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gamemodel.RealGame;

public class GameManager implements Runnable 
{
	private ExecutorService pool = Executors.newCachedThreadPool();
	List<HandlerView> hw=new ArrayList<>();
	protected String whoWokeMeUp="";
	private boolean isFull=false;
	final int delay=5000;
	
	
	private void setupGame()
	{
		System.out.println("creazione partita");
		RealGame rl=new RealGame(hw.size());
		Controller c=new Controller(rl);
		for(int i=0;i<hw.size();i++){
			hw.get(i).setPlayer(rl.getPlayers().get(i));
			hw.get(i).setController(c);
			pool.execute(hw.get(i));
		}
		System.out.println("game partito con " + hw.size());
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

