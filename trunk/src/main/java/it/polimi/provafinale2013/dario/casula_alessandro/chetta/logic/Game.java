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
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.exceptions.IllegalBetException;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Logger;

/**
 * La classe <code>Game</code> contiene i metodi per l'organizzazione dell'intera partita.
 * E' la classe del <code>Model</code> con la quale si interfaccia il <code>Controller</code>.
 * <code>Game</code> viene creata una volta soltanto e contiene al suo interno 
 * attributi che riassumono lo stato dell'intera partita:
 * contiene tutti i giocatori, tutti i mazzi di carte,
 * la tabella delle quotazioni e un oggetto della classe <code>Race</code>, che gestisce la fase di corsa.
 */
public class Game {
	private ArrayList<Player> players;
	private Queue<CharacterCard> characterCardDeck;
	private ArrayList<ActionCard> actionCardDeckArrayList;
	private Queue<ActionCard> actionCardDeck;
	private Race race;
	private OddsBlackBoard oddsBlackBoard;
	private static final Integer WINNER=0;
	private static final Integer SECOND=1;
	private static final Integer THIRD=2;
	//dichiaro il log da usare per comunicare eventuali errori durante le fasi del gioco
	public static Logger logicLogger = Logger.getLogger("logic logger");
	/**
	 * Costruttore di oggetti di tipo Game
	 * <p>crea anche il mazzo di carte azione e il mazzo di carte personaggio
	 */
	public Game(){
		//genero il mazzo carte azione
		actionCardDeckArrayList=CardGenerator.generateActionCardDeck();
		setActionCardQueueFromArrayList(actionCardDeckArrayList);
		//genero il mazzo carte personaggio
		setCharacterCardQueueFromArrayList(CardGenerator.generateCharacterCardDeck());
		//assegno un array vuoto di player a players
		players=new ArrayList<Player>();
		//creo la oddsBlackBoard il cui costruttore mescolerà le scuderie sulla obb
		oddsBlackBoard=new OddsBlackBoard();
		//creo l'oggetto che implementa la gara
		race=new Race(oddsBlackBoard.clone());
	}
	
	/**
	 * Crea il mazzo delle carte personaggio
	 * <p>il mazzo è strutturato in una coda
	 * @param characterCardArrayList tutte le carte personaggio
	 */
	private void setCharacterCardQueueFromArrayList(ArrayList<CharacterCard> characterCardArrayList){
		characterCardDeck=new LinkedList<CharacterCard>();
		
		Collections.shuffle(characterCardArrayList);
		for(int i=0; i<characterCardArrayList.size(); i++){
			this.characterCardDeck.add(characterCardArrayList.get(i));
		}
	}
	
	/**
	 * Crea il mazzo delle carte azione
	 * <p>il mazzo è strutturato in una coda
	 * @param characterCardArrayList tutte le carte azione
	 */
	private void setActionCardQueueFromArrayList(ArrayList<ActionCard> actionCardArrayList){
		actionCardDeck=new LinkedList<ActionCard>();
		
		Collections.shuffle(actionCardArrayList);
		for(int i=0; i<actionCardArrayList.size(); i++){
			this.actionCardDeck.add(actionCardArrayList.get(i));
		}
	}
	
	/**
	 * Imposta la tabella delle quotazioni
	 * @param blackBoardPassed tabella da impostare
	 */
	public void setBlackBoard(OddsBlackBoard blackBoardPassed){
		this.oddsBlackBoard = blackBoardPassed;
	}
	
	/**
	 * Imposta la Race
	 * @param racePassed Race da impostare
	 */
	public void setRace(Race racePassed){
		this.race=racePassed;
	}
	
	/**
	 * Imposta l'array di tutti i giocatori
	 * @param allPlayers array di giocatori da impostare
	 */
	public void addAllPlayers(ArrayList<Player> allPlayers){
		this.players = allPlayers;
	}
	
	/**
	 * Aggiunge un nuovo giocatore alla partita
	 * @param playerName nome del giocatore da aggiungere
	 */
	public void addNewPlayer(String playerName){
		//do a sorte il personaggio
		CharacterCard newPlayerCharacterCard = characterCardDeck.poll();
		
		//il personaggio precedentemente assegnato contiene il numero di scuderia che possiede
		//lo uso. All'inizio nella oddsspace abbiamo una sola scuderia per riga pertanto posso usare
		//un indice 0 per prendere la scuderia relativa a quella indicata dalla carta personaggio
		StableCard newPlayerStableCard = oddsBlackBoard.getStablesOn(newPlayerCharacterCard.getStableOdds()-2).get(0);
		
		ArrayList<ActionCard> newPlayerActionCards = giveTwoActionCards();
		
		Player newPlayer = new Player(playerName, newPlayerStableCard, newPlayerCharacterCard, 2, newPlayerActionCards);
		
		players.add(newPlayer);
	}
	
	/**
	 * Restituisce due carte azione dal mazzo delle carte azione
	 * <p>le carte restituite vengono eliminate dal mazzo
	 * @return ArrayList<ActionCard> con due carte azione
	 */
	//possiamo trovare un metodo più professionale per farlo a me è venuto in mente questo
	//il concetto è, faccio lo shuffle una volta e poi do le carte sequenzialmente però
	//mi serve qualcosa per tener traccia delle carte che ho già dato, quindi uso un indice
	//avevo pensato ad un hashmap ma mi sembra eccessivo
	private ArrayList<ActionCard> giveTwoActionCards(){
		ArrayList<ActionCard> twoActionCards = new ArrayList<ActionCard>();
		twoActionCards.add(actionCardDeck.poll());
		twoActionCards.add(actionCardDeck.poll());

		return twoActionCards;
	}
	
	//---------------------------------------------metodi di interfaccia
	//questi metodi serviranno alla view per poter mostrare all'utente in che condizioni è	
	
	
	/**
	 * Restituisce il nome del giocatore alla posizione <code>playerIndex</code>
	 * @param playerIndex indice del giocatore nell'array di tutti i giocatori
	 * @return il giocatore
	 */
	public String getNameOfPlayer(Integer playerIndex){
		return players.get(playerIndex).getPlayerName();
	}
	
	/**
	 * Restituisce i punti vittoria del giocatore alla posizione <code>playerIndex</code>
	 * @param playerIndex indice del giocatore nell'array di tutti i giocatori
	 * @return punti vittoria del giocatore
	 */
	public Integer getVictoryPointOfPlayer(Integer playerIndex){
		return players.get(playerIndex).getPlayerVictoryPoints();
	}
	
	/**
	 * Restituisce i danari del giocatore alla posizione <code>playerIndex</code>
	 * @param playerIndex indice del giocatore nell'array di tutti i giocatori
	 * @return danari del giocatore
	 */
	public Integer getDanariAmountOfPlayer(Integer playerIndex){
		return players.get(playerIndex).getPlayerDanariAmount();
	}
	
	/**
	 * Restituisce le carte azione del giocatore alla posizione <code>playerIndex</code>
	 * @param playerIndex indice del giocatore nell'array di tutti i giocatori
	 * @return carte azione del giocatore
	 */
	public ArrayList<ActionCard> getActionCardsOfPlayer(Integer playerIndex){
		return players.get(playerIndex).getPlayerActionCards();
	}
	
	/**
	 * Restituisce le scommesse del giocatore alla posizione <code>playerIndex</code>
	 * @param playerIndex indice del giocatore nell'array di tutti i giocatori
	 * @return scommesse del giocatore
	 */
	public ArrayList<Bet> getBetsOfPlayer(Integer playerIndex){
		return players.get(playerIndex).getPlayerBets();
	}
	
	/**
	 * Restituisce la scuderia del giocatore alla posizione <code>playerIndex</code>
	 * @param playerIndex indice del giocatore nell'array di tutti i giocatori
	 * @return scuderia del giocatore
	 */
	public StableCard getStableCardOfPlayer(Integer playerIndex){
		return players.get(playerIndex).getPlayerStable();
	}
	
	/**
	 * Restituisce la carta personaggio del giocatore alla posizione <code>playerIndex</code>
	 * @param playerIndex indice del giocatore nell'array di tutti i giocatori
	 * @return carta personaggio del giocatore
	 */
	public CharacterCard getCharacterCardOfPlayer(Integer playerIndex){
		return players.get(playerIndex).getPlayerCharacter();
	}
	
	/**
	 * Restituisce le scuderia alla quotazione <code>positionOnTheBlackBoard</code>
	 * @param positionOnTheBlackBoard quotazione delle scuderie volute
	 * @return scuderie alla quotazione scelta
	 */
	public ArrayList<StableCard> getStablesCardAt(Integer positionOnTheBlackBoard){
		return oddsBlackBoard.getStablesOn(positionOnTheBlackBoard);
	}
	
	/**
	 * Restituisce il numero di giocatori della partita
	 * @return numero di giocatori della partita
	 */
	public Integer getNumberOfPlayers(){
		return players.size();
	}
	
	//metodi funzionali per il turno
	/**
	 * Aggiunge la scommessa al giocatore e controlla se è soddisfatta la scommessa minima
	 * @param playerIndex indice del giocatore a cui aggiungere la scommessa
	 * @param stableChosenByThePlayer scuderia sulla quale è stato scommesso
	 * @param betDanariAmount Danari scommessi
	 * @param isBetToWin indica se la scommessa è vincente o no
	 * @throws IllegalBetException lanciata se il giocatore non soddisfa la scommessa minima
	 */
	public void addBetOf(Integer playerIndex, StableCard stableChosenByThePlayer, Integer betDanariAmount, Boolean isBetToWin) throws IllegalBetException{
		Player player = players.get(playerIndex);
		if(player.getPlayerDanariAmount() < player.getMinimumBet()){
			player.decreasePlayerVictoryPoints(HorseFeverController.penality);
			throw new IllegalBetException("Your number of Victory Points has been decreased of 2! You don't have enough Danari for the Minimum Bet:" + player.getMinimumBet().toString());
		}
		//non voglio mantenere l'information hiding qui perchè voglio che race modifichi lo stato del mio player
		player.addPlayerBet(race.manageBet(player, stableChosenByThePlayer, betDanariAmount, isBetToWin));
		player.decreasePlayerDanariAmount(betDanariAmount);
	}
	
	/**
	 * Decrementa i punti vittoria del giocatore
	 * @param playerIndex indice del giocatore a cui decrementare i punti vittoria
	 * @param pointToDecrease numero di punti vittoria da togliere
	 */
	public void decreaseVictoryPoints(Integer playerIndex, Integer pointToDecrease){
		this.players.get(playerIndex).decreasePlayerVictoryPoints(pointToDecrease);
	}
	
	/**
	 * Restituisce la scommessa minima del giocatore
	 * @param playerIndex indice del giocatore di cui si vuole la scommessa minima
	 * @return scommessa minima del giocatore indicato
	 */
	public Integer getMinimumBetAmountOfPlayer(Integer playerIndex){
		return players.get(playerIndex).getMinimumBet();
	}
	
	/**
	 * Posa una carta azione sulla corsia della scuderia desiderata.
	 * Inoltre elimina la carta giocata dalle carte azione disponibili per il giocatore
	 * @param playerIndex indice del giocatore che posa la carta
	 * @param actionCardChosenByThePlayer carta azione da posare
	 * @param stableCardChosenByThePlayer scuderia sulla quale posare la carta
	 */
	public void addActionCardOnLane(Integer playerIndex, ActionCard actionCardChosenByThePlayer,StableCard stableCardChosenByThePlayer){
		race.addActionCardOnLane( actionCardChosenByThePlayer, stableCardChosenByThePlayer);
		removeUsedActionCardFromThePlayer(players.get(playerIndex), actionCardChosenByThePlayer);
	}
	
	/**
	 * Rimuove, dalle carte disponibili per il giocatore, la carta azione indicata
	 * @param player giocatore dal quale rimuovere la carta
	 * @param usedActionCard carta azione da rimuovere
	 */
	private void removeUsedActionCardFromThePlayer(Player player, ActionCard usedActionCard){
		player.removeThisActionCard(usedActionCard);
	}
	
	/**
	 * Restituisce le carte azione sulla corsia <code>indexOfLane</code>
	 * @param indexOfLane indice della corsia
	 * @return carte azione sulla corsia
	 */
	public ArrayList<ActionCard> getActionCardsOnLane(Integer indexOfLane){
		return race.getActionCardsOnLane(indexOfLane);
	}
	
	/**
	 * Restituisce la scuderia della corsia <code>indexOfLane</code>
	 * @param indexOfLane indice della corsia
	 * @return scuderia della corsia
	 */
	public StableCard getStableOfLane(Integer indexOfLane){
		return race.getStableOfLane(indexOfLane);
	}
	
	/**
	 * Controlla tutte le Lane e rimuove le carte azione con la stessa lettera
	 * che sono sulla medesima Lane
	 */
	public void removeSameLetterCardsFromAllTheLanes(){
		race.removeSameLetterCardsFromAllTheLanes();
	}
	
	/**
	 *Restituisce un ArrayList con tutte le posizioni delle pedine nelle corsie 
     *<p>Gli indici dell'array delle posizioni coincidono con gli indici dell'array delle corsie
	 * @return ArrayList con tutte le posizioni delle pedine nelle corsie
	 */
	public ArrayList<Integer> getAllPawnPositions(){
		return 	race.getAllPawnPositions();
	}

	/**
	 * Pesca la prima carta movimento del mazzo
	 * <p>se il mazzo delle carte movimento è esaurito, viene rigenerato nuovamente
	 * @return carta movimento pescata
	 */
	public MovementCard peekFirstMovementCard(){
		return race.peekFirstMovementCard();
	}
	
	/**
	 * Pesca un ulteriore carta movimento dal mazzo
	 * <p>se il mazzo delle carte movimento è esaurito, viene rigenerato nuovamente
	 * @return carta movimento pescata
	 */
	public MovementCard peekNextMovementCard(){
		return race.peekNextMovementCard();
	}
	
	/**
	 * Effettua lo sprint e restituisce un array con i colori usciti dal lancio dei dadi
	 * @return ArrayList con i colori usciti dal lancio dei due dadi sprint
	 */
	public ArrayList<Color> sprint(){
		ArrayList<Integer> sprintColorsInteger = race.sprint();
		ArrayList<Color> sprintColors = new ArrayList<Color>();
		sprintColors.add(colorOfStableNumber(sprintColorsInteger.get(0)));
		sprintColors.add(colorOfStableNumber(sprintColorsInteger.get(1)));
		
		return sprintColors;
	}
	
	/**
	 * Controlla se il turno di corsa è terminato
	 * @return true se il turno è terminato, false altrimenti
	 */
	public Boolean isTheRoundOver(){
		return race.isTheRoundOver();
	}
	
	/**
	 * Costruisce la classifica di arrivo
	 */
	public void makeRanking(){
		race.updateRanking();
	}
	
	/**
	 * Restituisce la classifica
	 * @return classifica corrente
	 */
	public ArrayList<StableCard> getRanking(){
		return race.getRanking();
	}
	
	/**
	 * Controlla se su qualche Lane sono presenti carte azione con l'effetto di
	 * rimuovere tutte le carte azione positive o tutte le negative e,
	 * se presenti, le fa agire
	 */
	public void checkRemovePositiveOrNegativeActionCards(){
		race.checkRemovePositiveOrNegativeActionCards();
	}
	
	/**
	 * Controlla se ci sono carte azione che aumentano o diminuiscono la quotazione
	 * di una scuderia. Se sono presenti, le fa agire.
	 */
	public void checkOddsMovementMadeByActionCards(){
		//le carte di questo tipo aumentano o decrementano la quotazione di due punti
		if(race.isThereOnTheLanesAnOddsUpActionCard()){
			StableCard stableToIncrease = race.getStableOfLanesWhichHasOddsUpActionCard();
			oddsBlackBoard.oddsUpFor(stableToIncrease);
			oddsBlackBoard.oddsUpFor(stableToIncrease);
		}
		if(race.isThereOnTheLanesAnOddsDownActionCard()){
			StableCard stableToDecrease = race.getStableOfLanesWhichHasOddsDownActionCard();
			oddsBlackBoard.oddsDownFor(stableToDecrease);
			oddsBlackBoard.oddsDownFor(stableToDecrease);
		}
	}
	
	/**
	 * Aggiorna la tabella delle quotazioni dopo il turno di corsa.
	 */
	public void updateOddsBlackBoard(){
		oddsBlackBoard.updateOddsBlackBoard(race.getRanking());
	}
	
	/**
	 * Restituisce il colore della scuderia passata in ingresso
	 * @param numberOfStable indice della scuderia di cui si vuole il colore
	 * @return colore della scuderia
	 */
	public static Color colorOfStableNumber(Integer numberOfStable){
		switch(numberOfStable){
		 case 0:  return Color.BLACK;
		 case 1:  return Color.BLUE;
		 case 2:  return Color.GREEN;
		 case 3:  return Color.RED;
		 case 4:  return Color.YELLOW;
		}
		 return Color.WHITE;
         
	}

	/**
	 * Controlla se la scuderia <code>stable</code> è la prima classificata
	 * @param stable scuderia da cercare
	 * @return <code>true</code> se è la scuderia è vincitrice, <code>false</code> altrimenti
	 */
	private boolean isTheWinnerStable(StableCard stable){
		if(stable.equals(race.getRanking().get(WINNER))){
			return true;
		}
		return false;
	}
	
	/**
	 * Controlla se la scuderia <code>stable</code> è nei primi 3 posti (podio)
	 * @param stable scuderia da cercare
	 * @return <code>true</code> se la scuderia è sul podio, <code>false</code> altrimenti
	 */
	private boolean isOnPodium(StableCard stable){
		
		if(stable.equals(race.getRanking().get(WINNER))
				|| stable.equals(race.getRanking().get(SECOND))
				|| stable.equals(race.getRanking().get(THIRD))){
							return true;
		}
		return false;
	}
	
	/**
	 * Questo metodo paga i giocatori alla fine del turno di corsa.
	 * @throws IllegalStateException
	 */
	public void payPlayers(){
		
		//gestisco il pagamento per ogni giocatore singolarmente
		for(int i=0; i<this.players.size(); i++){
			//controllo ogni sua scommessa
			Player tempPlayer = this.players.get(i);
			for(int j=0; j<tempPlayer.getPlayerBets().size(); j++){
				//se la scommessa è vincente
				Bet tempBet = tempPlayer.getPlayerBets().get(j);
				
				if(tempBet.getIsBetToWin()){
					//se la scuderia è prima pago e assegno 3 punti vittoria
					if(isTheWinnerStable(tempBet.getBetStable())){
						Integer odds = this.oddsBlackBoard.getOddsPositionOfTheStable(tempBet.getBetStable());
						//ho scritto odds+2 perche il metodo getOddsPositionOfTheStable
						//ritorna non la quotazione (da 2 a 7) ma la posizione della stable 
						//nell'array della blackboard (indice da 0 a 5)
						tempPlayer.increasePlayerDanariAmount(tempBet.getBetAmount()*(odds+2));
						tempPlayer.increasePlayerVictoryPoints(3);
						//setto la scommessa come vinta
						tempPlayer.setWonBet(j);
					}
				//se la scommessa è piazzato
				}else{
					
					//se la scuderia è sul podio
					if(isOnPodium(tempBet.getBetStable())){
						//pago e assegno un solo punto vittoria
						tempPlayer.increasePlayerDanariAmount(2*tempBet.getBetAmount());
						tempPlayer.increasePlayerVictoryPoints(1);
						//setto la scommessa come vinta
						tempPlayer.setWonBet(j);
					}
					
				}
				//pago nel caso il giocatore sia in possesso di una scuderia sul podio
				if(isTheWinnerStable(tempPlayer.getPlayerStable())){
					tempPlayer.increasePlayerDanariAmount(600);
				}
				if(tempPlayer.getPlayerStable().equals(race.getRanking().get(SECOND))){
					tempPlayer.increasePlayerDanariAmount(400);
				}
				if(tempPlayer.getPlayerStable().equals(race.getRanking().get(THIRD))){
					tempPlayer.increasePlayerDanariAmount(200);
				}
			}
		}
	}
	
	/**
	 * Sceglie il vincitore alla fine dell'intera partita
	 * @return
	 */
	public Player chooseTheWinnerPlayer(){
		
		ArrayList<Player> tempWinnersToPoints = new ArrayList<Player>();
		ArrayList<Player> tempWinnersToDanari = new ArrayList<Player>();
		int maxVictoryPoints=0;
		//trovo i punti vittoria massimi
		for(int i=0;i<this.players.size();i++){
			if(this.players.get(i).getPlayerVictoryPoints()>maxVictoryPoints){
				maxVictoryPoints=this.players.get(i).getPlayerVictoryPoints();
			}
		}
		//faccio un array con i giocatori con i massimi punti vittoria
		for(int i=0;i<this.players.size();i++){
			if(this.players.get(i).getPlayerVictoryPoints()==maxVictoryPoints){
				tempWinnersToPoints.add(this.players.get(i));
			}
		}
		//se è unico è il vincitore
		if(tempWinnersToPoints.size()==1){
			return tempWinnersToPoints.get(0);
		//se non è unico vince quello con più danari
		}else if(tempWinnersToPoints.size()>1){
			//trovo danari massimi
			int maxDanari=0;
			for(int i=0;i<tempWinnersToPoints.size();i++){
				if(tempWinnersToPoints.get(i).getPlayerDanariAmount()>maxDanari){
					maxDanari=tempWinnersToPoints.get(i).getPlayerDanariAmount();
				}
			}
			//array con giocatori massimi danari
			for(int i=0;i<tempWinnersToPoints.size();i++){
				if(tempWinnersToPoints.get(i).getPlayerDanariAmount()==maxDanari){
					tempWinnersToDanari.add(tempWinnersToPoints.get(i));
				}
			}
			//se è unico è il vincitore
			if(tempWinnersToDanari.size()==1){
				return tempWinnersToDanari.get(0);
			//se non è unico ne scelgo uno a caso
			}else if(tempWinnersToDanari.size()>1){
				Random randomGenerator = new Random();
				randomGenerator.setSeed(new Date().getTime());
				int indexOfWinner = Math.abs(randomGenerator.nextInt()%tempWinnersToDanari.size());
				return tempWinnersToDanari.get(indexOfWinner);
			}
		}
		return null;
	}
	
	/**
	 * Controlla se ci sono giocatori che perdono.
	 * <p>I giocatori perdono perchè non hanno abbastanza Danari per la 
	 * Scommessa Minima e i loro Punti Vittoria sono in numero minore 
	 * o uguale a 2.
	 * @return indici nell'array <code>players</code> nella classe <code>Game</code> dei giocatori che hanno perso
	 */
	public ArrayList<Integer> findLoosers(){
		ArrayList<Integer> indexOfPlayersToRemove = new ArrayList<Integer>();
		for(Player checkedPlayer: this.players){
			if((checkedPlayer.getPlayerDanariAmount()<checkedPlayer.getMinimumBet()) && 
					(checkedPlayer.getPlayerVictoryPoints()<=2)){
				indexOfPlayersToRemove.add(players.indexOf(checkedPlayer));
			}
		}
		return indexOfPlayersToRemove;
	}
	
	/**
	 * Rimuove i giocatori in ingresso dalla lista dei giocatori nella partita
	 * @param indexesOfPlayerToRemove Array di indici dei giocatori da rimuovere
	 */
	public void removePlayer(ArrayList<Integer> indexesOfPlayerToRemove){
		ArrayList<Player> playerToRemove = new ArrayList<Player>();
		for(int plaierIndex: indexesOfPlayerToRemove){
			playerToRemove.add(players.get(plaierIndex));
		}
		players.removeAll(playerToRemove);
	}
	
	/**
	 * Prepara un nuovo turno di corsa.
	 * <p>Distribuisce due carte azione ai giocatori ed elimina le vecchie scommesse
	 */
	public void prepareNewRace(){
		setActionCardQueueFromArrayList(actionCardDeckArrayList);
		for(Player player: players){
			ArrayList<ActionCard> playerActionCards = giveTwoActionCards();
			player.setActionCards(playerActionCards);
			player.resetPlayerBets();
		}
		race.prepareNewRace();
	}
}
