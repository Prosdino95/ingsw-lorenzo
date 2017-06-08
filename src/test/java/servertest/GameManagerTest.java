package servertest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import server.GameManager;
import server.HandlerView;

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
		gm.addHV(new HandlerView());
		gm.addHV(new HandlerView());
		gm.addHV(new HandlerView());
		gm.addHV(new HandlerView());
		if(!gm.getIsFull())
			gm.addHV(new HandlerView());		
	}
	
	@Test 
	public void test2() throws IOException, InterruptedException 
	{
		new Thread(gm).start();
		gm.addHV(new HandlerView());
		gm.addHV(new HandlerView());
		Thread.sleep(6000);
		if(!gm.getIsFull())
			gm.addHV(new HandlerView());		
	}

	@Test 
	public void test3() throws IOException, InterruptedException 
	{
		new Thread(gm).start();
		gm.addHV(new HandlerView());
		gm.addHV(new HandlerView());
		Thread.sleep(6000);
		gm.addHV(new HandlerView());
		gm.addHV(new HandlerView());
	}
	

}
