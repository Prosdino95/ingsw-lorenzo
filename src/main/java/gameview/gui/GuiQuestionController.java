package gameview.gui;

import java.util.List;


import gamemodel.GameQuestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import reti.ServerResponse;

public class GuiQuestionController {
	
	@FXML TextFlow text;
	@FXML VBox question;
	@FXML TextField servants;
	@FXML TextFlow servantsQuest;
	@FXML AnchorPane root;
	private ToggleGroup choices;
	private ServerResponse sr;
	private String answer;
	
	public void update(ServerResponse sr){
		question.getChildren().clear();
		text.getChildren().clear();
		servants.getChildrenUnmodifiable().clear();
		root.setBackground(new Background(new BackgroundImage(new Image("/wood.jpg"), null, null, null, null)));
		servants.setEditable(false);
		choices=new ToggleGroup();
		question.setSpacing(-20);
		question.setStyle("-fx-background-color: transparent;");
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
		case NEW_MODEL:
			this.text.getChildren().add(new Text("new model"));	
			break;
		case OK:
			this.text.getChildren().add(new Text("ok from server"));	
			break;
		case ERROR:
		case MESSAGE:	
		case PLAYER_ASSIGNED:
			initializeTextOnly();
			break;	
		default:
			break;		
		}
	}

	private void initializeQuestion() {		
		Text text=new Text(sr.getQuestion().getGq().toString());
		this.text.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		this.text.getChildren().add(text);	
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
			vbox.setStyle("-fx-background-color: transparent;");
			RadioButton rbutton=new RadioButton();
			if(id%4==0){
				hbox=new HBox();
				hbox.setSpacing(20);
				hbox.setStyle("-fx-background-color: transparent;");
				question.getChildren().add(hbox);
			}				
			if(id==0)
				rbutton.setSelected(true);
			rbutton.setId(id.toString());
			rbutton.setToggleGroup(choices);
			Text chooseText=new Text(o.toString());	
			chooseText.setFont(Font.font(7));
			vbox.getChildren().add(rbutton);
			TextFlow textFlow=new TextFlow(chooseText);
			double top=12;
			double left=50;
			textFlow.setPadding(new Insets(top,left, 0, 0));
			textFlow.setPrefSize(153, 146);
			vbox.getChildren().add(textFlow);
			hbox.getChildren().add(vbox);
			id++;
		}	
	}

	private void questionHowMany() {
		answer="0";
		Text t=new Text("write the number and press ENTER");
		servantsQuest.getChildren().add(t);
		servants.setEditable(true);
		servants.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case ENTER:
				try{
					Integer.parseInt(servants.getText());
					answer=servants.getText();
				}
				catch(NumberFormatException ex){
					t.setText(" so you must write a NUMBER and press ENTER");
				}
				answer=servants.getText();
				break;
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




