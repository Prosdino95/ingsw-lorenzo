package gamemodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import reti.ClientRequest;
import reti.RequestType;

public class Question implements Serializable{

	private static final long serialVersionUID = 1L;
	private GameQuestion gq; 
	private List<Object> choose;
	
	public Question(GameQuestion gq, List<Object> choose) {
		this.gq=gq;
		this.choose=choose;
	}

	public GameQuestion getGq() {
		return gq;
	}

	public void setGq(GameQuestion gq) {
		this.gq = gq;
	}

	public List<Object> getChoose() {
		return choose;
	}

	public void setChoose(List<Object> choose) {
		this.choose = choose;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	static List<Object> yesOrNo()
	{
		List<Object> list=new ArrayList<Object>();
		list.add("Nope");
		list.add("Yes");	
		return list;
	}

	@Override
	public String toString() {
		String s = gq + "?";
		int i = 0;
		for (Object o : choose) {
			s += " ";
			s += i;
			s += ": ";
			s += o.toString();
			i++;
		}
		return s;
	}
}
