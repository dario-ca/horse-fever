/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.rmi;


import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.Card;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI.GuiHorseFeverRMI;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.Bet;
import java.awt.Color;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ControllerHorseFeverRMI extends Remote{

	public static final Integer numberOfStables = 6;
	public static final Integer maxNumberOfPlayers = 6;
	public static final Integer minimumNumberOfPlayers = 2;
	public static final String serverError = "error occurred in the Remote Method Call";

	public void registerGui(GuiHorseFeverRMI guiRMI, String playerName) throws RemoteException;
	public void continueGame() throws RemoteException;
	public String getNameOfPlayer(Integer looserPlayerIndex) throws RemoteException;
	public String getCurrentPlayerMinimumBetAmount() throws RemoteException;
	public void addBet(Color stableColor, Integer betAmount, Boolean isBetToWin) throws RemoteException;
	public void addBet() throws RemoteException;
	public void addActionCardOnLane(ActionCard selectedActionCard, Color selectedColor) throws RemoteException;
	public List<ActionCard> getPlayerActionCards(GuiHorseFeverRMI guiRMI) throws RemoteException;
	public String getPlayerDanariAmount(GuiHorseFeverRMI guiRMI) throws RemoteException;
	public String getPlayerPV(GuiHorseFeverRMI guiRMI) throws RemoteException;
	public String getPlayerName(GuiHorseFeverRMI guiRMI) throws RemoteException;
	public Card getPlayerCharacterCard(GuiHorseFeverRMI guiRMI) throws RemoteException;
	public Card getPlayerStableCard(GuiHorseFeverRMI guiRMI) throws RemoteException;
	public List<Bet> getBetsOfThePlayer(Integer k) throws RemoteException;
	public Integer getNumberOfPlayers() throws RemoteException;
	public List<StableCard> getStablesCardAt(Integer i) throws RemoteException;
	public List<StableCard> getRanking() throws RemoteException;
	public List<Integer> getAllPawnPosition() throws RemoteException;
	public String getStableCardOfPlayer(Integer i) throws RemoteException;
	public String getDanariAmountOfPlayer(Integer i) throws RemoteException;
	public String getVictoryPointOfPlayer(Integer i) throws RemoteException;
	public List<ActionCard> getActionCardsOnLane(Integer i) throws RemoteException;


}
