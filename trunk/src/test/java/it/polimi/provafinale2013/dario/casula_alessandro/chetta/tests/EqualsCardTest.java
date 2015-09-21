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

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.*;

import java.awt.Color;

import javax.swing.ImageIcon;

import org.junit.Test;


public class EqualsCardTest {
	ImageIcon image = new ImageIcon("");
	
	StableCard dieSwarzMondTest1 = new StableCard("Die Swarz Mond", 58, image, Color.BLACK, image);
	StableCard dieSwarzMondTest2 = new StableCard("Die Swarz Mond", 58, image, Color.BLACK, image);
	StableCard theBlueBloodTest = new StableCard("The Blue Blood", 56, image, Color.BLUE, image);
	
	ActionCard magnaVelocitasTest1 = new ActionCard("Magna Velocitas", 1, image, image, "A", Effect.POSITIVE, ActionType.START, 4, false);
	ActionCard magnaVelocitasTest2 = new ActionCard("Magna Velocitas", 1, image, image, "A", Effect.POSITIVE, ActionType.START, 4, false);
	ActionCard fortunaBenevolaTest = new ActionCard("Fortuna Benevola", 2, image, image, "B", Effect.POSITIVE, ActionType.START, 1, true);
	
	CharacterCard cranioMercantiTest = new CharacterCard("Cranio Mercanti", 71, image, 3400, 2);	

	@Test
	public void equalStableCardTest() {
		boolean condition = this.dieSwarzMondTest1.equals(dieSwarzMondTest2);
		assertTrue(condition);
	}

	@Test
	public void differentStableCardTest() {
		boolean condition = this.dieSwarzMondTest1.equals(theBlueBloodTest);
		assertFalse(condition);
	}
	
	@Test
	public void equalActionCardTest() {
		boolean condition = this.magnaVelocitasTest1.equals(magnaVelocitasTest2);
		assertTrue(condition);
	}
	
	@Test
	public void differentActionCardTest() {
		boolean condition = this.magnaVelocitasTest1.equals(fortunaBenevolaTest);
		assertFalse(condition);
	}
	
	@Test
	public void sameCharacterCardTest() {
		boolean condition = this.cranioMercantiTest.equals(cranioMercantiTest);
		assertTrue(condition);
	}
	
	@Test
	public void nullCharacterCardTest() {
		boolean condition = this.cranioMercantiTest.equals(null);
		assertFalse(condition);
	}
}
