package gamemodel.effects;

import java.util.ArrayList;
import java.util.List;

import gamemodel.Action;
import gamemodel.Board;
import gamemodel.Color;
import gamemodel.FamilyMember;
import gamemodel.GameQuestion;
import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Question;
import gamemodel.Resource;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.TowerActionSpace;
import gamemodel.card.CardType;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.permanenteffect.StrengthModifyAndDiscount;

public class BonusAction implements IstantEffect {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Resource discount;
	private Integer actionValue;
	private FamilyMember familyMember;
	private Board board;

	private CardType cardType;
	private ActionSpaceType asType;

	public BonusAction(Board board, Integer actionValue, CardType cardType, Resource discount) {
		this.board = board;
		this.discount = discount;
		this.actionValue = actionValue;
		this.cardType = cardType;
	}

	public BonusAction(Board board, int actionValue, ActionSpaceType asType) {
		if (asType != ActionSpaceType.HARVEST &&
			asType != ActionSpaceType.PRODUCTION) throw new AssertionError();
		this.board = board;
		this.actionValue = actionValue;
		this.asType = asType;
	}



	@Override
	public void activate(Player player) throws GameException {
		this.familyMember = new FamilyMember(player, Color.STRANGE, this.actionValue);

		ActionSpace chosenActionSpace = null;
		StrengthModifyAndDiscount tempEffect = null;
		if (cardType != null) {
			List<TowerActionSpace> actionSpaces = board.getActionSpaces(cardType);
			List<Object> javaSucks = new ArrayList<>();
			for (TowerActionSpace tas : actionSpaces) {
				javaSucks.add(tas);
			}
			
			Integer defaultAS = 2;
			
			try {
				Integer choice = player.answerToQuestion(new Question(GameQuestion.SELECT_ACTION_SPACE, javaSucks));
				chosenActionSpace = actionSpaces.get(choice);
			} catch (GameException e) {
				if (e.getType() == GameError.NOT_PLAYING_ONLINE) 
					chosenActionSpace = (TowerActionSpace) this.board.getActionSpace(defaultAS);			
			}
			
			tempEffect = new StrengthModifyAndDiscount(this.discount);
			player.registerPermanentEffect(tempEffect);

		} else {
			chosenActionSpace = board.getActionSpaces(asType).get(0);
		}
		
		Integer defaultNS = 0;
		Integer chosenServantsNo = 0;
		try {
			Integer choice = player.answerToQuestion(new Question(GameQuestion.HOW_MANY_FMS , new ArrayList<>()));
			chosenServantsNo = choice;
		} catch (GameException e) {
			if (e.getType() == GameError.NOT_PLAYING_ONLINE)
				chosenServantsNo = defaultNS;
		}
		
		player.placeFamilyMember(new Action(player, chosenActionSpace, familyMember, chosenServantsNo));
		player.removePermanentEffect(tempEffect);

	}

	@Override
	public String toString() {
		String str="Bonus action:";
		if(cardType!=null)
			str+=" C Type= " + cardType + ",";
		if(asType!=null)
			str+=" AS type= " + asType + ",";
		if(actionValue!=0)
			str+=" strength= " + actionValue + ",";
		if(discount!=null)
			str+=" discount= " + discount;
		return str;
	}
}
