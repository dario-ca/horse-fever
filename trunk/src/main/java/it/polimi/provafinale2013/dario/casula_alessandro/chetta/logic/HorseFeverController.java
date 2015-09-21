/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.CharacterCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui.HorseFeverGUI;

import java.awt.Color;
import java.util.ArrayList;

public interface HorseFeverController {

	int maxNumberOfPlayers = 6;
	int numberOfStables = 6;
	public final Integer penality=2;
	public static final Integer finishLine = 12;
	public static final Integer minimumNumberOfPlayers = 2;
	
	public void registerGui(HorseFeverGUI gui);
	public void addNewPlayer(String text);
	public void setTurnManager();
	public void continueGame(String string);
	public String getCurrentPlayerName();
	public String getCurrentPlayerMinimumBetAmount();
	public void addBet();
	public void addBet(Color stableColor, Integer betAmount, Boolean isBetToWin);
	public ArrayList<ActionCard> getCurrentPlayerActionCards();
	public void addActionCardOnLane(ActionCard selectedActionCard, Color selectedColor);
	public String getNameOfPlayer(Integer looserPlayerIndex);
	public void continueGame();
	public CharacterCard getCurrentPlayerCharacterCard();
	public StableCard getCurrentPlayerStableCard();
	public Integer getNumberOfPlayers();
	public ArrayList<Bet> getBetsOfThePlayer(Integer k);
	public String getCurrentPlayerDanariAmount();
	public String getCurrentPlayerPV();
	public ArrayList<StableCard> getStablesCardAt(Integer i);
	public ArrayList<StableCard> getRanking();
	public ArrayList<Integer> getAllPawnPosition();
	public String getDanariAmountOfPlayer(Integer i);
	public String getStableCardOfPlayer(Integer i);
	public String getVictoryPointOfPlayer(Integer i);
	public ArrayList<ActionCard> getActionCardsOnLane(Integer i);

}
