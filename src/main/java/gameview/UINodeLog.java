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
			if(hs.hasMessage()) {		
				sr=hs.getMessage();
//				if(sr.isThereANewModel())
	//				ms.update(response.getModel());
				if(sr.isThereAMessage())
					System.out.println(sr.getMessage());
			}
			if(CLIView.inKeyboard.ready())
				run=false;
		}
		super.run();
	}

}
