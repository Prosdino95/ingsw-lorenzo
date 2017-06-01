package gameview;

import java.util.function.Consumer;

public class UINodeSetRequestType extends UINode {
	
	Consumer<RequestType> set;
	RequestType type;

	public UINodeSetRequestType(String desc,Consumer<RequestType> set,RequestType type, UITree tree) {
		super(desc, tree);
		this.set=set;
		this.type=type;
	}

	@Override
	public void run() {
		set.accept(type);
		super.run();
	}

}
