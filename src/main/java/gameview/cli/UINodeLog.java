package gameview.cli;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import gamemodel.Model;
import gamemodel.Team;
import gameview.ViewController;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;

public class UINodeLog extends UINode 
{

	private boolean leaderPlayed = false;

	public UINodeLog(String desc, UITree tree,ViewController hs) 
	{
		super(desc, tree);
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
				Thread.currentThread().interrupt();
				Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
			}
			boolean hasMessage;
			hasMessage = this.tree.hasMessage();
			if(hasMessage) {		
				sr=tree.getMessage();
				
				// System.out.println(sr);
				switch (sr.getType()) {
				case NEW_MODEL:
					newModel(sr.getModel());
					
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
				case LEADER:
					System.out.println(sr.getQuestion());
					Supplier<Boolean> asdf = () -> {
						System.out.println("Ain't got time for this, just select one of the possible choices");
						return true;
					};
					try {
						String s = null;
						Integer i;
						do {
							i = this.tree.getInt();
						} while (i >= sr.getQuestion().getChoose().size() && asdf.get());
						s = i.toString();
						tree.sendRequestToServer(new ClientRequest(s));
					} catch (OfflineException e) {
						System.out.println("Catched offline exception");
					}
					leaderPlayed  = true;
					break;
				default:
					System.out.println("Should this message get here? " + sr);
					break;
				}
			}
			
			if (tree.isOffline())
				leaderPlayed = true;
			boolean canPlay = leaderPlayed && tree.hasModel && tree.hasPlayer;
			if(canPlay && (tree.inKeyboard.ready() || tree.isOffline())) {
				run=false;
			}
		}
		
		super.run();
	}
	
	private void newModel(Model model){
		System.out.print("A new model has arrived... It says:");
		tree.setModel(model);
		switch(model.getState()){
		case GAME_FINISH:
			break;
		case PLAYER_PLAING:
			System.out.println("it's player turn :"+model.getCurrentPlayer().toString2());
			break;
		case SET_UP_ROUND:
			System.out.println("the turn is finished:"+model.turn);
			break;
		case VATICAN_TIME:
			System.out.println("it's time to play with the Pope, unless you're too poor");
			System.out.println("1)yes \n 2)not");
			try {
				tree.sendRequestToServer(new ClientRequest(this.tree.getString(),
						RequestType.VATICAN_REPORT));
			} catch (OfflineException e) {
				System.out.println("Catched offline exception");
			}
			break;
		default:
			break;
		
		}
		
	}
}


