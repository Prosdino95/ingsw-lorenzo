package servertest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import server.GameManager;
import server.HandlerViewSocket;

public class GameManagerTest {
	GameManager gm;

	@Before
	public void setUp() throws Exception 
	{	
		gm=new GameManager();
	}

	@Test 
	public void test() throws IOException 
	{
		new Thread(gm).start();
		gm.addHV(new HandlerViewSocket());
		gm.addHV(new HandlerViewSocket());
		gm.addHV(new HandlerViewSocket());
		gm.addHV(new HandlerViewSocket());
		if(!gm.getIsFull())
			gm.addHV(new HandlerViewSocket());		
	}
	
	@Test 
	public void test2() throws IOException, InterruptedException 
	{
		new Thread(gm).start();
		gm.addHV(new HandlerViewSocket());
		gm.addHV(new HandlerViewSocket());
		Thread.sleep(6000);
		if(!gm.getIsFull())
			gm.addHV(new HandlerViewSocket());		
	}

	@Test 
	public void test3() throws IOException, InterruptedException 
	{
		new Thread(gm).start();
		gm.addHV(new HandlerViewSocket());
		gm.addHV(new HandlerViewSocket());
		Thread.sleep(6000);
		gm.addHV(new HandlerViewSocket());
		gm.addHV(new HandlerViewSocket());
	}
	

}
