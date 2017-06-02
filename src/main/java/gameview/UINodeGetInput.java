package gameview;

import java.util.function.Consumer;

public class UINodeGetInput extends UINode {
	
	Consumer<String> set;
	String choose;

	public UINodeGetInput(String desc,Consumer<String> set, UITree tree) {
		super(desc, tree);
		this.set=set;
	}
	
	@Override
	public void run() {
		System.out.println(intro);
		choose = CLIView.getString();
		try {
			set.accept(choose);
		} catch (NumberFormatException e) {
			System.out.println("QUANTI => Mi serve un numero");
			run();
		}
		super.run();
	}

}
