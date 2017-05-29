package gameview;

import java.util.function.Consumer;

public class UINodeSetResponseType extends UINode {
	
	Consumer<ResponseType> set;
	ResponseType type;

	public UINodeSetResponseType(String desc,Consumer<ResponseType> set,ResponseType type) {
		super(desc);
		this.set=set;
		this.type=type;
	}

	@Override
	public void run() {
		set.accept(type);
		if(!descendants.isEmpty())
			this.nextNode=descendants.get(0);	
	}

}
