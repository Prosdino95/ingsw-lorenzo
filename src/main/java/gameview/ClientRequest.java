package gameview;

import gamemodel.ActionSpace.*;
import gamemodel.*;

public class ClientRequest {	
	private RequestType type;
	private ActionSpace where;
	private int servants;
	private FamilyMember which;
	private String answer;
	
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
		this.where=where;
	}
	
	public void setWhich(FamilyMember which){
		this.which=which;
		
	}

	@Override
	public String toString() {
		return "ClientRequest [type=" + type + ", where=" + where + ", servants=" + servants + ", which=" + which
				+ ", answer=" + answer + "]";
	}

	public void cleanUp() {
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}
}
