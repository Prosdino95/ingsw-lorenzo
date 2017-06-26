package gamemodel.effects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.FamilyMember;
import gamemodel.GameQuestion;
import gamemodel.Player;
import gamemodel.Question;
import gamemodel.command.GameException;

public class SetOneColoredFM implements IstantEffect,Serializable {

	private static final long serialVersionUID = 1L;
	private int actionPoint;
	
	public SetOneColoredFM(int actionPoint){
		this.actionPoint=actionPoint;
	}

	@Override
	public void activate(Player player) throws GameException {
		Integer index;
		List<Object>list=new ArrayList<>();
		list.addAll(player.getFamilyMembersList());
		index=player.answerToQuestion(0, new Question(GameQuestion.CHOOSE_FAMILY_MEMBER,list));
		FamilyMember fm=(FamilyMember)list.get(index);
		fm.setActionpoint(actionPoint);
	}

}
