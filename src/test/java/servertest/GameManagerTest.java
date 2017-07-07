package servertest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import reti.server.HandlerViewSocket;
import reti.server.Server;

public class GameManagerTest {
	Server s;

	@Before
	public void setUp() throws Exception 
	{	
		s=new Server();
	}

	@Test 
	public void test() throws IOException 
	{
		s.addHV(new HandlerViewSocket());
		s.addHV(new HandlerViewSocket());
		s.addHV(new HandlerViewSocket());
		s.addHV(new HandlerViewSocket());
		s.addHV(new HandlerViewSocket());		
	}		
	
	@Test 
	public void test2() throws IOException, InterruptedException 
	{
		s.addHV(new HandlerViewSocket());
		s.addHV(new HandlerViewSocket());
		Thread.sleep(6000);
		s.addHV(new HandlerViewSocket());		
	}

	@Test 
	public void test3() throws IOException, InterruptedException 
	{
		s.addHV(new HandlerViewSocket());
		s.addHV(new HandlerViewSocket());
		Thread.sleep(6000);
		s.addHV(new HandlerViewSocket());
		s.addHV(new HandlerViewSocket());
	}
	

}
