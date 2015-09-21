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
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.Lane;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.OddsBlackBoard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.Race;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.junit.Test;

public class UpdateRankingTest {
	
	ArrayList<ArrayList<StableCard>> oddsSpacesTest = new ArrayList<ArrayList<StableCard>>();
	OddsBlackBoard oddsBlackBoardTest = new OddsBlackBoard(oddsSpacesTest);
	Race raceTest = new Race(oddsBlackBoardTest);
	ImageIcon image = new ImageIcon("");
	ArrayList<StableCard> expectedRankingTest = new ArrayList<StableCard>();

	@Test
	public void test() {
		//creo gli array di scuderie in ogni quotazione
		ArrayList<StableCard> highOdds2 = new ArrayList<StableCard>();
		ArrayList<StableCard> highOdds3 = new ArrayList<StableCard>();
		ArrayList<StableCard> highOdds4 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds5 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds6 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds7 = new ArrayList<StableCard>();
		//creo le carte scuderia della stable card che prende anche l'immagine del segnalino
		StableCard firstStableCard = new StableCard("Die Swarz Mond", 58, image, Color.BLACK, image);
		StableCard secondStableCard = new StableCard("The Blue Blood", 56, image, Color.BLUE, image);
		StableCard thirdStableCard = new StableCard("La Gloire Vert", 60, image, Color.GREEN, image);
		StableCard fourthStableCard = new StableCard("El Fragor Rojo", 59, image, Color.RED, image);
		StableCard fifthStableCard = new StableCard("L'Ardor Giallo", 57, image, Color.YELLOW, image);
		StableCard sixthStableCard = new StableCard("Virtus Alba", 55, image, Color.WHITE, image);
		//riempio gli array di scuderie in ogni quotazione
		highOdds2.add(secondStableCard);
		highOdds3.add(firstStableCard);
		highOdds4.add(thirdStableCard);
		highOdds4.add(fourthStableCard);
		lowOdds5.add(sixthStableCard);
		lowOdds7.add(fifthStableCard);
		//creo la blackboard con le quotazioni
		oddsSpacesTest.add(highOdds2);
		oddsSpacesTest.add(highOdds3);
		oddsSpacesTest.add(highOdds4);
		oddsSpacesTest.add(lowOdds5);
		oddsSpacesTest.add(lowOdds6);
		oddsSpacesTest.add(lowOdds7);
		//creo le lane
		Lane firstLaneTest = new Lane(firstStableCard);
		Lane secondLaneTest = new Lane(secondStableCard);
		Lane thirdLaneTest = new Lane(thirdStableCard);
		Lane fourthLaneTest = new Lane(fourthStableCard);
		Lane fifthLaneTest = new Lane(fifthStableCard);
		Lane sixthLaneTest = new Lane(sixthStableCard);
		//metto "In Igni Veritas" sulla lane6
		ActionCard inIgniVeritasTest = new ActionCard("In Igni Veritas", 5, image, image, null, Effect.POSITIVE, ActionType.PHOTOFINISH, 0, false);
		sixthLaneTest.addActionCardOnLane(inIgniVeritasTest);
		//metto "Mala Tempora" sulla lane2
		ActionCard malaTemporaTest = new ActionCard("Mala Tempora", 12, image, image, null, Effect.NEGATIVE, ActionType.PHOTOFINISH, 0, false);
		secondLaneTest.addActionCardOnLane(malaTemporaTest);
		//impongo la posizione delle pedine sulle lane
		firstLaneTest.setLanePawnPosition(13);
		secondLaneTest.setLanePawnPosition(13);
		thirdLaneTest.setLanePawnPosition(16);
		fourthLaneTest.setLanePawnPosition(13);
		fifthLaneTest.setLanePawnPosition(15);
		sixthLaneTest.setLanePawnPosition(16);
		//metto tutte le lane nella race
		ArrayList<Lane> allLanesTest = new ArrayList<Lane>();
		allLanesTest.add(firstLaneTest);
		allLanesTest.add(secondLaneTest);
		allLanesTest.add(thirdLaneTest);
		allLanesTest.add(fourthLaneTest);
		allLanesTest.add(fifthLaneTest);
		allLanesTest.add(sixthLaneTest);
		raceTest.setAllLanes(allLanesTest);
		//creo la classifica che mi aspetto
		expectedRankingTest.add(sixthStableCard);
		expectedRankingTest.add(thirdStableCard);
		expectedRankingTest.add(fifthStableCard);
		expectedRankingTest.add(firstStableCard);
		expectedRankingTest.add(fourthStableCard);
		expectedRankingTest.add(secondStableCard);
		//genero la classifica con il metodo da testare
		for(Lane lane: allLanesTest){
			lane.checkIfIsFinishLineReached();
		}
		raceTest.updateRanking();
		ArrayList<StableCard> generatedRanking = raceTest.getRanking();
		//controllo se la classifica generata è uguale a quella che mi aspetto
		boolean condition = true;
		for(int index=0; index<expectedRankingTest.size(); index++){
				if(!(generatedRanking.get(index).equals(expectedRankingTest.get(index)))){
					condition = false;
				}
		}
		assertTrue(condition);
	}
}