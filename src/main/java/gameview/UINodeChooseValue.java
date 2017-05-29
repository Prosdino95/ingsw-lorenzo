package gameview;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UINodeChooseValue<E extends Object> extends UINode {
	Consumer<E> setter;
	Supplier<List<E>> getterList;

	public UINodeChooseValue (String desc,Consumer<E> set,Supplier<List<E>> getterList) {
		super(desc);
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
	public void run() {
		System.out.println(intro);
		print(getterList.get());
		try {
			E choose = getterList.get().get(CLIView.getInt());
			setter.accept(choose);			
			if(!descendants.isEmpty())
			this.nextNode=descendants.get(0);
			}		
		catch(IndexOutOfBoundsException e){
			System.out.println("Invalin choise");
			run();
			}
	}
}
