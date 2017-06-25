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
	private CardType cardType;
	
	private FamilyMember familyMember;
	private Board board;

	public BonusAction(Board board, Integer actionValue, CardType cardType, Resource discount) {
		this.board = board;
		this.discount = discount;
		this.actionValue = actionValue;
		this.cardType = cardType;
	}

	@Override
	public void activate(Player player) throws GameException {
		this.familyMember = new FamilyMember(player, Color.STRANGE, this.actionValue);

		List<TowerActionSpace> actionSpaces = board.getActionSpaces(cardType);
		List<Object> javaSucks = new ArrayList<>();
		for (TowerActionSpace tas : actionSpaces) {
			javaSucks.add(tas);
		}
		
		Integer defaultAS = 2;
		Integer defaultNS = 0;
		
		TowerActionSpace chosenActionSpace = null;
		try {
			
			Integer choice = player.answerToQuestion(defaultAS, new Question(GameQuestion.SELECT_ACTION_SPACE, javaSucks));
			chosenActionSpace = actionSpaces.get(choice);
		} catch (GameException e) {
			if (e.getType() == GameError.NOT_PLAYING_ONLINE) 
				chosenActionSpace = (TowerActionSpace) this.board.getActionSpace(defaultAS);			
		}

		Integer chosenServantsNo = null;
		try {
			Integer choice = player.answerToQuestion(defaultNS, new Question(GameQuestion.HOW_MANY_FMS , new ArrayList<>()));
			chosenServantsNo = choice;
		} catch (GameException e) {
			if (e.getType() == GameError.NOT_PLAYING_ONLINE)
				chosenServantsNo = defaultNS;
		}
		
		
//		System.out.println(chosenActionSpace);
//		System.out.println(player);
		
		StrengthModifyAndDiscount tempEffect = 
				new StrengthModifyAndDiscount(this.discount);
		player.registerPermanentEffect(tempEffect);
		player.placeFamilyMember(new Action(player, chosenActionSpace, familyMember, chosenServantsNo));
		player.removePermanentEffect(tempEffect);

//		System.out.println(chosenActionSpace);
//		System.out.println(player);


	}

	@Override
	public String toString() {
		return "BonusAction [discount=" + discount + ", actionValue=" + actionValue + ", cardType=" + cardType + "]";
	}
	
	
}
