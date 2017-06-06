package server;

import java.util.*;

public class GameManager implements Runnable 
{
	List<HandlerView> hw=new ArrayList<>();
	protected String whoWokeMeUp="";
	private boolean isFull=false;
	final int delay=5000;
	
	
	private void setupGame()
	{
		System.out.println("game partito con " + hw.size());
		return;
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
		if(whoWokeMeUp=="TimeOut")
			isFull=true;
		if(whoWokeMeUp=="Add")
			return;
	}
	
	public synchronized void addHV(HandlerView hv)
	{
		hw.add(hv);
		System.out.println("aggiunto");
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
		// TODO Auto-generated method stub
		whoWokeMeUp="TimeOut";
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

