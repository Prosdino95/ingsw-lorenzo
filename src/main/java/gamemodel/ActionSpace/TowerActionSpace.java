package gamemodel.ActionSpace;

import gamemodel.Card;
import gamemodel.FamilyMember;
import gamemodel.Tower;

public interface TowerActionSpace extends ActionSpace {

	Tower getTower();

	void attachDevelopmentCard(Card card);

	void giveCard(FamilyMember f);

	Card getCard();

}
