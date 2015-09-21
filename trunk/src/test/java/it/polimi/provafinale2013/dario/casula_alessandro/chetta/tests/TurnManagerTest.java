/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.tests;

import static org.junit.Assert.*;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.TurnManager;
import org.junit.Test;

public class TurnManagerTest {
	
	TurnManager turnManagerTest = new TurnManager(6, 0, false);
	
	@Test
	public void getCurrentPlayerTest(){
		assertTrue(this.turnManagerTest.getCurrentPlayer()==0);
	}
	
	@Test
	public void hasNextTest(){
		assertTrue(this.turnManagerTest.hasNext());
	}
	
	@Test
	public void goNextTest(){
		this.turnManagerTest.goNext();
		assertTrue(this.turnManagerTest.getCurrentPlayer()==1);
	}
	
	@Test
	public void getFirstPlayerTest(){
		assertTrue(this.turnManagerTest.getFirstPlayer()==0);
	}
}
