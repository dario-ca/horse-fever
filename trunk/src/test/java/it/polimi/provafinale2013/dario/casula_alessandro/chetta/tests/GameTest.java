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
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.*;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.exceptions.IllegalBetException;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.*;
import org.junit.Test;

public class GameTest {
	
	public Game gameTest = new Game();
	String newPlayerName = "newPlayer";
	
	@Test
	public void addNewPlayerTest(){
		this.gameTest.addNewPlayer(newPlayerName);
		//la partita ha un solo giocatore ed è quello aggiunto
		assertTrue((this.gameTest.getNumberOfPlayers()==1)&&(this.gameTest.getNameOfPlayer(0).equals(newPlayerName)));
	}
	
	@Test
	public void addBetOfTest(){
		this.gameTest.addNewPlayer(newPlayerName);
		try{
			this.gameTest.addBetOf(0, null, 500, true);
		}catch(IllegalBetException e){
			//non viene sollevata
		}
		//il giocatore ha una sola scommessa ed è quella appena aggiunta
		assertTrue((this.gameTest.getBetsOfPlayer(0).size()==1)&&(this.gameTest.getBetsOfPlayer(0).get(0).getBetAmount()==500)&&(this.gameTest.getBetsOfPlayer(0).get(0).getIsBetToWin()==true));
	}
	
	@Test
	public void decreaseVictoryPointsTest(){
		this.gameTest.addNewPlayer(newPlayerName);
		this.gameTest.decreaseVictoryPoints(0, 1);
		assertTrue(this.gameTest.getVictoryPointOfPlayer(0)==1);
	}
	
	@Test
	public void addActionCardOnLaneTest(){
		ImageIcon image = new ImageIcon("");
		//carta scuderia
		StableCard firstStableCard = new StableCard("Die Swarz Mond", 58, image, Color.BLACK, image);
		//creo la tabella quotazioni
		ArrayList<ArrayList<StableCard>> oddsSpacesTest = new ArrayList<ArrayList<StableCard>>();
		OddsBlackBoard oddsBlackBoardTest = new OddsBlackBoard(oddsSpacesTest);
		//creo la gara
		Race raceTest = new Race(oddsBlackBoardTest);
		this.gameTest.setRace(raceTest);
		//creo array delle lane
		ArrayList<Lane> allTheLanesTest = new ArrayList<Lane>();
		Lane firstLaneTest = new Lane(firstStableCard);
		allTheLanesTest.add(firstLaneTest);
		raceTest.setAllLanes(allTheLanesTest);		
		//creo la carta azione
		ActionCard alfioAllibratoreTest = new ActionCard("Alfio Allibratore", 15, image, image, null, Effect.SPECIAL, ActionType.ODDSUP, 0, false);
		//creo il giocatore
		ArrayList<Bet> playerBetsTest = new ArrayList<Bet>();
		ArrayList<ActionCard> actionCardsTest = new ArrayList<ActionCard>();
		Player playerTest = new Player("Name for Test", null, 500, null, 5, actionCardsTest, playerBetsTest);
		actionCardsTest.add(alfioAllibratoreTest);
		//impongo i giocatori
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(playerTest);
		this.gameTest.addAllPlayers(allPlayers);
		//metto "Alfio Allibratore" sulla lane
		this.gameTest.addActionCardOnLane(0, alfioAllibratoreTest, firstStableCard);
		//c'è solo la carta azione appena messa e il giocatore non ha più carte azione
		boolean condition = (
				this.gameTest.getActionCardsOnLane(0).size()==1 &&
				this.gameTest.getActionCardsOnLane(0).get(0).equals(alfioAllibratoreTest) &&
				playerTest.getPlayerActionCards().isEmpty()
				);
		assertTrue(condition);
	}
	
	@Test
	public void findLoosersTest(){
		Player looser = new Player("playerTest", null, 100, null, 2, null, null);
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(looser);
		this.gameTest.addAllPlayers(allPlayers);
		ArrayList<Integer> loosersIndexes = this.gameTest.findLoosers();
		//l'unico giocatore perdente è l'unico inserito
		boolean condition = (
				loosersIndexes.size()==1 &&
				loosersIndexes.get(0)==0
				);
		assertTrue(condition);
	}
	
	@Test
	public void removePlayerTest(){
		Player looser = new Player("playerTest", null, 100, null, 2, null, null);
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(looser);
		this.gameTest.addAllPlayers(allPlayers);
		ArrayList<Integer> loosersIndexes = this.gameTest.findLoosers();
		this.gameTest.removePlayer(loosersIndexes);
		//non ci sono più giocatori
		assertTrue(this.gameTest.getNumberOfPlayers()==0);
	}
}