package gamemodel.permanenteffect;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import gamemodel.Color;
import gamemodel.FamilyMember;

public class FamilyMemberModify extends PermanentEffect {
	private static final long serialVersionUID = 1L;
	private transient Consumer<FamilyMember> g;
	private int quantity=0;
	private int set=0;
	private Color type=null;
	
	public static FamilyMemberModify coloredMembersSet(Integer n) {
		return new FamilyMemberModify((f) -> f.setActionpoint(n),n,0);
	}
	
	public static FamilyMemberModify coloredMembersDebuff(Integer n) {
		return new FamilyMemberModify((f) -> f.setActionpoint(f.getActionpoint()-n),0,n);
	}
	
	public static FamilyMemberModify coloredMembersBuff(Integer n) {
		return new FamilyMemberModify((f) -> f.setActionpoint(f.getActionpoint()+n),0,n);
	}
	
	public static FamilyMemberModify oneMembersBuff(Integer n,Color type) {
		return new FamilyMemberModify((f) -> f.setActionpoint(f.getActionpoint()+n),0,n,type);
	}

	public FamilyMemberModify(Consumer<FamilyMember> g,int set,int quantity) {
		super(PEffect.FM);
		this.set=set;
		this.quantity=quantity;
		this.g=g;
	}
	
	public FamilyMemberModify(Consumer<FamilyMember> g, int set, int quantity, Color type) {		
		super(PEffect.FM);
		this.set=set;
		this.quantity=quantity;
		this.g=g;
		this.type=type;
	}

	public void modify(List<FamilyMember> familyMembers){
		if (g != null)
			for (FamilyMember f : familyMembers)
				if(type==null && f.getColor()!= Color.UNCOLORED)
					g.accept(f);
				else if(f.getColor()==type)
					g.accept(f);
	}

	@Override
	public String toString() {
		String s = "";
		s+="FM: ";
		if(type!=null)
			s+=type;
		else s+="colored";
		s+="have ";
		if(set!=0)
			s+=set+"point";
		if(quantity!=0){
			s+="+"+quantity+"point";
		}
		return s;
	}
}
