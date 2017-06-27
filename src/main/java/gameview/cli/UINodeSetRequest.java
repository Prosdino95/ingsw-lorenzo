package gameview.cli;

import java.io.IOException;
import java.util.function.Consumer;

import reti.RequestType;

public class UINodeSetRequest extends UINode {
	
	Consumer<RequestType> set;
	RequestType type;

	public UINodeSetRequest(String desc,Consumer<RequestType> set,RequestType type, UITree tree) {
		super(desc, tree);
		this.set=set;
		this.type=type;
	}

	@Override
	public void run() throws IOException {
		set.accept(type);
		super.run();
	}

}
