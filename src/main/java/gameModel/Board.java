package gameModel;

import java.util.HashMap;
import java.util.Map;

public class Board {
	
	private Map<Integer,ActionSpace> actionSpaces;
	
	public Board(){
		actionSpaces=new HashMap<>();
	}

	public void addActionSpace(ActionSpace a){
		actionSpaces.put(a.getId(),a);
	}

	public ActionSpace getActionSpaces(int id) {
		return actionSpaces.get(id);
	}
	
	
	

}
