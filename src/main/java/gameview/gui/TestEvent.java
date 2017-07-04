package gameview.gui;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TestEvent extends Application {
	TimerEvent te;
	private Stage stage;
	private MyEvent ev;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		te = new TimerEvent(this);
		Timer timer;
		timer = new Timer();
		timer.schedule(te, 0, 1000);
		
		EventType<MyEvent> E_TYPE = new EventType<>("E_TYPE");
		this.ev = new MyEvent(E_TYPE);
		stage.addEventHandler(E_TYPE, e -> {
			System.out.println("YAAAAAY!");
		});
		
		Pane root = new Pane();
		stage.setScene(new Scene(root ));
		stage.show();
	}

	
	public void timerFinished() {
		stage.fireEvent(ev);
	}

	public static void main(String args[]){
		Application.launch(TestEvent.class, args);		
	}
	
}

class MyEvent extends Event {

	public MyEvent(EventType<? extends Event> eventType) {
		super(eventType);
		// TODO Auto-generated constructor stub
	}

}


class TimerEvent extends TimerTask
{
	private TestEvent te;

	public TimerEvent(TestEvent testEvent) {
		super();
		this.te = testEvent;
	}
	
	public void run(){
		te.timerFinished();
	}
}