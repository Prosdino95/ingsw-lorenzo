package gameview;


public class UINodeChooseUI extends UINode {

	int choose;

	public UINodeChooseUI(String desc, UITree tree) {
		super(desc, tree);
	}

	@Override
	public void run() {
		print();
		this.nextNode=this.nextNode();
	}

	@Override
	public UINodeChooseUI addSon(UINode node) {
		 super.addSon(node);
		 return this;
		
	}

	private void print() {
		System.out.println("Choose:");
		int i = 0;
		for (UINode n : descendants) {
			System.out.print(i);
			System.out.print(": ");
			System.out.println(n.getShortDescription());
			i++;
		}
	}

	private UINode nextNode() {
		choose=CLIView.getInt();	
		try{
			return descendants.get(choose);					
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Invalin choise");
			run();
		}	
		return descendants.get(choose);	
	}	
}