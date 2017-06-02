package gamemodel.permanenteffect;

import java.util.Map;
import java.util.function.Consumer;

import gamemodel.Color;
import gamemodel.FamilyMember;

public class FamilyMemberModify extends PermanentEffect {
	public Consumer<Map<Color,FamilyMember>> f;
	

	public FamilyMemberModify(String tag,Consumer<Map<Color,FamilyMember>> f) {
		super("FM");
		this.f=f;
	}
	
	public void modify(Map<Color,FamilyMember> familyMembers){
		 f.accept(familyMembers);
	}

}
