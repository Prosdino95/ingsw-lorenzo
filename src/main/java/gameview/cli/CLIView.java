package gameview.cli;


import java.io.IOException;

import gameview.ViewController;

public class CLIView {

	
	public static void main(String[] arg) throws IOException {		
		UITree uiTree;
		
		ViewController hs = new ViewController();
		uiTree = new UITree(hs);
		
		uiTree.run();
	}

	public static void cliStart(String networkChoose) throws IOException {
		UITree uiTree;
		
		ViewController hs = new ViewController(networkChoose);
		uiTree = new UITree(hs);
		
		uiTree.run();
		
		
	}
}


