package gameview.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;

import gameview.ViewController;


public class CLIView {
	static BufferedReader inKeyboard = new BufferedReader(new InputStreamReader(System.in));

	UITree uiTree;
	
	public static void main(String[] arg) throws IOException, InterruptedException, NotBoundException {		
		CLIView view = new CLIView();
		ViewController hs=new ViewController();
		view.uiTree = new UITree(hs);
		view.uiTree.run();
	}
	
	public static int getInt(){
		try {
			return Integer.parseInt(inKeyboard.readLine());
		} catch (NumberFormatException e) {
			return getInt();
		} catch (IOException e) {
			e.printStackTrace();
			return getInt();
		}
	}

	public static String getString(){
		try {
			return CLIView.inKeyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getString();
	}
	

}


