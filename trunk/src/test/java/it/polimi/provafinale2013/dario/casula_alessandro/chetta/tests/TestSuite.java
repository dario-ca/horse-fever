/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.tests;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ChooseTheWinnerPlayerTest.class,
	EqualsCardTest.class,
	GameTest.class,
	LaneTest.class,
	OddsBlackBoardTest.class,
	PayPlayersTest.class,
	PlayerTest.class,
	RaceTest.class,
	TurnManagerTest.class,
	UpdateRankingTest.class
	})

public class TestSuite {}