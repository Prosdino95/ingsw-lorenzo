package gamemodel.permanenteffect;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import gamemodel.Color;
import gamemodel.FamilyMember;

public class FamilyMemberModify extends PermanentEffect {
	private static final long serialVersionUID = 1L;
	public Consumer<Map<Color,FamilyMember>> f;
	private Consumer<FamilyMember> g;
	
	public static FamilyMemberModify allMembersN(Integer n) {
		return new FamilyMemberModify((f) -> f.setActionpoint(n));
	}

	// TODO: Perche' stiamo prendendo il tag?
	public FamilyMemberModify(String tag,Consumer<Map<Color,FamilyMember>> f) {
		super(PEffect.FM);
		this.f=f;
	}

	public FamilyMemberModify(Consumer<FamilyMember> g) {
		super(PEffect.FM);
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

}
