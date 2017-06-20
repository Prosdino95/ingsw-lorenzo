package gameview.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class PlayerBoardController {
	@FXML private Pane b0;
	
	public void metti(GuiCard card) {
       b0.getChildren().add(card);       
    }

}
