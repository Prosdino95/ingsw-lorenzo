package gameview;

import gamemodel.ActionSpace.*;
import gamemodel.*;

public class ClientResponse {	
	private ResponseType type;
	private ActionSpace where;
	private int servants;
	private FamilyMember which;
	
	
	public void setType(ResponseType type) {
		this.type = type;
	}
	
	public void setServants(String servants) {
		this.servants= Integer.parseInt(servants);
	}
		
	public ResponseType getType() {
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
		return "ClientResponse [type=" + type + ", where=" + where + ", servants=" + servants + ", which=" + which
				+ "]";
	}

	public void cleanUp() {
	}
}
