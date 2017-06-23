package gameview.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class PlayerBoardController {
	@FXML private Pane b0;
	@FXML Pane cartaTorre2;
	
	public void metti(GUICard card) {
       b0.getChildren().add(card);       
    }

}
