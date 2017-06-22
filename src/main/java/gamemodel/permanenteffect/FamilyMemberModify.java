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
	private int debuff=0;
	private int set=0;
	
	public static FamilyMemberModify allMembersN(Integer n) {
		return new FamilyMemberModify((f) -> f.setActionpoint(n),n,0);
	}
	
	public static FamilyMemberModify allMembersDebuff(Integer n) {
		return new FamilyMemberModify((f) -> f.setActionpoint(f.getActionpoint()-n),0,n);
	}

	// TODO: map to list
	public FamilyMemberModify(Consumer<Map<Color,FamilyMember>> f) {
		
		super(PEffect.FM);
		this.f=f;
	}

	public FamilyMemberModify(Consumer<FamilyMember> g,int set,int debuff) {
		super(PEffect.FM);
		this.set=set;
		this.debuff=debuff;
		this.g=g;
	}
	
	public void modify(Map<Color,FamilyMember> familyMembers){
		if (f != null)
			f.accept(familyMembers);
	}

	public void modify(List<FamilyMember> familyMembers){
		if (g != null)
			for (FamilyMember f : familyMembers)
				g.accept(f);
	}

	@Override
	public String toString() {
		return "FamilyMemberModify [debuff=" + debuff + ", set=" + set + "]";
	}
	
	

}
