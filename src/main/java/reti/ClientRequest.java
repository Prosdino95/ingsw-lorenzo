package reti;


import gamemodel.actionSpace.ActionSpace;
import gameview.gui.LeaderCardAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gamemodel.*;

public class ClientRequest  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestType type;
	private int where;
	private int servants;
	private Color which;
	private String answer;
	private Player player;
	private LeaderCardAction action;
	private Integer lcID;
	private transient LeaderCard sleaderCard;
	
	public ClientRequest(String string) {
		type = RequestType.ANSWER;
		answer = string;
	}
	
	public ClientRequest(LeaderCard lc, LeaderCardAction action) {
		type = RequestType.LEADERCARD;
		this.setAction(action); 
		sleaderCard = lc;
		this.lcID = lc.getId();
	}
	
	
	public void setWhichLeaderCard(LeaderCard lc) {
		sleaderCard = lc;
		lcID = lc.getId();
	}
	
	public LeaderCard getLeaderCard() {
		return sleaderCard;
	}

	
	public ClientRequest() {
		this.type=RequestType.FINISHACTION;
	}

	public ClientRequest(RequestType t) {
		this.type = t;
	}
	
	public ClientRequest(String a, RequestType type) {
		this.answer=a;
		this.type=type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
	
	public void setServants(String servants) {
		this.type = RequestType.PLACEFAMILYMEMBER;
		this.servants= Integer.parseInt(servants);
	}
		
	public RequestType getType() {
		return type;
	}
	
	public void setWhere(ActionSpace where){
		this.type = RequestType.PLACEFAMILYMEMBER;
		this.where=where.getId();
	}

	public void setWhere(Integer id){
		this.type = RequestType.PLACEFAMILYMEMBER;
		this.which=Color.UNCOLORED;
		this.where=id;
	}

	public void setWhich(FamilyMember which){
		this.type = RequestType.PLACEFAMILYMEMBER;
		this.which=which.getColor();
	}

	public void setWhich(Color color){
		this.type = RequestType.PLACEFAMILYMEMBER;
		this.which=color;
	}


	public int getWhere() {
		return where;
	}

	public int getServants() {
		return servants;
	}

	public Color getWhich() {
		return which;
	}
	
	public void setPlayer(Player player){
		this.player=player;
	}

	@Override
	public String toString() {
		String str = "";
		str += this.type;
		str += " ";
		switch (this.type) {
		case ANSWER:
			str += "Answer: " + answer;
			break;
		case CHAT:
			break;
		case FINISHACTION:
			break;
		case IWANTAMODEL:
			break;
		case IWANTMONEY:
			break;
		case LEADERCARD:
			str += "LeaderCard: " + sleaderCard + " Action: " + getAction();
			break;
		case PLACEFAMILYMEMBER:
			str += "Where: " + where + " Servants: " + servants + "Which: " + which;
			break;
		case VATICAN_REPORT:
			str += "Answer: " + answer;
			break;
		default:
			break;
		}

		return str;
	}

	public void cleanUp() {
	}

	public void setAnswer(String answer) {
		this.type = RequestType.ANSWER;
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}

	public Player getPlayer() {
		return player;
	}

	public List<LeaderCardAction> possibleLeaderCardActions() {
		return sleaderCard.getPossibleActions();

	}

	public Integer getLeaderCardID() {
		return lcID;
	}

	public void setServants(int i) {
		this.type = RequestType.PLACEFAMILYMEMBER;
		servants = i;
	}

	public LeaderCardAction getAction() {
		return action;
	}

	public void setAction(LeaderCardAction action) {
		this.action = action;
	}
}
