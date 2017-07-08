package gamemodel.effects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.GameQuestion;
import gamemodel.Question;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.player.FamilyMember;
import gamemodel.player.Player;

public class SetOneColoredFM implements IstantEffect,Serializable {

	private static final long serialVersionUID = 1L;
	private int actionPoint;
	
	public SetOneColoredFM(int actionPoint){
		this.actionPoint=actionPoint;
	}

	@Override
	public void activate(Player player) throws GameException {
		Integer index = 0;
		List<Object>list=new ArrayList<>();
		list.addAll(player.getFamilyMembers());
		try {
			index=player.answerToQuestion(new Question(GameQuestion.CHOOSE_FAMILY_MEMBER,list));
		} catch (GameException e) {
			if (e.getType() == GameError.NOT_PLAYING_ONLINE)
				index = 0;
			else
				throw e;
		}
		
		FamilyMember fm=(FamilyMember)list.get(index);
		fm.setActionpoint(actionPoint);
	}
	@Override
	public String toString()
	{
		return " One of family members'value " + actionPoint;
	}
}
