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
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionType;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.Effect;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.*;

import java.awt.Color;
import javax.swing.ImageIcon;
import org.junit.Test;

public class LaneTest {
	
	ImageIcon image = new ImageIcon("");
	StableCard firstStableCard = new StableCard("Die Swarz Mond", 58, image, Color.BLACK, image);
	Lane firstLaneTest = new Lane(firstStableCard);

	@Test
	public void resetLaneTest(){
		//metto una carta azione sulla lane
		ActionCard inIgniVeritasTest = new ActionCard("In Igni Veritas", 5, image, image, null, Effect.POSITIVE, ActionType.PHOTOFINISH, 0, false);
		firstLaneTest.addActionCardOnLane(inIgniVeritasTest);
		//impongo una posizione diversa da zero della pedina
		firstLaneTest.setLanePawnPosition(13);
		
		this.firstLaneTest.resetLane();
		assertTrue(this.firstLaneTest.getLaneActionCards().isEmpty() && this.firstLaneTest.getLanePawnPosition()==0);
	}
	
	@Test
	public void addActionCardOnLaneTest(){
		//metto una carta azione sulla lane
		ActionCard inIgniVeritasTest = new ActionCard("In Igni Veritas", 5, image, image, null, Effect.POSITIVE, ActionType.PHOTOFINISH, 0, false);
		firstLaneTest.addActionCardOnLane(inIgniVeritasTest);
		assertTrue(this.firstLaneTest.getLaneActionCards().get(0).equals(inIgniVeritasTest));
	}
	
	@Test
	public void getLaneOfStableTest(){
		assertTrue(this.firstLaneTest.getLaneOfStable().equals(firstStableCard));
	}
	
	@Test
	//caso in cui sia presente una carta che impone l'avanzamento
	public void startOfTestMagnaVelocitas(){
		//metto magna velocitas sulla lane (mi avanza di 4 anzichè della carta movimento)
		ActionCard magnaVelocitasTest = new ActionCard("Magna Velocitas", 1, image, image, "A", Effect.POSITIVE, ActionType.START, 4, false);
		firstLaneTest.addActionCardOnLane(magnaVelocitasTest);
		this.firstLaneTest.startOf(6);
		assertTrue(this.firstLaneTest.getLanePawnPosition()==4);
	}
	
	@Test
	//caso in cui sia presente una carta movimento che diminuisce l'avanzamento
	public void startOfTestAquaPutrida(){
		//metto aqua putrida sulla lane (diminuisce di una casella l'avanzamento)
		ActionCard aquaPutridaTest = new ActionCard("Aqua Putrida", 9, image, image, "B", Effect.NEGATIVE, ActionType.START, 1, true);
		firstLaneTest.addActionCardOnLane(aquaPutridaTest);
		this.firstLaneTest.startOf(6);
		assertTrue(this.firstLaneTest.getLanePawnPosition()==5);
	}
	
	@Test
	//caso in cui non ci siano carte azione
	public void goAheadOfTest(){
		this.firstLaneTest.goAheadOf(4);
		assertTrue(this.firstLaneTest.getLanePawnPosition()==4);
	}
	
	@Test
	//caso in cui ci sia la carta che impone uno sprint doppio
	public void giveSprintTestHerbaMagica(){
		//metto herba magica sulla lane (impone uno sprint doppio)
		ActionCard herbaMagicaTest = new ActionCard("Herba Magica", 4, image, image, "D", Effect.POSITIVE, ActionType.SPRINT, 2, false);
		firstLaneTest.addActionCardOnLane(herbaMagicaTest);
		this.firstLaneTest.giveSprint();
		assertTrue(this.firstLaneTest.getLanePawnPosition()==2);
	}
	
	@Test
	//caso in cui ci sia la carta che blocca lo sprint
	public void giveSprintTestSerumMaleficum(){
		//metto serum maleficum sulla lane (blocca lo sprint)
		ActionCard serumMaleficumTest = new ActionCard("Serum Maleficum", 10, image, image, "C", Effect.NEGATIVE, ActionType.SPRINT, 0, false);
		firstLaneTest.addActionCardOnLane(serumMaleficumTest);
		this.firstLaneTest.giveSprint();
		assertTrue(this.firstLaneTest.getLanePawnPosition()==0);
	}
	
	@Test
	public void removeSameLetterCardsTest(){
		//metto vigor ferreum sulla lane
		ActionCard vigorFerreumTest = new ActionCard("Vigor Ferreum", 7, image, image, "G", Effect.POSITIVE, ActionType.HELPLAST, 4, false);
		firstLaneTest.addActionCardOnLane(vigorFerreumTest);
		//metto felix infernalis sulla lane
		ActionCard felixInfernalis = new ActionCard("Felix Infernalis", 14, image, image, "G", Effect.NEGATIVE, ActionType.STOPFIRST, 0, false);
		firstLaneTest.addActionCardOnLane(felixInfernalis);
		
		this.firstLaneTest.removeSameLetterCards();
		assertTrue(this.firstLaneTest.getLaneActionCards().isEmpty());
	}
	
	@Test
	public void checkRemoveCardsTest(){
		//metto Rochelle Recherche sulla Lane
		ActionCard rochelleRechercheTest = new ActionCard("Rochelle Recherche", 20, image, image, null, Effect.SPECIAL, ActionType.REMOVEPOSITIVE, 0, false);
		this.firstLaneTest.addActionCardOnLane(rochelleRechercheTest);
		//metto una carta azione positiva sulla Lane
		ActionCard flagellumFulgurisTest = new ActionCard("Flagellum Fulguris", 3, image, image, "C", Effect.POSITIVE, ActionType.SPRINT, 1, true);
		this.firstLaneTest.addActionCardOnLane(flagellumFulgurisTest);
		
		this.firstLaneTest.checkRemoveCards();
		//sulla Lane rimane solo la carta Rochelle Recherche
		assertTrue((this.firstLaneTest.getLaneActionCards().size()==1) && this.firstLaneTest.getLaneActionCards().get(0).equals(rochelleRechercheTest));
	}
	
	@Test
	public void hasPhotoFinishCardTest(){
		//metto In igni veritas sulla lane
		ActionCard inIgniVeritasTest = new ActionCard("In Igni Veritas", 5, image, image, null, Effect.POSITIVE, ActionType.PHOTOFINISH, 0, false);
		firstLaneTest.addActionCardOnLane(inIgniVeritasTest);
		
		assertTrue(this.firstLaneTest.hasPhotoFinishCard(Effect.POSITIVE));
	}
	
	@Test
	public void hasHelpLastCardTest(){
		//metto vigor ferreum sulla lane
		ActionCard vigorFerreumTest = new ActionCard("Vigor Ferreum", 7, image, image, "G", Effect.POSITIVE, ActionType.HELPLAST, 4, false);
		firstLaneTest.addActionCardOnLane(vigorFerreumTest);
		
		assertTrue(this.firstLaneTest.hasHelpLastCard());
	}
	
	@Test
	public void hasStopFirstCardtest(){
		//metto felix infernalis sulla lane
		ActionCard felixInfernalis = new ActionCard("Felix Infernalis", 14, image, image, "G", Effect.NEGATIVE, ActionType.STOPFIRST, 0, false);
		firstLaneTest.addActionCardOnLane(felixInfernalis);
		
		assertTrue(this.firstLaneTest.hasStopFirstCard());
	}
	
	@Test
	public void hasOddsUpActionCardTest(){
		//metto "Alfio Allibratore" sulla lane
		ActionCard alfioAllibratoreTest = new ActionCard("Alfio Allibratore", 15, image, image, null, Effect.SPECIAL, ActionType.ODDSUP, 0, false);
		this.firstLaneTest.addActionCardOnLane(alfioAllibratoreTest);
		
		assertTrue(this.firstLaneTest.hasOddsUpActionCard());
	}
	
	@Test
	public void hasOddsDownActionCardTest(){
		//metto "Steven Sting" sulla lane
		ActionCard stevenStingTest = new ActionCard("Steven Sting", 19, image, image, null, Effect.SPECIAL, ActionType.ODDSDOWN, 0, false);
		this.firstLaneTest.addActionCardOnLane(stevenStingTest);
		
		assertTrue(this.firstLaneTest.hasOddsDownActionCard());
	}
	
	@Test
	public void checkIfIsFinishLineReachedTest(){
		this.firstLaneTest.setLanePawnPosition(12);
		this.firstLaneTest.checkIfIsFinishLineReached();
		assertTrue(this.firstLaneTest.isFinishLineReached());
	}
}
