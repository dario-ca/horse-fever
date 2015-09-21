/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.tests;
//test del metodo chooseTheWinnerPlayer nella classe Game
import static org.junit.Assert.*;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.*;

import java.util.ArrayList;
import org.junit.Test;

public class ChooseTheWinnerPlayerTest {
	
	Game testGame = new Game();
	ArrayList<Player> playersTest = new ArrayList<Player>();
	
	//primo test: vince chi ha più punti vittoria
	@Test
	public void testVictoryPoints() {
		Player firstPlayerTest = new Player("firstPlayer", null, null, null, 6, null, null);
		Player secondPlayerTest = new Player("secondPlayer", null, null, null, 5, null, null);
		Player thirdPlayerTest = new Player("thirdPlayer", null, null, null, 4, null, null);
		Player fourthPlayerTest = new Player("fourthPlayer", null, null, null, 3, null, null);
		Player fifthPlayerTest = new Player("fifthPlayer", null, null, null, 2, null, null);
		Player sixthPlayerTest = new Player("sixthPlayer", null, null, null, 1, null, null);
	
		playersTest.add(firstPlayerTest);
		playersTest.add(secondPlayerTest);
		playersTest.add(thirdPlayerTest);
		playersTest.add(fourthPlayerTest);
		playersTest.add(fifthPlayerTest);
		playersTest.add(sixthPlayerTest);
		
		testGame.addAllPlayers(playersTest);
		
		Player winnerTest = testGame.chooseTheWinnerPlayer();
		assertEquals("Winner for Victory Points",firstPlayerTest, winnerTest);
	}
	
	//secondo test:hanno tutti lo stesso numero di PV, vince chi ha più danari.
	@Test
	public void testDanariAmount(){
		Player firstPlayerTest = new Player("firstPlayer", null, 600, null, 6, null, null);
		Player secondPlayerTest = new Player("secondPlayer", null, 500, null, 6, null, null);
		Player thirdPlayerTest = new Player("thirdPlayer", null, 400, null, 6, null, null);
		Player fourthPlayerTest = new Player("fourthPlayer", null, 300, null, 6, null, null);
		Player fifthPlayerTest = new Player("fifthPlayer", null, 200, null, 6, null, null);
		Player sixthPlayerTest = new Player("sixthPlayer", null, 100, null, 6, null, null);
	
		playersTest.add(firstPlayerTest);
		playersTest.add(secondPlayerTest);
		playersTest.add(thirdPlayerTest);
		playersTest.add(fourthPlayerTest);
		playersTest.add(fifthPlayerTest);
		playersTest.add(sixthPlayerTest);
		
		testGame.addAllPlayers(playersTest);
		
		Player winnerTest = testGame.chooseTheWinnerPlayer();
		assertEquals("Winner for Danari Amount",firstPlayerTest, winnerTest);
	}
}
