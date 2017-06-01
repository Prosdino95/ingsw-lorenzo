package gameview;


import java.util.ArrayList;
import java.util.List;

public abstract class UINode {
	protected List<UINode> descendants;
	protected String intro;
	protected ClientRequest response;
	protected UINode nextNode;
	protected UITree tree;
	
	public UINode(String desc, UITree tree) {
		this.intro = desc;
		this.tree = tree;
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
	
	public void run() {
		if(!descendants.isEmpty())
			this.nextNode=descendants.get(0);	
	}
}
