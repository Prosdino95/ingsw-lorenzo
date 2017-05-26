package gamemodel.ActionSpace;

import gamemodel.FamilyMember;
import gamemodel.Tower;
import gamemodel.card.Card;

public interface TowerActionSpace extends ActionSpace {

	Tower getTower();

	void attachDevelopmentCard(Card card);

	void giveCard(FamilyMember f);

	Card getCard();

}
