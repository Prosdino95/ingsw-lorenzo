package gameview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import gamemodel.*;

public class CLIView {
	static BufferedReader inKeyboard = new BufferedReader(new InputStreamReader(System.in));

	UITree uiTree;
	
	public static void main(String[] arg) throws IOException {		
		CLIView view = new CLIView();
		view.uiTree = new UITree(new ModelShell(),new HandlerServer());
		view.uiTree.run();
	}
	
	public static int getInt(){
		try {
			return Integer.parseInt(inKeyboard.readLine());
		} catch (NumberFormatException e) {
			getInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
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


