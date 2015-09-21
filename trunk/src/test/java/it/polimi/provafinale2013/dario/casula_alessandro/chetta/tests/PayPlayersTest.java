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
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.junit.Test;

public class PayPlayersTest{
	
	Game gameTest = new Game();
	ArrayList<ArrayList<StableCard>> oddsSpacesTest = new ArrayList<ArrayList<StableCard>>();
	OddsBlackBoard oddsBlackBoardTest = new OddsBlackBoard(oddsSpacesTest);
	Race raceTest = new Race(oddsBlackBoardTest);
	ArrayList<Player> playersTest = new ArrayList<Player>();
	ArrayList<Bet> playerBetsTest = new ArrayList<Bet>();
	ArrayList<StableCard> rankingTest = new ArrayList<StableCard>();
	ImageIcon image = new ImageIcon("");

	@Test
	public void test() {
		//setto la race al game
		gameTest.setRace(raceTest);
		//setto la blacboard al game
		gameTest.setBlackBoard(oddsBlackBoardTest);
		//creo le carte scuderia
		StableCard firstStableCard = new StableCard("Die Swarz Mond", 58, image, Color.BLACK, null);
		StableCard secondStableCard = new StableCard("The Blue Blood", 56, image, Color.BLUE, null);
		StableCard thirdStableCard = new StableCard("La Gloire Vert", 60, image, Color.GREEN, null);
		StableCard fourthStableCard = new StableCard("El Fragor Rojo", 59, image, Color.RED, null);
		StableCard fifthStableCard = new StableCard("L'Ardor Giallo", 57, image, Color.YELLOW, null);
		StableCard sixthStableCard = new StableCard("Virtus Alba", 55, image, Color.WHITE, null);
		//creo le scommesse di ogni giocatore
		Bet firstBet = new Bet(300, true, firstStableCard, false);
		Bet secondBet = new Bet(300, false, thirdStableCard, false);
		Bet thirdBet = new Bet(300, true, sixthStableCard,false);
		Bet fourthBet = new Bet (300, true, firstStableCard,false);
		Bet fifthBet = new Bet (300, false, fifthStableCard,false);
		Bet sixthBet = new Bet (300, false, secondStableCard,false);
		//creo gli array delle scommesse
		ArrayList<Bet> firstBets = new ArrayList<Bet>();
		ArrayList<Bet> secondBets = new ArrayList<Bet>();
		ArrayList<Bet> thirdBets = new ArrayList<Bet>();
		ArrayList<Bet> fourthBets = new ArrayList<Bet>();
		ArrayList<Bet> fifthBets = new ArrayList<Bet>();
		ArrayList<Bet> sixthBets = new ArrayList<Bet>();
		//ogni giocatore fa una sola scommessa che aggiungo agli array
		firstBets.add(firstBet);
		secondBets.add(secondBet);
		thirdBets.add(thirdBet);
		fourthBets.add(fourthBet);
		fifthBets.add(fifthBet);
		sixthBets.add(sixthBet);
		//creo i giocatori
		Player firstPlayerTest = new Player("firstPlayer", firstStableCard, 400, null, 2, null, firstBets);
		Player secondPlayerTest = new Player("secondPlayer", secondStableCard, 400, null, 2, null, secondBets);
		Player thirdPlayerTest = new Player("thirdPlayer", thirdStableCard, 400, null, 2, null, thirdBets);
		Player fourthPlayerTest = new Player("fourthPlayer", fourthStableCard, 400, null, 2, null, fourthBets);
		Player fifthPlayerTest = new Player("fifthPlayer", fifthStableCard, 400, null, 2, null, fifthBets);
		Player sixthPlayerTest = new Player("sixthPlayer", sixthStableCard, 400, null, 2, null, sixthBets);
		//aggiungo i players a game		
		playersTest.add(firstPlayerTest);
		playersTest.add(secondPlayerTest);
		playersTest.add(thirdPlayerTest);
		playersTest.add(fourthPlayerTest);
		playersTest.add(fifthPlayerTest);
		playersTest.add(sixthPlayerTest);
		gameTest.addAllPlayers(playersTest);
		
		//creo le lane
		Lane firstLaneTest = new Lane(firstStableCard);
		Lane secondLaneTest = new Lane(secondStableCard);
		Lane thirdLaneTest = new Lane(thirdStableCard);
		Lane fourthLaneTest = new Lane(fourthStableCard);
		Lane fifthLaneTest = new Lane(fifthStableCard);
		Lane sixthLaneTest = new Lane(sixthStableCard);
		
		//metto tutte le lane nella race
		ArrayList<Lane> allLanesTest = new ArrayList<Lane>();
		allLanesTest.add(firstLaneTest);
		allLanesTest.add(secondLaneTest);
		allLanesTest.add(thirdLaneTest);
		allLanesTest.add(fourthLaneTest);
		allLanesTest.add(fifthLaneTest);
		allLanesTest.add(sixthLaneTest);
		raceTest.setAllLanes(allLanesTest);
		
		//creo gli array di scuderie in ogni quotazione
		ArrayList<StableCard> highOdds2 = new ArrayList<StableCard>();
		ArrayList<StableCard> highOdds3 = new ArrayList<StableCard>();
		ArrayList<StableCard> highOdds4 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds5 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds6 = new ArrayList<StableCard>();
		ArrayList<StableCard> lowOdds7 = new ArrayList<StableCard>();
		//riempio gli array di scuderie in ogni quotazione
		highOdds2.add(firstStableCard);
		highOdds2.add(secondStableCard);
		//highOdds3.add(null);
		highOdds4.add(fourthStableCard);
		highOdds4.add(sixthStableCard);
		lowOdds5.add(thirdStableCard);
		//lowOdds6.add(null);
		lowOdds7.add(fifthStableCard);
		//creo la blackboard con le quotazioni
		oddsSpacesTest.add(highOdds2);
		oddsSpacesTest.add(highOdds3);
		oddsSpacesTest.add(highOdds4);
		oddsSpacesTest.add(lowOdds5);
		oddsSpacesTest.add(lowOdds6);
		oddsSpacesTest.add(lowOdds7);
		//creo la classifica
		rankingTest.add(firstStableCard);
		rankingTest.add(fifthStableCard);
		rankingTest.add(thirdStableCard);
		rankingTest.add(secondStableCard);
		rankingTest.add(sixthStableCard);
		rankingTest.add(fourthStableCard);
		//metto la classifica nel gameTest
		raceTest.setRanking(rankingTest);
		//faccio partire il pagamento
		gameTest.payPlayers();
		//ATTENZIONE: il metodo dei pagamenti semplicemente paga i giocatori
		//non sottrae i soldi della scommessa, che viene fatto all'aggiunta
		//della scommessa stessa da un altro metodo, quindi per avere la reale
		//quantità di danari per ogni player bisogna sottrrarre 300 danari
		boolean condition = (
				firstPlayerTest.getPlayerDanariAmount() == 1600 &&
				firstPlayerTest.getPlayerVictoryPoints() == 5 &&
				
				secondPlayerTest.getPlayerDanariAmount() == 1000 &&
				secondPlayerTest.getPlayerVictoryPoints() == 3 &&
				
				thirdPlayerTest.getPlayerDanariAmount() == 600 &&
				thirdPlayerTest.getPlayerVictoryPoints() == 2 &&
				
				fourthPlayerTest.getPlayerDanariAmount() == 1000 &&
				fourthPlayerTest.getPlayerVictoryPoints() == 5 &&
				
				fifthPlayerTest.getPlayerDanariAmount() == 1400 &&
				fifthPlayerTest.getPlayerVictoryPoints() == 3 &&
				
				sixthPlayerTest.getPlayerDanariAmount() == 400 &&
				sixthPlayerTest.getPlayerVictoryPoints() == 2
		);
		assertTrue(condition);
	}
}
