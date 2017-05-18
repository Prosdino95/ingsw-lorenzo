package gamemodeltest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import gamemodeltest.command.*;

@RunWith(Suite.class)
@SuiteClasses({
	PlaceFamilyMemberCommandCouncilPlaceTest.class,
	PlaceFamilyMemberCommandMarketTest.class,
	PlaceFamilyMemberCommandHAndPTest.class,
	PlaceFamilyMemberCommandTowerTest.class
	})
public class CommandTests {	
}
