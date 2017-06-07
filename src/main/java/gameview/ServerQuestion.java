package gameview;

import java.io.Serializable;

public class ServerQuestion implements Serializable{

	private static final long serialVersionUID = 1L;
	private String answer;
	private String question;
	
	public ServerQuestion(String question) {
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}
	
	public void ask() {
		System.out.println(question);
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ClientRequest getRequest() {
		ClientRequest req = new ClientRequest();
		req.setType(RequestType.ANSWER);
		req.setAnswer(answer);
		return req;
	}

	@Override
	public String toString() {
		return "ServerQuestion [question=" + question + "]";
	}
}
