package gamemodel;

import java.io.Serializable;
import java.util.List;

import gameview.ClientRequest;
import gameview.RequestType;
import server.GameQuestion;

public class Question implements Serializable{

	private static final long serialVersionUID = 1L;
	private String answer;
	private GameQuestion gq; 
	private List<Object> choose;
	
	public Question(GameQuestion gq,List<Object> choose) {
		this.gq=gq;
		this.choose=choose;
	}


	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ClientRequest createRequest() {
		ClientRequest req = new ClientRequest();
		req.setType(RequestType.ANSWER);
		req.setAnswer(answer);
		return req;
	}


	@Override
	public String toString() {
		return "Question [gq=" + gq + ", choose=" + choose + "]";
	}
}
