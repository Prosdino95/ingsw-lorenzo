package gameview;

import java.io.IOException;

public class UINodeLog extends UINode 
{

	HandlerServer hs;
	
	public UINodeLog(String desc, UITree tree,HandlerServer hs) 
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
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(hs.hasMessage()) {		
				sr=hs.getMessage();
				System.out.println(sr);
				if(sr.isThereANewModel())
					tree.updateShell(sr.getModel());
				if(sr.isThereAMessage())
					System.out.println(sr.getMessage());
			}
			if(CLIView.inKeyboard.ready())
				run=false;
		}
		super.run();
	}

}
