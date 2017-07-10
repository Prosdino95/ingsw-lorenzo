package gameview.cli;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import gamemodel.Model;
import gamemodel.player.Team;
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
				
				switch (sr.getType()) {
				case NEW_MODEL:
					newModel(sr.getModel());
					
					break;
				case MESSAGE:

					System.out.println(sr.getMessage());
					break;
				case PLAYER_ASSIGNED:
					if (!tree.hasModel) {

						break;
					}
					Team team = sr.getPlayerTeam();
					System.out.println("Your player got assigned, you're team: " + team);
					tree.setPlayer(team);

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
					}
					leaderPlayed  = true;
					break;
				default:

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
		tree.setModel(model);
		switch(model.getState()){
		case GAME_FINISH:
			break;
		case PLAYER_PLAING:
			System.out.println("It's the turn of player "+model.getCurrentPlayer().toString2());
			break;
		case SET_UP_ROUND:
			System.out.println("Setting up turn " + model.turn + ".");
			break;
		case VATICAN_TIME:
			System.out.println("It's time to play with the Pope, unless you're too poor");
			System.out.println("Do you want to support him?");
			System.out.println("1)Yes \n 2)No");
			try {
				tree.sendRequestToServer(new ClientRequest(this.tree.getString(),
						RequestType.VATICAN_REPORT));
			} catch (OfflineException e) {
			}
			break;
		default:
			break;
		
		}
		
	}
}


