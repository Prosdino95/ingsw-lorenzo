package gameview.gui;

import java.util.List;

import gamemodel.GameQuestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import reti.ServerResponse;

public class GuiQuestionController {
	
	@FXML TextFlow text;
	@FXML VBox question;
	private ToggleGroup choices;
	private ServerResponse sr;
	private String answer;
	
	public void update(ServerResponse sr){
		choices=new ToggleGroup();
		choices.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				answer=((RadioButton)choices.getSelectedToggle()).getId();	         
			} 
		});
		this.sr=sr;
		switch(sr.getType()){	
		case QUESTION:
		case LEADER:
			initializeQuestion();
			break;
		case ERROR:
		case MESSAGE:
		case NEW_MODEL:
		case OK:
		case PLAYER_ASSIGNED:
			initializeTextOnly();
			break;	
		case VATICAN_SUPPORT:
			break;
		default:
			break;
		
		}
	}

	private void initializeQuestion() {		
		text.getChildren().add(new Text(sr.getQuestion().getGq().toString()));	
		if(sr.getQuestion().getGq()==GameQuestion.HOW_MANY_FMS)
			questionHowMany();
		else
			genericQuestion();
	}

	private void genericQuestion() {
		Integer id=0;
		List<Object> list=sr.getQuestion().getChoose();
		HBox hbox =null;
		for(Object o:list){
			VBox vbox=new VBox();
			RadioButton rbutton=new RadioButton();
			if(id%4==0){
				hbox=new HBox();
				question.getChildren().add(hbox);
			}				
			if(id==0)
				rbutton.setSelected(true);
			rbutton.setId(id.toString());
			rbutton.setToggleGroup(choices);
			Text text=new Text(o.toString());			
			vbox.getChildren().add(rbutton);
			vbox.getChildren().add(new TextFlow(text));
			hbox.getChildren().add(vbox);
			id++;
		}			
	}

	private void questionHowMany() {
		Text t=new Text("   write the number and press ENTER");
		text.getChildren().add(t);
		TextField tf=new TextField();
		question.getChildren().add(tf);
		tf.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case ENTER:
				answer=tf.getText();
			default:
				break;
			}
		});
	}

private void initializeTextOnly() {
		if(sr.getMessage()!=null)
			text.getChildren().add(new Text(sr.getMessage()));
		if(sr.getError()!=null){
			text.getChildren().add(new Text("you can't do this because: "));
			text.getChildren().add(new Text(sr.getError().toString()));
		}		
	}
	
	public void test(){
		System.out.println(this.answer);
	}

	public String getAnswer() {
		return answer;
	}
}




