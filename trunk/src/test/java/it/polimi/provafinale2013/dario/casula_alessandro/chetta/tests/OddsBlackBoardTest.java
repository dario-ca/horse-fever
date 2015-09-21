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
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.*;

import java.awt.Color;
import java.util.*;

import javax.swing.ImageIcon;


import org.junit.Before;
import org.junit.Test;

public class OddsBlackBoardTest {
	ArrayList<StableCard> lastRankingTest = new ArrayList<StableCard>();
	ArrayList<ArrayList<StableCard>> oddsSpacesTest = new ArrayList<ArrayList<StableCard>>();
	ArrayList<ArrayList<StableCard>> newOddsSpacesTest = new ArrayList<ArrayList<StableCard>>();
	OddsBlackBoard oddsBlackBoardTest = new OddsBlackBoard(oddsSpacesTest);
	ImageIcon image = new ImageIcon("");
	//creo le carte scuderia
	StableCard firstStableCard = new StableCard("Die Swarz Mond", 58, image, Color.BLACK, image);
	StableCard secondStableCard = new StableCard("The Blue Blood", 56, image, Color.BLUE, image);
	StableCard thirdStableCard = new StableCard("La Gloire Vert", 60, image, Color.GREEN, image);
	StableCard fourthStableCard = new StableCard("El Fragor Rojo", 59, image, Color.RED, image);
	StableCard fifthStableCard = new StableCard("L'Ardor Giallo", 57, image, Color.YELLOW, image);
	StableCard sixthStableCard = new StableCard("Virtus Alba", 55, image, Color.WHITE, image);
			
	
	@Before
	public void setup(){
		//creo la classifica
		lastRankingTest.add(fourthStableCard);
		lastRankingTest.add(thirdStableCard);
		lastRankingTest.add(firstStableCard);
		lastRankingTest.add(secondStableCard);
		lastRankingTest.add(sixthStableCard);
		lastRankingTest.add(fifthStableCard);
		//creo gli array di scuderie in ogni quotazione
		ArrayList<StableCard> highOdds2 = new ArrayList<StableCard>();
		ArrayList<StableCard> highOdds3 = new ArrayList<StableCard>();
		ArrayList<StableCard> highOdds4 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds5 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds6 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds7 = new ArrayList<StableCard>();
		//riempio gli array di scuderie in ogni quotazione
		highOdds3.add(fourthStableCard);
		highOdds3.add(sixthStableCard);
		highOdds4.add(firstStableCard);
		lowOdds5.add(secondStableCard);
		lowOdds5.add(fifthStableCard);
		lowOdds7.add(thirdStableCard);
		//creo la blackboard con le vecchie quotazioni
		oddsSpacesTest.add(highOdds2);
		oddsSpacesTest.add(highOdds3);
		oddsSpacesTest.add(highOdds4);
		oddsSpacesTest.add(lowOdds5);
		oddsSpacesTest.add(lowOdds6);
		oddsSpacesTest.add(lowOdds7);
		
		//creo la blackboard con le nuove quotazioni
		ArrayList<StableCard> newHighOdds2 = new ArrayList<StableCard>();
		ArrayList<StableCard> newHighOdds4 = new ArrayList<StableCard>();
		ArrayList<StableCard> newLowOdds5 = new ArrayList<StableCard>();
		ArrayList<StableCard> newLowOdds6 = new ArrayList<StableCard>();
		
		newHighOdds2.add(fourthStableCard);
		newHighOdds4.add(firstStableCard);
		newHighOdds4.add(sixthStableCard);
		newLowOdds5.add(secondStableCard);
		newLowOdds6.add(thirdStableCard);
		newLowOdds6.add(fifthStableCard);				
				
		newOddsSpacesTest.add(highOdds2);
		newOddsSpacesTest.add(highOdds3);
		newOddsSpacesTest.add(highOdds4);
		newOddsSpacesTest.add(lowOdds5);
		newOddsSpacesTest.add(lowOdds6);
		newOddsSpacesTest.add(lowOdds7);
	}
	
	@Test
	public void updateOddsBlackBoardTest() {

		oddsBlackBoardTest.updateOddsBlackBoard(lastRankingTest);
		
		boolean condition = true;
		for(int i=0;i<oddsBlackBoardTest.getOddsSpaces().size();i++){
			ArrayList<StableCard> tempStableOnOdds = oddsBlackBoardTest.getOddsSpaces().get(i);
			ArrayList<StableCard> tempNewStableOnOdds = newOddsSpacesTest.get(i);
			for(int j=0;j<tempStableOnOdds.size();j++){
				if(!(tempStableOnOdds.get(j).equals(tempNewStableOnOdds.get(j)))){
					condition=false;
				}
			}
		}
	assertTrue(condition);
	}
	
	@Test
	public void getOddsPositionOfTheStableTest() {
		assertTrue(this.oddsBlackBoardTest.getOddsPositionOfTheStable(thirdStableCard)==5);
	}
	
	@Test
	public void getStablesOnTest(){
		ArrayList<StableCard> stablesOnLane = this.oddsBlackBoardTest.getStablesOn(1);
		assertTrue(stablesOnLane.get(0).equals(fourthStableCard) && stablesOnLane.get(1).equals(sixthStableCard));
	}
	
	@Test
	public void oddsUpForTest(){
		this.oddsBlackBoardTest.oddsUpFor(firstStableCard);
		assertTrue(this.oddsBlackBoardTest.getOddsPositionOfTheStable(firstStableCard)==1);
	}
	
	@Test
	public void oddsDownForTest(){
		this.oddsBlackBoardTest.oddsDownFor(secondStableCard);
		assertTrue(this.oddsBlackBoardTest.getOddsPositionOfTheStable(secondStableCard)==4);
	}
	
}
