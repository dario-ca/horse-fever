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
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.MovementCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui.HorseFeverGUI;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * La classe Controller e' una implementazione dell'interfaccia HorseFeverController:
 * rappresenta pertanto un tipo di controllore per la gestione del gioco Horse Fever.
 * Ha tra i suoi attributi l'interfaccia grafica che implementa l'interfaccia
 * HorseFeverGUI, il modello logico del gioco e un attributo che si occupa di mantenere
 * lo stato del gioco. 
 * @see HorseFeverController
 */

public class Controller implements HorseFeverController{
	//riferimento all'interfaccia grafica che dovra' implementare l'interfaccia HorseFeverGUI
	private HorseFeverGUI gui;
	//rappresenta lo stato del gioco
	private GameState gameState;
	//riferimento al modello logico (Core del gioco)
	private Game logicModel;
	//oggetto che amministra il flusso dei turni tra i vari giocatori
	private TurnManager turnManager;
	//variabile che viene impostata all'inizio del gioco in base al numero di giocatori
	//che indica il numero di rounds da fare
	public int numberOfRounds;
	
	/**
	 * Crea un'istanza di Controller
	 * @constructor
	 */
	public Controller(){
		this.logicModel=new Game();

	}
	
	/**
	 * consente di comunicare al controller la gui di riferimento
	 * @param gui oggetto che implementa HorseFeverGUI
	 */
	public void registerGui(HorseFeverGUI gui){
		this.gui=gui;
		this.gameState=GameState.TAKE_PLAYERS;
		continueGame();
	}
	
	/**
	 * comunica al controllore che la fase di iscrizione giocatori e' terminata
	 * quindi il controllore può iniziare a contare i turni
	 * @throws IllegalStateException lanciata se il numero dei giocatori e' inferiore a 2 in questo caso il modello logico viene resettato riassegnandogli una nuova istanza	 * 
	 */
	public void setTurnManager(){
		//prendo il numero di giocatori
		if(this.logicModel.getNumberOfPlayers()<minimumNumberOfPlayers)
		{
			//riporto il modello logico allo stato iniziale
			this.logicModel = new Game();
			throw new IllegalStateException();
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

		gui.setMainFrameVisible(true);
		gui.updateCurrentData();
		continueGame();
	}
	
	/**
	 * Rappresenta il core del controllore, gestisce il flusso del gioco, il metodo si occupa, a partire dallo stato del controllore, di decidere quale sarà la prossima fase
	 * del gioco.
	 * <p>Durante la prima scommessa, che è obbligatoria, se il giocatore non dispone dei danari sufficienti a 
	 * soddisfare l'ammontare di scommessa minima, gli vengono detratti <code>HorseFeverController.PENALITY</code> punti vittoria e non fa la scommessa
	 * @param error e' un eventuale errore che si vuole comunicare all'utente 
	 */
	public void continueGame(String error){
		switch (gameState) {
		case TAKE_PLAYERS:
			gui.startTakePlayers(error);
			break;
		case TAKE_FIRST_BETS:
			//se il giocatore non ha abbastanza soldi salta
			if(Integer.decode(getCurrentPlayerDanariAmount())<Integer.decode(getCurrentPlayerMinimumBetAmount()))
				{
					logicModel.decreaseVictoryPoints(turnManager.getCurrentPlayer(), penality);
					addBet();
					return;
				}
			gui.startBetsManagerPopUp(error, true);
			break;
		case ADD_ACTION_CARDS_FIRST_TIME:
			gui.startActionCardManagerPopUp();
			break;
		case ADD_ACTION_CARDS_SECOND_TIME:
			gui.updateCurrentData();
			gui.startActionCardManagerPopUp();
			break;
		case TAKE_SECOND_BETS:
			gui.startBetsManagerPopUp(error, false);
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
			startNewGame();
			break;	
		}
	}
	/**
	 * Gestisce il flusso del gioco 
	 * Overloading di <code>continueGame(String error)</code> usare nel caso in cui non ci sia un errore da comunicare 
	 */
	public void continueGame(){
		continueGame("");
	}
	//metodi che forniscono info alla gui:
	//sul giocatore corrente
	public void addNewPlayer(String playerName){
		logicModel.addNewPlayer(playerName);
	}
	
	/**
	 *  @return l'insieme delle carte azione possedute dall'utente in turno
	 */
	public ArrayList<ActionCard> getCurrentPlayerActionCards(){
		return logicModel.getActionCardsOfPlayer(turnManager.getCurrentPlayer());
	}
	
	/**
	 * @return la carta personaggio dell'utente in turno
	 */
	public CharacterCard getCurrentPlayerCharacterCard(){
		return logicModel.getCharacterCardOfPlayer(turnManager.getCurrentPlayer());
	}
	
	/**
	 * @return la carta scuderia dell'utente in turno
	 */
	public StableCard getCurrentPlayerStableCard(){
		return logicModel.getStableCardOfPlayer(turnManager.getCurrentPlayer());
	}
	
	/**
	 * 
	 * @return le scommesse dell'utente in turno
	 */
	public ArrayList<Bet> getCurrentPlayerBets(){
		return logicModel.getBetsOfPlayer(turnManager.getCurrentPlayer());
	}
	
	/**
	 * 
	 * @return il nome dell'utente in turno
	 */
	public String getCurrentPlayerName(){
		return logicModel.getNameOfPlayer(turnManager.getCurrentPlayer());
	}
	
	/**
	 * @return i danari dell'utente in turno
	 */
	public String getCurrentPlayerDanariAmount(){
		return logicModel.getDanariAmountOfPlayer(turnManager.getCurrentPlayer()).toString();
	}
	
	/**
	 *@return i punti vittoria dell'utente in turno
	 */
	public String getCurrentPlayerPV(){
		return logicModel.getVictoryPointOfPlayer(turnManager.getCurrentPlayer()).toString();
	}
	
	/**
	 * @return l'ammontare minimo della scommessa dell'utente in turno
	 */
	public String getCurrentPlayerMinimumBetAmount(){
		return logicModel.getMinimumBetAmountOfPlayer(turnManager.getCurrentPlayer()).toString();
	}
	
	//-----------------------------------------------------info generali sui giocatori
	/**
	 * @return il numero complessivo di giocatori, non conta gli utenti gia' eliminati
	 */
	public Integer getNumberOfPlayers(){
		return logicModel.getNumberOfPlayers();
	}
	
	/**
	 * @param indice del giocatore
	 * @return le scommesse dell'utente indicato
	 */
	public ArrayList<Bet> getBetsOfThePlayer(Integer playerIndex){
		return logicModel.getBetsOfPlayer(playerIndex);
	}
	
	/**
	 * @param indice del giocatore
	 * @return nome del giocatore
	 */
	public String getNameOfPlayer(Integer playerIndex){
	return logicModel.getNameOfPlayer(playerIndex);	
	}
	
	/**
	 * @param indice del giocatore
	 * @return i danari del giocatore
	 */
	public String getDanariAmountOfPlayer(Integer playerIndex){
		return logicModel.getDanariAmountOfPlayer(playerIndex).toString();
	}
	
	/**
	 * @param indice del giocatore
	 * @return i punti vittoria del giocatore
	 */
	public String getVictoryPointOfPlayer(Integer playerIndex){
		return logicModel.getVictoryPointOfPlayer(playerIndex).toString();
	}

	/**
	 * @param indice del giocatore
	 * @return la carta scuderia del giocatore
	 */
	public String getStableCardOfPlayer(Integer playerIndex){
		return logicModel.getStableCardOfPlayer(playerIndex).getCardName();
	}
	
	/**
	 * informazioni sulla classifica
	 * @param indice della graduatoria delle quotazioni
	 * @return l'insieme delle carte scuderia che si trovano alla posizione espressa nell'argomento
	 */
	//info sulla classifica delle quotazioni
	public ArrayList<StableCard> getStablesCardAt(Integer positionOnTheBlackBoard){
		return logicModel.getStablesCardAt(positionOnTheBlackBoard);
	}
	
	/**
	 * info sulla classifica d'arrivo
	 * @return la classifica
	 */
	public ArrayList<StableCard> getRanking(){
		return logicModel.getRanking();
	}
	
	/**
	 * info sulla gara
	 * @return la posizione di ogni cavallo sulla relativa corsia
	 */
	public ArrayList<Integer> getAllPawnPosition(){
		return logicModel.getAllPawnPositions();
	}
	
	/**
	 * @param indice della corsia
	 * @return l'insieme delle carte azione posate sulla corsia
	 */
	public ArrayList<ActionCard> getActionCardsOnLane(Integer laneIndex){
		return logicModel.getActionCardsOnLane(laneIndex);
	}
	//--------------------------------------------------------modificatori del modello
	
	/**
	 * da usare quando l'utente decide di non scommettere
	 * <p>e' un overloading di <code>addBet(Color stableColor, Integer betDanariAmount, Boolean isBetToWin)</code>
	 */
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
			
		
		gui.updateCurrentData();
		continueGame();
	}
	
	/**
	 * aggiunge una scommessa all'utente di turno 
	 * @param colore della scuderia
	 * @param ammontare della scommessa
	 * @param booleano se è una scommessa vincente oppure no (vincente<code> true</code> | piazzato <code>false</code> )
	 */
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
			
		
		gui.updateCurrentData();
		continueGame();
	}
	/**
	 * aggiunge una carta azione su una lane
	 * @param carta azione da posizionare
	 * @param colore della corsia dove posizionare la carta azione
	 * 
	 */
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
		
		gui.updateCurrentData();
		continueGame();
	}
	
	/**
	 * rimuove le carte azione con stessa lettera sulla stessa corsia. Viene fatto per ogni corsia
	 */
	private void removeSameLetterActionCards(){
		logicModel.removeSameLetterCardsFromAllTheLanes();
		//setto il prossimo stato
		gameState=GameState.CHECK_ACTION_CARDS_BEFORE_RACE;
		gui.controlButtonEnabled(true);
		gui.updateCurrentData();
		
		continueGame();
	}
	
	/**
	 * controlla se ci sono carte azione che hanno effetto prima dell'inizio della gara
	 */
	private void checkActionCardsBeforeRace(){
		logicModel.checkRemovePositiveOrNegativeActionCards();
		logicModel.checkOddsMovementMadeByActionCards();
		//rendo visibili le carte azione nella gui
		gui.setShowActionCards(true);
		gameState=GameState.START_RACE;
		gui.updateCurrentData();
	}
	
	/**
	 * inizia la gara facendo quindi agire le carte azione che hanno effetto in questa fase
	 * <p>Viene estratta una nuova carta movimento che viene comunicata all'interfaccia grafica
	 */
	private void performStartRace(){
		MovementCard movementCard= logicModel.peekFirstMovementCard();
		gui.setControlButtonName("SPRINT");
		gui.setMovementCard(movementCard);
		if(gameState.equals(GameState.START_RACE))
			{
			gameState=GameState.SPRINT;
			}
		gui.updateCurrentData();
	}
	
	/**
	 * Continua la gara. <p>Viene estratta una nuova carta movimento che viene comunicata all'interfaccia grafica
	 */
	private void performRace(){
		MovementCard movementCard= logicModel.peekNextMovementCard();
		gameState=GameState.SPRINT;
		gui.setDicesColor(null);
		gui.setControlButtonName("SPRINT");
		gui.setMovementCard(movementCard);
		if(logicModel.isTheRoundOver())
			{
				gameState=GameState.PAYMENT;
				gui.setControlButtonName("PAGAMENTI");
			}
		gui.updateCurrentData();
	}
	
	/**
	 * Innesca lo sprint e genera due colori che vengono comunicati alla interfaccia grafica
	 */
	private void sprint(){
		ArrayList<Color> colorArray = logicModel.sprint();
		gui.setDicesColor(colorArray);
		gameState=GameState.IN_RACE;
		gui.setControlButtonName("GO");
		if(logicModel.isTheRoundOver())
			{
				gameState=GameState.PAYMENT;
				gui.setControlButtonName("PAGAMENTI");
				logicModel.makeRanking();
			}
		gui.updateCurrentData();
	}

	/**
	 * @param numberOfPlayers
	 * @return numero di raund da fare durante questa partita
	 */
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
	
	/**
	 * Controlla l'esito di tutte le scommesse di tutti i giocatori pagando quelle vinte
	 */
	private void checkBets(){
		logicModel.payPlayers();
		gameState=GameState.UPDATE_ODDS_BLACK_BOARD;
		gui.setDicesColor(null);
		gui.setControlButtonName("AGGIORNA");
		gui.updateCurrentData();
	}
	
	/**
	 * comunica al modello logico di apportare modifiche riguardo alla posizione delle 
	 * scuderie in graduatoria, in particolare confrontera' per ogni scuderia la classifica di arrivo
	 * con la loro posizione in classifica ed eventualmente apporterà delle modifiche alla loro quotazione
	 */
	private void updateOBB(){
		logicModel.updateOddsBlackBoard();
		gameState=GameState.REMOVE_LOOSERS;
		gui.setControlButtonName("CONTINUA");
		gui.setMovementCard(null);
		gui.updateCurrentData();
	}
	
	/**
	 * rimuove i giocatori che hanno perso. Nel caso in cui tutti i giocatori perodono o ne rimanga
	 * solo uno, il metodo estrae prima di eliminare i giocatori il potenziale vincitore della partita
	 */
	private void removeLoosers(){
		//trovo il vincitore da usare nell'eventualità che tutti i player perdano
		String currentWinner = logicModel.chooseTheWinnerPlayer().getPlayerName();
		ArrayList<Integer> loosersPlayerIndexes = logicModel.findLoosers();
		if(!loosersPlayerIndexes.isEmpty())
		{
			gui.startComunicateLoosersPopUp(loosersPlayerIndexes);
		}
	
			logicModel.removePlayer(loosersPlayerIndexes);
	
		//controllo se e' rimasto solo un giocatore, allora quello è il vincitore
		if(logicModel.getNumberOfPlayers()==1){
			gameState=GameState.SAY_THE_WINNER;
			return;
		}
		if(logicModel.getNumberOfPlayers()==0){
			gui.startComunicateTheWinner(currentWinner);
			gameState=GameState.NEW_GAME;
			return;
		}
		gui.setControlButtonName("START");
		gui.controlButtonEnabled(false);
		gameState=GameState.START_NEXT_ROUND;
		//setto il nuovo first player
		Integer firstPlayer = nextFirstPlayer();
		turnManager=new TurnManager(logicModel.getNumberOfPlayers(), firstPlayer, false);
		gui.updateCurrentData();
		continueGame();
	}
	
	/**
	 * 
	 * @return il primo giocatore
	 */
	private Integer nextFirstPlayer(){
		Integer currentFirstPlayer = turnManager.getFirstPlayer();
		if(currentFirstPlayer>=logicModel.getNumberOfPlayers()-1){
			return 0;
		}
		return currentFirstPlayer+1;
	}
	
	/**
	 * prepara il contesto per un nuovo round. 
	 */
	private void prepareNextRound(){
		gui.setShowActionCards(false);
		if(numberOfRounds>0)
		{
			gameState=GameState.TAKE_FIRST_BETS;
			logicModel.prepareNewRace();
			numberOfRounds--;
			gui.updateCurrentData();
			continueGame();
		}else{
			gameState=GameState.SAY_THE_WINNER;
			continueGame();
		}
	}
	
	/**
	 * si occupa di comunicare ai giocatori chi e' il vincitore
	 */
	private void sayTheWinner(){
		gameState=GameState.NEW_GAME;
		gui.setControlButtonName("NUOVO GIOCO");
		gui.controlButtonEnabled(true);
		String winner = logicModel.chooseTheWinnerPlayer().getPlayerName();
		gui.startComunicateTheWinner(winner);
	}
	
	/**
	 * prepara il contesto per un nuovo gioco
	 */
	private void startNewGame(){
		gui.disposeFrame();		
		
		this.logicModel=new Game();
		gui.setControlButtonName("START");
		gui.controlButtonEnabled(false);
		this.gameState=GameState.TAKE_PLAYERS;
		continueGame();
		
	}
}
