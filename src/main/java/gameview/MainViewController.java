package gameview;

import gameview.cli.CLIView;
import gameview.gui.GuiView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;




public class MainViewController {
	@FXML RadioButton rmi,gui,socket,cli;
	private final ToggleGroup network = new ToggleGroup();
	private final ToggleGroup view = new ToggleGroup();
	private String networkChoose="rmi";
	private String viewChoose="gui";
	private Stage stage;
	private GuiView gv;
	
	public void initialize(Stage stage){
		this.stage=stage;
		gui.setToggleGroup(view);
		cli.setToggleGroup(view);
		rmi.setToggleGroup(network);
		socket.setToggleGroup(network);
		rmi.setSelected(true);
		cli.setSelected(true);		
		network.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (network.getSelectedToggle() ==rmi) 
					networkChoose="rmi";
				else if (network.getSelectedToggle() ==socket) 
					networkChoose="socket";			         
			} 
		});
		view.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (view.getSelectedToggle() ==cli) 
					viewChoose="cli";
				else if (view.getSelectedToggle() ==gui) 
					viewChoose="gui";
			         
			} 
		});
	}
	
	public void connect() throws Exception{
		if(viewChoose=="cli"){
			CLIView.cliStart(networkChoose);
		}
		if(viewChoose=="gui"){
			gv=new GuiView();
			gv.start(stage);
		}
	}

	public void stop(){
		if(gv!=null)
			gv.stop();
	}


}
