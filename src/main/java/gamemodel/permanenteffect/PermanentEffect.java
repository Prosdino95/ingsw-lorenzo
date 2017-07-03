package gamemodel.permanenteffect;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

public class PermanentEffect implements Serializable
{

	private static final long serialVersionUID = 1L;
	private List<PEffect> tag= new ArrayList<>();
	

	public PermanentEffect(PEffect tag) 
	{
		this.tag.add(tag);
	}
	
	public PermanentEffect(List<PEffect> tag) 
	{
		this.tag=tag;
	}
	
	public boolean hasTag(PEffect tag){
		return this.tag.contains(tag);
	}

	public void addTag(PEffect tag) {
		this.tag.add(tag);		
	}
	
	public String toString()
	{
		String string="";
		for(PEffect pe:tag)
		{
			if(pe==PEffect.HALVE_SERVANTS)
				string+=" You have to spend 2 servants to increase your action value by 1.";
			if(pe==PEffect.NO_MATTER_IF_OCCUPIED)
				string+=" You can place your family members in occupied action spaces.";
			if(pe==PEffect.NO_BONUS)
				string+=" You don’t take the bonuses when you take a development card from the third and the fourth floor of the towers.";
			if(pe==PEffect.NO_VICTORY_POINTS_BOUNDED_TO_CHARACTER_CARDS)
				string+=" At the end of the game, you don’t score points for any of your character card.";
			if(pe==PEffect.NO_VICTORY_POINTS_BOUNDED_TO_VENTURE_CARDS)
				string+=" At the end of the game, you don’t score points for any of your venture card.";
			if(pe==PEffect.NO_VICTORY_POINTS_BOUNDED_TO_TERRITORY_CARDS)
				string+=" At the end of the game, you don’t score points for any of your territory card.";
			if(pe==PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_FIVE_VICTORY_POINT)
				string+=" At the end of the game you lose 1 victory point for every 5 victory points you have.";
			if(pe==PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_MILITARY_POINT)
				string+=" At the end of the game you lose 1 victory point for every military point you have.";
			if(pe==PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_WOOD_AND_STONE_ON_YOUR_BUILDINGS_CARDS_COST)
				string+=" At the end of the game you lose 1 victory point for every wood and stone on your building card's cost.";
			if(pe==PEffect.LOSE_ONE_VICTORY_POINT_FOR_EVERY_RESOURCE)
				string+=" At the end of the game you lose 1 victory point for every resource you have.";
			if(pe==PEffect.NO_FIRST_ACTION)  //da sistemare chiedere a Marco
				string+=" Each round you skip your first turn. When all players have taken all their turns you may still\r\n" + "place your last Family Member";
			if(pe==PEffect.NO_NEED_TO_PAY_3_COINS)
				string+=" You don’t have to spend 3 coins when you place your family members in a tower that is already occupied.";
			if(pe==PEffect.RESOURCES_TWICE_FROM_DEVELOPEMENT_CARDS_ISTANT_EFFECT)
				string+=" Each time you receive wood, stone, coins, or servants as an immediate effect from development cards you receive the resources twice.";
			if(pe==PEffect.FIVE_ADDITIONAL_VICTORY_POINTS_WHEN_SUPPORT_THE_CHURCH)
				string+=" You gain 5 additional victory points when you support the Church.";
			if(pe==PEffect.NO_NEED_TO_SATISFY_MILITARY_POINTS_REQUIREMENT)
				string+=" You don’t need to satisfy the military points requirement when you take territory cards.";
		}
		return string;
	}
}
