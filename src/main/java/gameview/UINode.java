package gameview;


import java.util.ArrayList;
import java.util.List;

public abstract class UINode {
	protected List<UINode> descendants;
	protected String intro;
	protected ClientResponse response;
	protected UINode nextNode;
	
	public UINode(String desc) {
		this.intro = desc;
		this.descendants = new ArrayList<UINode>();
	}

	public UINode getNextNode()
	{
		return nextNode;
	}
	
	public UINode addSon(UINode node) {
		descendants.add(node);
		return this;
	}
	
	public String getShortDescription() {
		return intro;
	}	
	
	public abstract void run();
}
