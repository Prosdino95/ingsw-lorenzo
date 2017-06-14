package gameview;

import java.io.IOException;

import gamemodel.Team;
import reti.ServerResponse;

public class UINodeLog extends UINode 
{
	ViewController hs;
	
	public UINodeLog(String desc, UITree tree,ViewController hs) 
	{
		super(desc, tree);
		this.hs=hs;
	}
	
	@Override
	public void run() throws IOException
	{
		boolean run=true;
		ServerResponse sr=null;
		while(run)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(hs.hasMessage()) {		
				sr=hs.getMessage();
				// System.out.println(sr);
				switch (sr.getType()) {
				case NEW_MODEL:
					System.out.println("A new model has arrived...");
					tree.setModel(sr.getModel());
					break;
				case MESSAGE:
					System.out.print("Someone sent you this message: ");
					System.out.println(sr.getMessage());
					break;
				case PLAYER_ASSIGNED:
					if (!tree.hasModel) {
						System.out.println("They sent me the player but I still did not get the model...");
						break;
					}
					Team team = sr.getPlayerTeam();
					System.out.println("Your player got assigned, you're team: " + team);
					tree.setPlayer(team);
					System.out.println("Now get out of this log and play!");
					break;
				default:
					System.out.println("Should this message get here? " + sr);
					break;
				}
			}
			
			if(CLIView.inKeyboard.ready() && tree.hasModel && tree.hasPlayer) {
				run=false;
			}
		}
		
		super.run();
	}
}
