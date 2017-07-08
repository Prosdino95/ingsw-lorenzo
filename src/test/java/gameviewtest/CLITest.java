package gameviewtest;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import gamemodel.Model;
import gamemodel.player.Player;
import gamemodel.player.Resource;
import gamemodel.player.Team;
import gameview.cli.UITree;
import reti.ServerResponse;

public class CLITest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IOException {
		UITree uiTree;
		
		LinkedList<String> stringChoices = new LinkedList<>();
		LinkedList<Integer> intChoices = new LinkedList<>();
		LinkedList<ServerResponse> messages = new LinkedList<>();
		LinkedList<ServerResponse> responses = new LinkedList<>();

		Model m = new Model(2);
		m.setCurretPlayer(new Player(new Resource(0,0,0,0), m.getBoard(), Team.RED));

		messages.addLast(new ServerResponse(m));
		messages.addLast(new ServerResponse(m.getCurrentPlayer()));
		intChoices.addLast(0);
		intChoices.addLast(0);
		intChoices.addLast(0);
		stringChoices.addLast("0");
		responses.addLast(new ServerResponse());
		intChoices.addLast(4);

		uiTree = new UITree(intChoices, stringChoices, messages, responses);
		
		uiTree.run();
	}

}
