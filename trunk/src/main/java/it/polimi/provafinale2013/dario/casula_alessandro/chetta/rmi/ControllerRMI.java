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
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.CharacterCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.MovementCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI.GuiHorseFeverRMI;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.*;
import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;


//E' il controllore nel modello MVC
public class ControllerRMI implements ControllerHorseFeverRMI{
	private GameState gameState;
	private Game logicModel;
	private TurnManager turnManager;
	

	
	//Punti di penalita' se il giocatore non ha soldi per scommettere
	private final Integer penality=2;
	public int numberOfRounds;
	//per la gestione remota dei clients
	private List<GuiHorseFeverRMI> clients = new CopyOnWriteArrayList<GuiHorseFeverRMI>();
	//dichiaro il log da usare per comunicare eventuali errori durante le fasi del gioco
	private static Logger logServer = Logger.getLogger("server logger");
	
	public ControllerRMI() throws RemoteException{
		this.logicModel=new Game();
	}
	
	public void registerGui(GuiHorseFeverRMI gui, String playerName) throws RemoteException{
		//se ci sono più di sei o sei utenti allora l'utente non può connettersi
		if(clients.size()>=ControllerHorseFeverRMI.maxNumberOfPlayers){
			throw new IllegalStateException();
		}
		clients.add(gui);
		addNewPlayer(playerName);
		this.gameState=GameState.TAKE_PLAYERS;
		if(clients.size()>1){
			broadcastButtonEnable(true);
		}
		
	}
	
	
	
	//chiamando questo metodo la view dice al controllore di iniziare a contare i turni
	//deve essere chiamato una volta che tutti i players sono stati presi
	//lancia una exccezione se il numero di giocatori e minore di 2 in questo caso elimino tutti i giocatori
	//e rilancio la finestra per prendere i nuovi giocatori
	public void setTurnManager(){
		//prendo il numero di giocatori
		if(this.logicModel.getNumberOfPlayers()<minimumNumberOfPlayers)
		{
			//riporto il modello logico allo stato iniziale
			this.logicModel = new Game();
			return;
		}
		//la prossima fase sara' quella di prendere le prime scommesse
		gameState = GameState.TAKE_FIRST_BETS;
		//chiedo al modello logico quanti giocatori ci sono
		Integer numberOfPlayers = logicModel.getNumberOfPlayers();
		//creo un mumero casuale per settarlo come primo giocatore
		//oggetto Random
		Random randomGenerator = new Random();
		//setto il seed con l'orario
		randomGenerator.setSeed(new Date().getTime());
		Integer firstPlayer = Math.abs(randomGenerator.nextInt()%numberOfPlayers);
		turnManager = new TurnManager(numberOfPlayers, firstPlayer, false);
		//setto il numero di turni da fare con questi giocatori
		this.numberOfRounds = numberOfRounds(numberOfPlayers);
		broadcastButtonEnable(false);
		broadcastUpdate();		
		continueGame();
	}
	
	public void continueGame(String error){

		switch (gameState) {
		case TAKE_PLAYERS:
			setTurnManager();
			break;
		case TAKE_FIRST_BETS:
			//se il giocatore non ha abbastanza soldi salta
			if(Integer.decode(getDanariAmountOfPlayer(turnManager.getCurrentPlayer()))<Integer.decode(getPlayerMinimumBetAmount(turnManager.getCurrentPlayer())))
				{
					logicModel.decreaseVictoryPoints(turnManager.getCurrentPlayer(), penality);
					addBet();
					return;
				}
			try {
				getCurrentClientGui().startBetsManagerPopUp(error, true);
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
			break;
		case ADD_ACTION_CARDS_FIRST_TIME:
			try {
				getCurrentClientGui().startActionCardManagerPopUp();
				broadcastUpdate();
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
			break;
		case ADD_ACTION_CARDS_SECOND_TIME:
			try {
				getCurrentClientGui().startActionCardManagerPopUp();
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
			break;
		case TAKE_SECOND_BETS:
			try {
				getCurrentClientGui().startBetsManagerPopUp(error, false);
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
			break;
		case REMOVE_SAME_LETTER_CARDS:
			removeSameLetterActionCards();
			break;
		case CHECK_ACTION_CARDS_BEFORE_RACE:
			checkActionCardsBeforeRace();
			break;
		case START_RACE:
			performStartRace();
			break;
		case IN_RACE:
			performRace();
			break;
		case SPRINT:
			sprint();
			break;
		case PAYMENT:
			checkBets();
			break;
		case UPDATE_ODDS_BLACK_BOARD:
			updateOBB();
			break;
		case REMOVE_LOOSERS:
			removeLoosers();
			break;	
		case START_NEXT_ROUND:
			prepareNextRound();
			break;	
		case SAY_THE_WINNER:
			sayTheWinner();
			break;
		case NEW_GAME:
			break;
		default:
			break;	
		}
	}
	
	public void continueGame(){
		continueGame("");
	}
	//metodi che forniscono info alla gui:
	//sul giocatore corrente
	public void addNewPlayer(String playerName){
		logicModel.addNewPlayer(playerName);
	}
	
	public List<ActionCard> getPlayerActionCards(GuiHorseFeverRMI guiRMI){
		Integer playerIndex = clients.indexOf(guiRMI);
		List<ActionCard> actionCards = Collections.unmodifiableList(logicModel.getActionCardsOfPlayer(playerIndex));
		return actionCards;
	}
	
	public CharacterCard getPlayerCharacterCard(GuiHorseFeverRMI guiRMI){
		Integer playerIndex = clients.indexOf(guiRMI);
		return logicModel.getCharacterCardOfPlayer(playerIndex);
	}
	
	public StableCard getPlayerStableCard(GuiHorseFeverRMI guiRMI){
		Integer playerIndex = clients.indexOf(guiRMI);
		return logicModel.getStableCardOfPlayer(playerIndex);
	}
	
	public ArrayList<Bet> getCurrentPlayerBets(){
		return logicModel.getBetsOfPlayer(turnManager.getCurrentPlayer());
	}
	
	public String getPlayerName(GuiHorseFeverRMI guiRMI){
		
		Integer playerIndex = clients.indexOf(guiRMI);
		return logicModel.getNameOfPlayer(playerIndex);
	}
	
	public String getPlayerDanariAmount(GuiHorseFeverRMI guiRMI){
		Integer playerIndex = clients.indexOf(guiRMI);
		return logicModel.getDanariAmountOfPlayer(playerIndex).toString();
	}
	
	public String getPlayerPV(GuiHorseFeverRMI guiRMI){
		Integer playerIndex = clients.indexOf(guiRMI);
		return logicModel.getVictoryPointOfPlayer(playerIndex).toString();
	}
	
	public String getPlayerMinimumBetAmount(Integer playerIndex){
		return logicModel.getMinimumBetAmountOfPlayer(playerIndex).toString();
	}
	
	//info generali sui giocatori
	public Integer getNumberOfPlayers(){
		return logicModel.getNumberOfPlayers();
	}
	
	public List<Bet> getBetsOfThePlayer(Integer playerIndex){
		List<Bet> playerBets = Collections.unmodifiableList(logicModel.getBetsOfPlayer(playerIndex));
		return playerBets;
	}
	
	public String getNameOfPlayer(Integer playerIndex){
	return logicModel.getNameOfPlayer(playerIndex);	
	}
	
	public String getDanariAmountOfPlayer(Integer playerIndex){
		return logicModel.getDanariAmountOfPlayer(playerIndex).toString();
	}
	
	public String getVictoryPointOfPlayer(Integer playerIndex){
		return logicModel.getVictoryPointOfPlayer(playerIndex).toString();
	}
	
	public String getStableCardOfPlayer(Integer playerIndex){
		return logicModel.getStableCardOfPlayer(playerIndex).getCardName();
	}
	
	//info sulla classifica delle quotazioni
	public List<StableCard> getStablesCardAt(Integer positionOnTheBlackBoard){
		List<StableCard> playerBets = Collections.unmodifiableList( logicModel.getStablesCardAt(positionOnTheBlackBoard));

		return playerBets;
	}
	
	//info sulla classifica d'arrivo
	public List<StableCard> getRanking(){
		List<StableCard> ranking = Collections.unmodifiableList(logicModel.getRanking());
		return ranking;
	}
	
	//info sulla gara
	public List<Integer> getAllPawnPosition(){
		List<Integer> allPawnPosition = Collections.unmodifiableList(logicModel.getAllPawnPositions());
		return allPawnPosition;
	}
	
	
	public List<ActionCard> getActionCardsOnLane(Integer laneIndex){
		List<ActionCard> actionCardsOnLane = Collections.unmodifiableList(logicModel.getActionCardsOnLane(laneIndex));
		return actionCardsOnLane;
	}
	//modificatori del modello
	
	//chiamata quando l'utente decide di non scommettere
	public void addBet(){
		if(turnManager.hasNext())
			{
			turnManager.goNext();
			}
		else{
				Integer firstPlayer = turnManager.getFirstPlayer();
				turnManager=new TurnManager(logicModel.getNumberOfPlayers(), firstPlayer, false);
				if(gameState.equals(GameState.TAKE_FIRST_BETS))
					{
						gameState=GameState.ADD_ACTION_CARDS_FIRST_TIME;
					}
				else{
						gameState=GameState.REMOVE_SAME_LETTER_CARDS;
					}
			}	
			
	
			broadcastUpdate();
		continueGame();
	}
	
	public void addBet(Color stableColor, Integer betDanariAmount, Boolean isBetToWin){

		StableCard stableChosenByThePlayer = CardGenerator.getStableCardOfThisColor(stableColor);
		try{
			logicModel.addBetOf(turnManager.getCurrentPlayer(), stableChosenByThePlayer, betDanariAmount, isBetToWin);
			}
		catch(Exception e){
			continueGame("Dati non corretti");
			return;
		}
		if(turnManager.hasNext())
			{
			turnManager.goNext();
			}
		else{
				Integer firstPlayer = turnManager.getFirstPlayer();
				turnManager=new TurnManager(logicModel.getNumberOfPlayers(), firstPlayer, false);
				if(gameState.equals(GameState.TAKE_FIRST_BETS))
					{
					gameState=GameState.ADD_ACTION_CARDS_FIRST_TIME;
					}
				else{
					gameState=GameState.REMOVE_SAME_LETTER_CARDS;
				}
			}
			
		
	
			broadcastUpdate();
		
		continueGame();
	}
	
	public void addActionCardOnLane(ActionCard actionCardChosenByThePlayer, Color stableColor){
		StableCard stableCardChosenByThePlayer = CardGenerator.getStableCardOfThisColor(stableColor);
		logicModel.addActionCardOnLane(turnManager.getCurrentPlayer(), actionCardChosenByThePlayer, stableCardChosenByThePlayer);
		if(turnManager.hasNext())
		{
			turnManager.goNext();
		}
		else{	
				Integer firstPlayer = turnManager.getFirstPlayer();
				if(gameState.equals(GameState.ADD_ACTION_CARDS_SECOND_TIME))
				{
					turnManager=new TurnManager(logicModel.getNumberOfPlayers(), firstPlayer, true);
					gameState=GameState.TAKE_SECOND_BETS;
				}
				if(gameState.equals(GameState.ADD_ACTION_CARDS_FIRST_TIME))
				{
					turnManager=new TurnManager(logicModel.getNumberOfPlayers(), firstPlayer, false);
					gameState=GameState.ADD_ACTION_CARDS_SECOND_TIME;
				}
			}
		
		broadcastUpdate();
		continueGame();
	}
	
	private void removeSameLetterActionCards(){
		logicModel.removeSameLetterCardsFromAllTheLanes();
		//setto il prossimo stato
		gameState=GameState.CHECK_ACTION_CARDS_BEFORE_RACE;
		broadcastUpdate();
		continueGame();
	}
	
	private void checkActionCardsBeforeRace(){
		logicModel.checkRemovePositiveOrNegativeActionCards();
		logicModel.checkOddsMovementMadeByActionCards();
		gameState=GameState.START_RACE;
		//rendo visibili le carte azione nella gui
		broadcastShowActionCards(true);
		try {
			getCurrentClientGui().controlButtonEnabled(true);
		} catch (RemoteException e) {
			logServer.severe(ControllerHorseFeverRMI.serverError);
		}
		broadcastUpdate();
	}
	
	private void performStartRace(){
		MovementCard movementCard= logicModel.peekFirstMovementCard();
		broadcastButtonName("SPRINT");
		broadcastMovementCard(movementCard);
	
		if(gameState.equals(GameState.START_RACE))
			{
				gameState=GameState.SPRINT;
			}
		broadcastButtonEnable(true);
		broadcastUpdate();
	}
	
	private void performRace(){
		MovementCard movementCard= logicModel.peekNextMovementCard();
		broadcastButtonName("SPRINT");
		broadcastMovementCard(movementCard);
		broadcastDices(null);
		gameState=GameState.SPRINT;
	
		if(logicModel.isTheRoundOver())
			{
				gameState=GameState.PAYMENT;
				broadcastButtonName("PAGAMENTI");
			}
		broadcastUpdate();

	}
	
	private void sprint(){
		ArrayList<Color> colorArray = logicModel.sprint();
		broadcastDices(colorArray);
		gameState=GameState.IN_RACE;
		broadcastButtonName("GO");

		if(logicModel.isTheRoundOver())
			{
				gameState=GameState.PAYMENT;
				broadcastButtonName("PAGAMENTI");
				logicModel.makeRanking();
			}

		broadcastUpdate();
	}

	
	private int numberOfRounds(int numberOfPlayers){
		switch (numberOfPlayers) {
		case 2:
			return 6;
		case 3:
			return 6;
		case 4:
			return 4;
		case 5:
			return 5;
		case 6:
			return 6;
		
		}
		return 0;
	}
	
	private void checkBets(){
		logicModel.payPlayers();
		gameState=GameState.UPDATE_ODDS_BLACK_BOARD;
		broadcastDices(null);
		broadcastButtonName("AGGIORNA");
		broadcastUpdate();
	}
	
	private void updateOBB(){
		logicModel.updateOddsBlackBoard();
		gameState=GameState.REMOVE_LOOSERS;
		broadcastButtonName("CONTINUA");
		broadcastMovementCard(null);
		broadcastUpdate();

	}
	
	private void removeLoosers(){
		//trovo il vincitore da usare nell'eventualità che tutti i player perdano
		String currentWinner = logicModel.chooseTheWinnerPlayer().getPlayerName();
		ArrayList<Integer> loosersPlayerIndexes = logicModel.findLoosers();
		if(!loosersPlayerIndexes.isEmpty())
		{
			broadcastComunicateLoosers(loosersPlayerIndexes);
		}
			logicModel.removePlayer(loosersPlayerIndexes);
			removeThisClients(loosersPlayerIndexes);
		//controllo se e' rimasto solo un giocatore, allora quello è il vincitore
		if(logicModel.getNumberOfPlayers()==1){
			gameState=GameState.SAY_THE_WINNER;
			return;
		}
		if(logicModel.getNumberOfPlayers()==0){
			broadcastComunicateWinner(currentWinner);
			gameState=GameState.NEW_GAME;
			return;
		}
		broadcastButtonName("START");
		broadcastButtonEnable(false);
		gameState=GameState.START_NEXT_ROUND;
		//setto il nuovo first player
		Integer firstPlayer = nextFirstPlayer();
		turnManager=new TurnManager(logicModel.getNumberOfPlayers(), firstPlayer, false);
		broadcastUpdate();
		continueGame();
	}
	
	private Integer nextFirstPlayer(){
		Integer currentFirstPlayer = turnManager.getFirstPlayer();
		if(currentFirstPlayer>=logicModel.getNumberOfPlayers()-1){
			return 0;
		}
		return currentFirstPlayer+1;
	}
	
	private void prepareNextRound(){
		broadcastShowActionCards(false);
		if(numberOfRounds>0)
		{
			gameState=GameState.TAKE_FIRST_BETS;
			logicModel.prepareNewRace();
			numberOfRounds--;
			broadcastUpdate();
			continueGame();
		}else{
			gameState=GameState.SAY_THE_WINNER;
			continueGame();
			}
	}
	
	private void sayTheWinner(){
		broadcastButtonEnable(false);
		String winner = logicModel.chooseTheWinnerPlayer().getPlayerName();
		broadcastComunicateWinner(winner);
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			logServer.severe(ControllerHorseFeverRMI.serverError);
		}

	}
	
	public static void main(String[] args) {
		 try {
			    // creo il server
			    ControllerRMI server = new ControllerRMI();
			    // esporto il server e creo lo stub
			    ControllerHorseFeverRMI serverStub = (ControllerHorseFeverRMI) UnicastRemoteObject.exportObject(server, 0);
			    // prendo un riferimento al registry
	            Registry registry = LocateRegistry.getRegistry();
	            // esporto lo stub del server nel registry
	            registry.rebind("HFServer", serverStub);
	        } catch (Exception e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
	        }
	}

	
	private GuiHorseFeverRMI getCurrentClientGui(){
		return clients.get(turnManager.getCurrentPlayer());
	}

	private void broadcastUpdate(){
		for(GuiHorseFeverRMI guiRMI: clients)
		{
			try {
				guiRMI.updateCurrentData();
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
		}
	}

	private void broadcastShowActionCards(Boolean value){
		for(GuiHorseFeverRMI guiRMI: clients)
		{
			try {
				guiRMI.setShowActionCards(value);
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
		}
	}
	
	private void broadcastButtonName(String name){
		for(GuiHorseFeverRMI guiRMI: clients)
		{
			try {
				guiRMI.setControlButtonName(name);
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
		}
	}
	
	private void broadcastMovementCard(MovementCard card){
		for(GuiHorseFeverRMI guiRMI: clients)
		{
			try {
				guiRMI.setMovementCard(card);
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
		}
	}
	
	private void broadcastButtonEnable(Boolean value){
		for(GuiHorseFeverRMI guiRMI: clients)
		{
			try {
				guiRMI.controlButtonEnabled(value);
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
		}
	}
	
	private void broadcastDices(ArrayList<Color> colors)
	{
		for(GuiHorseFeverRMI guiRMI: clients)
		{
		try {
			guiRMI.setDicesColor(colors);
		} catch (RemoteException e) {
			logServer.severe(ControllerHorseFeverRMI.serverError);
		}
		}
	}
	
	private void broadcastComunicateLoosers(ArrayList<Integer> loosersPlayerIndexes){
		for(GuiHorseFeverRMI guiRMI: clients)
		{
		try {
			guiRMI.startComunicateLoosersPopUp(loosersPlayerIndexes);
		} catch (RemoteException e) {
			logServer.severe(ControllerHorseFeverRMI.serverError);
		}
		}
	}
	
	private void broadcastComunicateWinner(String currentWinner){
		for(GuiHorseFeverRMI guiRMI: clients)
		{
		try {
			guiRMI.startComunicateTheWinner(currentWinner);
		} catch (RemoteException e) {
			logServer.severe(ControllerHorseFeverRMI.serverError);
		}
		}
	}

	public String getCurrentPlayerMinimumBetAmount() throws RemoteException {
		return logicModel.getMinimumBetAmountOfPlayer(turnManager.getCurrentPlayer()).toString();
	}

	private void removeThisClients(ArrayList<Integer> loosersPlayerIndexes){
		ArrayList<GuiHorseFeverRMI> clientsToRemove = new ArrayList<GuiHorseFeverRMI>();
		for(int clientIndexToRemove: loosersPlayerIndexes){
			clientsToRemove.add(clients.get(clientIndexToRemove));
		}
		//avviso i clients che la loro partita sta per finire
		for(GuiHorseFeverRMI client: clientsToRemove){
			try {
				client.startYouLoseFrame();
			} catch (RemoteException e) {
				logServer.severe(ControllerHorseFeverRMI.serverError);
			}
		}
		clients.removeAll(clientsToRemove);
	}
	
}
