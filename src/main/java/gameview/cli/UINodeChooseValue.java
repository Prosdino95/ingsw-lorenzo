package gameview.cli;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import gamemodel.actionSpace.ActionSpace;

public class UINodeChooseValue<E extends Object> extends UINode {
	Consumer<E> setter;
	Supplier<List<E>> getterList;

	public UINodeChooseValue (String desc,Consumer<E> set,Supplier<List<E>> getterList, UITree tree) {
		super(desc, tree);
		this.setter=set;
		this.getterList=getterList;
	}

	private void print(List<E> list)
	{
		int i = 0;
		for(Object o:list) {
			System.out.print(i);
			System.out.print(": ");
			System.out.println(o.toString());
			i++;
		}
	}
	
	@Override
	public void run() throws IOException {
		System.out.println(intro);
		print(getterList.get());
		try {
			E choose = getterList.get().get(CLIView.getInt());
			setter.accept(choose);			
			super.run();
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Invalin choise");
			run();
		}
	}
}
