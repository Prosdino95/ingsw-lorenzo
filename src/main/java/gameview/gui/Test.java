package gameview.gui;

import java.util.ArrayList;
import java.util.List;

import gamemodel.GameQuestion;
import gamemodel.Model;
import gamemodel.Point;
import gamemodel.Question;
import gamemodel.Resource;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.card.Card;
import gamemodel.card.CardType;
import gamemodel.card.CharactersCard;
import gamemodel.command.GameError;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.TestEffects;
import gameview.gui.actionspacecontroll.CouncilPlaceActionSpaceControll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import reti.ServerResponse;

public class Test extends Application{
	List<Object> choose=new ArrayList<>();

		@Override
		public void start(Stage stage) throws Exception {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/CouncilPlaceActionSpace.fxml"));		
			Pane root = loader.load();
			CouncilPlaceActionSpaceControll controller=loader.getController();
			//GuiQuestionController controller=loader.getController();
			creacarte();
			//creaaltro();
			//controller.update(new ServerResponse(GameError.RESOURCE_ERR_SERVANTS));
			//controller.update(new ServerResponse(new Question(GameQuestion.LEADER,choose)));
			//controller.update(new ServerResponse(new Question(GameQuestion.HOW_MANY_FMS , new ArrayList<>())));
			ActionSpace a=new MemoryActionSpace(5, 4, new TestEffects(), null);
			controller.initialize(a);
			stage.setScene(new Scene(root));
			stage.show();
		}
		private void creaaltro() {
			choose.add(new Resource(5,5,5,5));
			choose.add(new Resource(5,5,5,5));
			choose.add(new Resource(5,5,5,5));
			choose.add(new Resource(5,5,5,5));
			
			choose.add(new Resource(5,5,5,5));
			
			
		}
		private void creacarte() {
			List<IstantEffect> l=new ArrayList<>();
			CharactersCard r=new CharactersCard(0,"name",1,new Resource(1,1,1,1),new Resource(1,1,1,1),new Point(1,1,1),new Point(1,1,1),l,null,CardType.CHARACTER);
			CharactersCard r1=new CharactersCard(1,"name",1,new Resource(1,1,1,1),new Resource(1,1,1,1),new Point(1,1,1),new Point(1,1,1),l,null,CardType.CHARACTER);
			CharactersCard r2=new CharactersCard(2,"name",1,new Resource(1,1,1,1),new Resource(1,1,1,1),new Point(1,1,1),new Point(1,1,1),l,null,CardType.CHARACTER);
			CharactersCard r3=new CharactersCard(3,"name",1,new Resource(1,1,1,1),new Resource(1,1,1,1),new Point(1,1,1),new Point(1,1,1),l,null,CardType.CHARACTER);
			for(int i=0;i<3;i++){
			choose.add(r);
			choose.add(r1);
			choose.add(r2);
			choose.add(r3);
			}
		}
		public static void main(String args[]){
			Application.launch(Test.class, args);		
		}
}


