package gamemodeltest;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import gamemodeltest.command.*;
import gamemodeltest.effects.*;

@RunWith(Suite.class)
@SuiteClasses({
	PlaceFamilyMemberCommandCouncilPlaceTest.class,
	PlaceFamilyMemberCommandMarketTest.class,
	PlaceFamilyMemberCommandHAndPTest.class,
	PlaceFamilyMemberCommandHAndPLittleTest.class,
	PlaceFamilyMemberCommandTowerTest.class,
	CommandPeffectTest.class,
	BonusActionTest.class,
	ExchangeTest.class,
	HarvesterAndBuildingsTest.class,
	ResourceForResourceTest.class,
	SetOneColoredFMTest.class,
	DoubleCostCardTest.class,
	LeaderCardTest.class,
	ModelTest.class,
	TurnOrderTest.class
	})
public class ModelSuiteTests {	
}
