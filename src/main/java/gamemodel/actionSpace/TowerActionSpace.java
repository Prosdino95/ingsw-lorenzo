package gamemodel.actionSpace;

import java.io.Serializable;

import gamemodel.FamilyMember;
import gamemodel.Tower;
import gamemodel.card.Card;
import gamemodel.command.GameException;

public interface TowerActionSpace extends ActionSpace,Serializable {

	Tower getTower();

	void attachDevelopmentCard(Card card);

	void giveCard(FamilyMember f) throws GameException;

	Card getCard();

}
