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
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.exceptions.IllegalBetException;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.*;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

public class RaceTest {
	
	OddsBlackBoard oddsBlackBoardTest = new OddsBlackBoard();
	Race raceTest = new Race(oddsBlackBoardTest);
	ArrayList<Lane> allTheLanesTest = new ArrayList<Lane>();
	ImageIcon image = new ImageIcon("");

	//creo le carte scuderia
	StableCard firstStableCard = new StableCard("Die Swarz Mond", 58, image, Color.BLACK, image);
	StableCard secondStableCard = new StableCard("The Blue Blood", 56, image, Color.BLUE, image);
	StableCard thirdStableCard = new StableCard("La Gloire Vert", 60, image, Color.GREEN, image);
	StableCard fourthStableCard = new StableCard("El Fragor Rojo", 59, image, Color.RED, image);
	StableCard fifthStableCard = new StableCard("L'Ardor Giallo", 57, image, Color.YELLOW, image);
	StableCard sixthStableCard = new StableCard("Virtus Alba", 55, image, Color.WHITE, image);
	//creo le lane
	Lane firstLaneTest = new Lane(firstStableCard);
	Lane secondLaneTest = new Lane(secondStableCard);
	Lane thirdLaneTest = new Lane(thirdStableCard);
	Lane fourthLaneTest = new Lane(fourthStableCard);
	Lane fifthLaneTest = new Lane(fifthStableCard);
	Lane sixthLaneTest = new Lane(sixthStableCard);	
	@Before
	public void setup(){
		//impongo la posizione delle pedine sulle lane
		firstLaneTest.setLanePawnPosition(13);
		secondLaneTest.setLanePawnPosition(13);
		thirdLaneTest.setLanePawnPosition(16);
		fourthLaneTest.setLanePawnPosition(13);
		fifthLaneTest.setLanePawnPosition(15);
		sixthLaneTest.setLanePawnPosition(16);
		//metto tutte le lane nella race
		allTheLanesTest.add(firstLaneTest);
		allTheLanesTest.add(secondLaneTest);
		allTheLanesTest.add(thirdLaneTest);
		allTheLanesTest.add(fourthLaneTest);
		allTheLanesTest.add(fifthLaneTest);
		allTheLanesTest.add(sixthLaneTest);
		
		raceTest.setAllLanes(allTheLanesTest);
		//metto "Alfio Allibratore" sulla lane6
		ActionCard alfioAllibratoreTest = new ActionCard("Alfio Allibratore", 15, image, image, null, Effect.SPECIAL, ActionType.ODDSUP, 0, false);
		sixthLaneTest.addActionCardOnLane(alfioAllibratoreTest);
		
		//metto "Steven Sting" sulla lane5
		ActionCard stevenStingTest = new ActionCard("Steven Sting", 19, image, image, null, Effect.SPECIAL, ActionType.ODDSDOWN, 0, false);
		fifthLaneTest.addActionCardOnLane(stevenStingTest);
	}
	
	
	@Test
	public void isTheRoundOverTest(){
		assertFalse(this.raceTest.isTheRoundOver());
	}
	
	@Test
	public void isThereOnTheLanesAnOddsUpActionCardTest(){
		assertTrue(this.raceTest.isThereOnTheLanesAnOddsUpActionCard());
	}
	
	@Test
	public void isThereOnTheLanesAnOddsDownActionCardTest(){
		assertTrue(this.raceTest.isThereOnTheLanesAnOddsDownActionCard());
	}
	
	@Test
	public void getStableOfLanesWhichHasOddsUpActionCardTest(){
		StableCard stable = this.raceTest.getStableOfLanesWhichHasOddsUpActionCard();
		assertTrue(stable.equals(sixthStableCard));
	}
	
	@Test
	public void getStableOfLanesWhichHasOddsDownActionCardTest(){
		StableCard stable = this.raceTest.getStableOfLanesWhichHasOddsDownActionCard();
		assertTrue(stable.equals(fifthStableCard));
	}
	
	@Test
	public void addActionCardOnLaneTest(){
		ActionCard fortunaBenevolaTest = new ActionCard("Fortuna Benevola", 2, image, image, "B", Effect.POSITIVE, ActionType.START, 1, true);
		this.raceTest.addActionCardOnLane(fortunaBenevolaTest, firstStableCard);
		assertTrue(this.allTheLanesTest.get(0).getLaneActionCards().get(0).equals(fortunaBenevolaTest));
	}
	
	@Test
	public void getActionCardsOnLaneTest(){
		//metto "Alfio Allibratore" sulla lane6
		ActionCard alfioAllibratoreTest = new ActionCard("Alfio Allibratore", 15, image, image, null, Effect.SPECIAL, ActionType.ODDSUP, 0, false);
		sixthLaneTest.addActionCardOnLane(alfioAllibratoreTest);
		assertTrue(this.raceTest.getActionCardsOnLane(5).get(0).equals(alfioAllibratoreTest));
	}
	
	@Test
	public void getStableOfLaneTest(){
		assertTrue(this.raceTest.getStableOfLane(0).equals(firstStableCard));
	}
	
	@Test
	public void checkRemovePositiveOrNegativeActionCardsTest(){
		//metto fritz finden sulla Lane3
		ActionCard fritzFindenTest = new ActionCard("Fritz Finden", 16, image, image, null, Effect.SPECIAL, ActionType.REMOVENEGATIVE, 0, false);
		thirdLaneTest.addActionCardOnLane(fritzFindenTest);
		//metto una carta azione negativa sulla Lane3
		ActionCard serumMaleficumTest = new ActionCard("Serum Maleficum", 10, image, image, null, Effect.NEGATIVE, ActionType.SPRINT, 0, false);
		thirdLaneTest.addActionCardOnLane(serumMaleficumTest);
		this.raceTest.checkRemovePositiveOrNegativeActionCards();
		//sulla Lane3 rimane solo la carta fritz finden
		assertTrue((this.allTheLanesTest.get(2).getLaneActionCards().size()==1)&& this.allTheLanesTest.get(2).getLaneActionCards().get(0).equals(fritzFindenTest));
	}
	
	@Test
	public void removeSameLetterCardsFromAllTheLanesTest(){
		//metto due carte con la lettera "A" sulla Lane4
		ActionCard magnaVelocitasTest = new ActionCard("Magna Velocitas", 1, image, image, "A", Effect.POSITIVE, ActionType.START, 4, false);
		fourthLaneTest.addActionCardOnLane(magnaVelocitasTest);
		ActionCard globusObscurusTest = new ActionCard("Globus Obscurus", 8, image, image, "A", Effect.NEGATIVE, ActionType.START, 0, false);
		fourthLaneTest.addActionCardOnLane(globusObscurusTest);
		
		this.raceTest.removeSameLetterCardsFromAllTheLanes();
		assertTrue(this.allTheLanesTest.get(3).getLaneActionCards().isEmpty());
	}
	
	@Test
	public void getAllPawnPositionsTest(){
		//creo l'array che mi aspetto
		ArrayList<Integer> expectedLanePawnPositions = new ArrayList<Integer>();
		expectedLanePawnPositions.add(13);
		expectedLanePawnPositions.add(13);
		expectedLanePawnPositions.add(16);
		expectedLanePawnPositions.add(13);
		expectedLanePawnPositions.add(15);
		expectedLanePawnPositions.add(16);
		
		ArrayList<Integer> generatedLanePawnPositions = this.raceTest.getAllPawnPositions();
		boolean condition = true;
		for(int index=0; index<generatedLanePawnPositions.size();index++){
			if(generatedLanePawnPositions.get(index)!=expectedLanePawnPositions.get(index)){
				condition = false;
			}
		}
		assertTrue(condition);
	}
	
	@Test
	//test con scommessa valida
	public void manageBetTestLegal(){
		//scommessa già fatta sulla prima scuderia
		Bet previousBet = new Bet(300, true, firstStableCard, false);
		//scommesse del giocatore (una sola)
		ArrayList<Bet> allBetTest = new ArrayList<Bet>();
		allBetTest.add(previousBet);
		//giocatore
		Player playerTest = new Player("playerTest", firstStableCard, 400, null, 2, null, allBetTest);
		//creo nuova scommessa
		Bet generatedBet = new Bet(null, null, null, null);
		try{
			generatedBet = this.raceTest.manageBet(playerTest, firstStableCard, 300, false);
		}catch(IllegalBetException e){
		//non la gestisco perchè non viene sollevata
		}finally{
			boolean condition = (generatedBet.getIsBetToWin()==false && generatedBet.getBetAmount()==300 && generatedBet.getBetStable().equals(firstStableCard));
			assertTrue(condition);
		}
	}
	
	@Test
	//test con scommessa non valida
	public void manageBetTestIllegal(){
		//scommessa già fatta sulla prima scuderia
		Bet previousBet = new Bet(300, true, firstStableCard, false);
		//scommesse del giocatore (una sola)
		ArrayList<Bet> allBetTest = new ArrayList<Bet>();
		allBetTest.add(previousBet);
		//giocatore
		Player playerTest = new Player("playerTest", firstStableCard, 400, null, 2, null, allBetTest);
		//provo a creare nuova scommessa uguale alla precedente
		boolean condition = false;
		try{
			this.raceTest.manageBet(playerTest, firstStableCard, 300, true);
		}catch(IllegalBetException e){
			condition = true;
		}finally{
			assertTrue(condition);
		}
	}
	
	@Test
	public void peekFirstMovementCardTest(){
		MovementCard cardPeeked = this.raceTest.peekFirstMovementCard();
		assertTrue(cardPeeked instanceof MovementCard);
	}
	
	@Test
	public void peekNextMovementCardTest(){
		MovementCard cardPeeked = this.raceTest.peekNextMovementCard();
		assertTrue(cardPeeked instanceof MovementCard);
	}
	
	@Test
	public void sprintTest(){
		ArrayList<Integer> previousPositions = this.raceTest.getAllPawnPositions();
		ArrayList<Integer> resultDices = this.raceTest.sprint();
		ArrayList<Integer> newPositions = this.raceTest.getAllPawnPositions();
		boolean condition = false;
		for(Integer position: resultDices){
			if(previousPositions.get(position)+1==newPositions.get(position)){
				condition = true;
			}
		}
		assertTrue(condition);
	}
	
	
}
