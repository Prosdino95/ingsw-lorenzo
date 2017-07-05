package gamemodel.permanenteffect;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import gamemodel.Color;
import gamemodel.FamilyMember;

public class FamilyMemberModify extends PermanentEffect {
	private static final long serialVersionUID = 1L;
	public transient Consumer<Map<Color,FamilyMember>> f;
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

	// TODO: map to list
	public FamilyMemberModify(Consumer<Map<Color,FamilyMember>> f) {
		
		super(PEffect.FM);
		this.f=f;
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

	public void modify(Map<Color,FamilyMember> familyMembers){
		if (f != null)
			f.accept(familyMembers);
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
		return "FamilyMemberModify [quantity=" + quantity + ", set=" + set + ", type=" + type + "]";
	}
	@Override
	public String toStringGui() {
		return "FamilyMemberModify [quantity=" + quantity + ", set=" + set + ", type=" + type + "]";
	}

	
	

}
