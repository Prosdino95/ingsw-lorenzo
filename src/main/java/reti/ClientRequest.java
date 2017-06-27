package reti;


import gamemodel.actionSpace.ActionSpace;

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
	private LeaderCard leaderCard;
	private String what;
	private Integer lcID;
	
	public ClientRequest(String string) {
		type = RequestType.ANSWER;
		answer = string;
	}
	
	public void setWhichLeaderCard(LeaderCard lc) {
		leaderCard = lc;
		lcID = lc.getId();
	}
	
	public LeaderCard getLeaderCard() {
		return leaderCard;
	}

	
	public void setWhatLC(String what) {
		this.what = what;
	}

	public ClientRequest() {
		super();
	}
	
	public ClientRequest(String a, RequestType type) {
		this.answer=a;
		this.type=type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
	
	public void setServants(String servants) {
		this.servants= Integer.parseInt(servants);
	}
		
	public RequestType getType() {
		return type;
	}
	
	public void setWhere(ActionSpace where){
		this.where=where.getId();
	}
	
	public void setWhich(FamilyMember which){
		this.which=which.getColor();
		
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
		return "ClientRequest [type=" + type + ", where=" + where + ", servants=" + servants + ", which=" + which
				+ ", answer=" + answer + "]";
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

	public List<String> possibleLeaderCardActions() {
		List<String> lst = new ArrayList<>();
		if (leaderCard.getPlayed()) {
			if (leaderCard.getPermanentEffect() == null) {
				lst.add("Nothing");
			} else if (leaderCard.getPlayedOPR()) { 
				lst.add("Nothing");
			} else {
				lst.add("Activate OPR effect");
			}
		} else {
			lst.add("Scartare");
			lst.add("Play it");
		}
		return lst;
	}

	public String getWhatLC() {
		return what;
	}

	public Integer getLeaderCardID() {
		return lcID;
	}
}
