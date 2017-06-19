package gameview.gui;

import javafx.fxml.*;
import javafx.scene.control.*;

public class ControllerCard {

	@FXML private TextField userTextField;
	
	public void text(String text){
		userTextField.appendText(text);
	}
	
}
