/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.*;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.exceptions.*;

import java.util.*;
/**
 * La classe <code>Race</code> rappresenta il round di corsa, 
 * dalla partenza al traguardo. Contiene tutti i metodi per gestire la corsa.
 * La classe contiene tutte le corsie, il mazzo delle carte movimento,
 * la classifica e la tabella delle quotazioni.
 */
public class Race {
	
	private ArrayList<Lane> allTheLanes;
	private Queue<MovementCard> movementCardDeck;
	private ArrayList<StableCard> ranking;
	private OddsBlackBoard oddsBlackBoard;
	
	/**
	 * Costruttore di <code>Race</code>
	 * @param oddsBlackBoard tabella delle quotazioni
	 */
	public Race(OddsBlackBoard oddsBlackBoard){
		this.oddsBlackBoard=oddsBlackBoard;
		this.ranking = new ArrayList<StableCard>();
		prepareMovementList(CardGenerator.generateMovementCardDeck());
		prepareLanes(CardGenerator.generateStableCardDeck());
	}
	
	/**
	 * Prepara il mazzo delle carte movimento. Le carte vengono mischiate in un ordine casuale.
	 * @param movementCardDeck mazzo delle carte movimento
	 */
	private void prepareMovementList(ArrayList<MovementCard> movementCardDeck){
		this.movementCardDeck = new LinkedList<MovementCard>();
		//mischio le carte
		Collections.shuffle(movementCardDeck);
		for(int i=0; i<movementCardDeck.size(); i++){
			this.movementCardDeck.add(movementCardDeck.get(i));
		}
	}
	
	//questo metodo è chiamato solo dal costruttore che setta le lane all'inizio
	/**
	 * Prepara tutte le Lane riempiendo il relativo attributo <code>allTheLanes</code>
	 * @param allTheStables tutte le scuderie
	 */
	private void prepareLanes(ArrayList<StableCard> allTheStables){
		this.allTheLanes = new ArrayList<Lane>();
		for(int i=0; i<allTheStables.size(); i++){
			this.allTheLanes.add(new Lane(allTheStables.get(i)));
		}
	}
	
	/**
	 * Controlla, partendo dalle corsie, se il turno di corsa è terminato.
	 * <p>Il turno si considera terminato quando tutte le scuderie hanno raggiunto il traguardo
	 * @return true se il round è terminato, altrimenti false
	 */
	public Boolean isTheRoundOver(){
		Boolean roundOver = true;
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			if(!allTheLanes.get(i).isFinishLineReached()){
				roundOver=false;
			}
		}
		return roundOver;
	}
	
	/**
	 * Controlla se su qualcuna delle corsie sono presenti carte azione di tipo OddsUp
	 * @return <b>true</b> se sono presenti carte azione di tipo OddsUp, <b>false</b> altrimenti
	 */
	public Boolean isThereOnTheLanesAnOddsUpActionCard(){
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			if(allTheLanes.get(i).hasOddsUpActionCard()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Controlla se su qualcuna delle corsie sono presenti carte azione di tipo OddsUp
	 * @return <b>true</b> se sono presenti carte azione di tipo OddsDown, <b>false</b> altrimenti
	 */
	public Boolean isThereOnTheLanesAnOddsDownActionCard(){
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			if(allTheLanes.get(i).hasOddsDownActionCard()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Controlla quale scuderia ha carte azione di tipo OddsUp
	 * @return scuderia che presenta carte azione di tipo OddsUp
	 */
	public StableCard getStableOfLanesWhichHasOddsUpActionCard(){
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			if(allTheLanes.get(i).hasOddsUpActionCard()){
				return allTheLanes.get(i).getLaneOfStable();
			}
		}
		return null;
	}
	
	/**
	 * Controlla quale scuderia ha carte azione di tipo OddsDown
	 * @return scuderia che presenta carte azione di tipo OddsDown
	 */
	public StableCard getStableOfLanesWhichHasOddsDownActionCard(){
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			if(allTheLanes.get(i).hasOddsDownActionCard()){
				return allTheLanes.get(i).getLaneOfStable();
			}
		}
		return null;
	}
	
	/**
	 * Imposta la classifica
	 * @param ranking classifica da impostare
	 */
	public void setRanking(ArrayList<StableCard> ranking){
		this.ranking = ranking;
	}
	
	/**
	 * Fornisce la scuderia in prima posizione, tra un ArrayList di scuderie.
	 * <p>la posizione è calcolata seguendo le regole del gioco Horse Fever: sono considerate anche la quotazione e la presenza di carte azione
	 * @param stables Scuderie passate in ingresso
	 * @return la scuderia in prima posizione
	 */
	private StableCard getFirstArrivedStable(ArrayList<StableCard> stables){
		StableCard firstStable=stables.get(0);
		int firstPosition=this.allTheLanes.get(getLaneIndexOfThisStable(firstStable)).getLanePawnPosition();
		int oddsOfTheFirst=this.oddsBlackBoard.getOddsPositionOfTheStable(firstStable);
		if(stables.size()==1){
			return firstStable;
		}else{
			//continuo a confrontare una scuderia con la successiva
			//nell'array e ogni volta decido chi tra le due è prima
			for(int i=1;i<stables.size();i++){
				boolean higherPositionCondition=(this.allTheLanes.get(getLaneIndexOfThisStable(stables.get(i))).getLanePawnPosition()>firstPosition);
				boolean samePositionCondition=(this.allTheLanes.get(getLaneIndexOfThisStable(stables.get(i))).getLanePawnPosition()==firstPosition);
				boolean sameOddsCondition=(this.oddsBlackBoard.getOddsPositionOfTheStable(stables.get(i))==oddsOfTheFirst);
				boolean negativeCardCondition=(this.allTheLanes.get(getLaneIndexOfThisStable(stables.get(i))).hasPhotoFinishCard(Effect.NEGATIVE));
				boolean negativeFirstCardCondition=(this.allTheLanes.get(getLaneIndexOfThisStable(firstStable)).hasPhotoFinishCard(Effect.NEGATIVE));
				boolean betterOddsCondition=(this.oddsBlackBoard.getOddsPositionOfTheStable(stables.get(i))<oddsOfTheFirst);
				boolean positiveCardCondition=(this.allTheLanes.get(getLaneIndexOfThisStable(stables.get(i))).hasPhotoFinishCard(Effect.POSITIVE));
				boolean positiveFirstCardCondition=(this.allTheLanes.get(getLaneIndexOfThisStable(firstStable)).hasPhotoFinishCard(Effect.POSITIVE));
				
				//NB: in tutti i casi non considerati significa che la prima
				//è quella che già sto considerando prima
				
				//posizione più avanzata
				if(higherPositionCondition){
					firstStable = stables.get(i);
					firstPosition=this.allTheLanes.get(getLaneIndexOfThisStable(firstStable)).getLanePawnPosition();
					oddsOfTheFirst=this.oddsBlackBoard.getOddsPositionOfTheStable(firstStable);
				//stessa posizione
				}else if(samePositionCondition){
							//trovata ha carta positiva o la prima ha quella negativa
							if(positiveCardCondition || negativeFirstCardCondition){
								firstStable = stables.get(i);
								firstPosition=this.allTheLanes.get(getLaneIndexOfThisStable(firstStable)).getLanePawnPosition();
								oddsOfTheFirst=this.oddsBlackBoard.getOddsPositionOfTheStable(firstStable);
							}							
							//stessa posizione,niente carte, quotazione migliore
							if(betterOddsCondition && !positiveCardCondition && !positiveFirstCardCondition && !negativeCardCondition && !negativeFirstCardCondition){
								firstStable = stables.get(i);
								firstPosition=this.allTheLanes.get(getLaneIndexOfThisStable(firstStable)).getLanePawnPosition();
								oddsOfTheFirst=this.oddsBlackBoard.getOddsPositionOfTheStable(firstStable);
							}
							//stessa posizione, stessa quotazione, nessuna carta
							if(sameOddsCondition && !positiveCardCondition && !positiveFirstCardCondition && !negativeCardCondition && !negativeFirstCardCondition){
								//scelgo random la prima
								Random randomGenerator = new Random();
								randomGenerator.setSeed(new Date().getTime());
								//se esce 0 la trovata è prima
								if(0==randomGenerator.nextInt()%2){
									firstStable = stables.get(i);
									firstPosition=this.allTheLanes.get(getLaneIndexOfThisStable(firstStable)).getLanePawnPosition();
									oddsOfTheFirst=this.oddsBlackBoard.getOddsPositionOfTheStable(firstStable);
								}
							}
				}
			}
		}
		return firstStable;
	}
	/**
	 * Controlla se una scuderia è già presente in classifica
	 * @param stableToFind scuderia da cercare nella classifica
	 * @return true se la scuderia è presente in classifica, false altrimenti
	 */
	private boolean isInRanking(StableCard stableToFind){
		for(StableCard stableCard: ranking){
			if(stableCard.equals(stableToFind)){
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Fornisce un vettore delle sole scuderie arrivate ma non già in classifica
	 * @return un vettore contenente le sole scuderie arrivate ma non già in classifica
	 */
	private ArrayList<StableCard> getArrivedStables(){
		ArrayList<StableCard> arrivedStables = new ArrayList<StableCard>();
		for(int i=0;i<this.allTheLanes.size();i++){
			if((this.allTheLanes.get(i).isFinishLineReached())&&(!isInRanking(this.allTheLanes.get(i).getLaneOfStable()))){
				arrivedStables.add(this.allTheLanes.get(i).getLaneOfStable());
			}
		}
		return arrivedStables;
	}
	
	/**
	 * Aggiorna la classifica aggiungendo solo le scuderie arrivate nell'ultimo turno, quindi non già presenti in classifica 
	 */
	public void updateRanking(){
		//considero solo le stable arrivate ma non già in classifica
		ArrayList<StableCard> stablesArrived = getArrivedStables();
		//classifica parziale da appendere a quella principale
		ArrayList<StableCard> partialRanking = new ArrayList<StableCard>();
		while(!stablesArrived.isEmpty()){
			StableCard firstStable= getFirstArrivedStable(stablesArrived);
			partialRanking.add(firstStable);
			stablesArrived.remove(firstStable);
		}
		this.ranking.addAll(partialRanking);
	}
	
	/**
	 * Rstituisce la calssifica
	 * @return classifica
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StableCard> getRanking(){
		return (ArrayList<StableCard>) this.ranking.clone();
	}
	
	/**
	 * Crea una scommessa impostando tutte le varie opzioni ricevute in ingresso.
	 * @param player giocatore di cui analizzare le scommesse
	 * @param stableChosenByThePlayer scuderia sulla quale il giocatore scommette
	 * @param danariAmount Danari scommessi dal giocatore
	 * @param isBetToWin dice se la scommessa è vincente oppure no
	 * @return ritorna la scommessa appena fatta
	 * @throws IllegalBetException
	 */
	//questo metodo gestisce qualsiasi scommessa, prima o seconda che sia basta assicurarsi di non averne fatte due uguali
	public Bet manageBet(Player player, StableCard stableChosenByThePlayer, Integer danariAmount, boolean isBetToWin)throws IllegalBetException{
		if(player.getPlayerDanariAmount()<danariAmount){
			throw new IllegalBetException("You don't have enough danari for this bet.");
		}
		ArrayList<Bet> playerBets = player.getPlayerBets();
		for(int index=0; index<playerBets.size(); index++){//scorro le scommesse del giocatore
			StableCard previouslyChosenStable=playerBets.get(index).getBetStable();
			boolean isCurrentBetToWin = playerBets.get(index).getIsBetToWin();
			if((stableChosenByThePlayer.equals(previouslyChosenStable)) && (isBetToWin==isCurrentBetToWin)){
				throw new IllegalBetException("Two equal bets on the same lane are not allowed.");
			}
		}
		return new Bet(danariAmount, isBetToWin, stableChosenByThePlayer, false);
	}
	
	/**
	 * Restituisce l'indice della scuderia <code>stableCardToFind</code> nell'array delle scuderie
	 * @param stableCardToFind scuderia da cercare
	 * @return indice della scuderia cercata
	 */
	private Integer getLaneIndexOfThisStable(StableCard stableCardToFind){
		Integer laneIndex=0;
		for(int i=0; i<allTheLanes.size(); i++){
			if(allTheLanes.get(i).getLaneOfStable().equals(stableCardToFind)){
				laneIndex = i;
			}
		}
		return laneIndex;
	}
	
	/**
	 * Posiziona una carta azione sulla corsia della scuderia scelta
	 * @param actionCardChosenByThePlayer carta azione da posare
	 * @param stableCardChosenByThePlayer scuderia su cui posare la carta
	 */
	public void addActionCardOnLane(ActionCard actionCardChosenByThePlayer, StableCard stableCardChosenByThePlayer){
		Integer laneIndexOfThisStable = getLaneIndexOfThisStable(stableCardChosenByThePlayer);
		allTheLanes.get(laneIndexOfThisStable).addActionCardOnLane(actionCardChosenByThePlayer);
	}
	
	/**
	 * Prende in input l'indice della corsia (un numero da 0 a 5) e ritorna l'array delle carte giocate su quella lane
	 * @param indexOfLane indice della corsia di cui si vogliono le carte azione
	 * @return ArrayList delle carte azione sulla corsia
	 */
	public ArrayList<ActionCard> getActionCardsOnLane(Integer indexOfLane){
		return allTheLanes.get(indexOfLane).getLaneActionCards();
	}
	
	/**
	 * Prende in input l'indice della corsia (un numero da 0 a 5) e ritorna la scuderia di quella lane
	 * @param indexOfLane indice della corsia di cui si vuole la scuderia
	 * @return scuderia della corsia
	 */
	public StableCard getStableOfLane(Integer indexOfLane){
		return allTheLanes.get(indexOfLane).getLaneOfStable();
	}
	
	/**
	 * Imposta il vettore di tutte le corsie
	 * @param allLanes vettore di tutte le corsie da impostare
	 */
	public void setAllLanes(ArrayList<Lane> allLanes){
		this.allTheLanes = allLanes;
	}
	
	/**
	 * Controlla se su qualche Lane sono presenti carte azione con l'effetto di rimuovere tutte le carte azione positive o tutte le negative e, se presenti, le fa agire
	 */
	public void checkRemovePositiveOrNegativeActionCards(){
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			allTheLanes.get(i).checkRemoveCards();
		}
	}
	
	/**
	 * Elimina dalle Lane le carte azione con la stessa lettera, siano esse positive o negative
	 */
	public void removeSameLetterCardsFromAllTheLanes(){
		for(int i=0; i<allTheLanes.size(); i++){
			allTheLanes.get(i).removeSameLetterCards();
		}
	}
	
	/**
	 * Restituisce un ArrayList con tutte le posizioni delle pedine nelle corsie
	 * <p>Gli indici dell'array delle posizioni coincidono con gli indici dell'array delle corsie
	 * @return ArrayList con tutte le posizioni delle pedine nelle corsie
	 */
	public ArrayList<Integer> getAllPawnPositions(){
		ArrayList<Integer> allPawnPositions = new ArrayList<Integer>();
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			allPawnPositions.add(allTheLanes.get(i).getLanePawnPosition());
		}
		return allPawnPositions;
	}
	
	/**
	 * Pesca la prima carta movimento del mazzo
	 * <p>se il mazzo delle carte movimento è esaurito, viene rigenerato nuovamente
	 * @return Carta movimento pescata
	 */
	public MovementCard peekFirstMovementCard(){
		//se il mazzo di movement card si è esaurito lo rigenero
		if(movementCardDeck.isEmpty()){
			prepareMovementList(CardGenerator.generateMovementCardDeck());
		}
		MovementCard peekedMovementCard = movementCardDeck.poll();
		ArrayList<Integer> movements = peekedMovementCard.getAllMovements();
		//prendo l'oddsspaces
		ArrayList<StableCard> stablesOnTheRow;
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			stablesOnTheRow = oddsBlackBoard.getStablesOn(i);
			for(int j=0; j<stablesOnTheRow.size(); j++){
				//estraggo dalla oddsSpace la scuderia piazzata su questa riga j
				StableCard stableOnThisRow = stablesOnTheRow.get(j);
				allTheLanes.get(getLaneIndexOfThisStable(stableOnThisRow)).startOf(movements.get(i));
			}
		}
		return peekedMovementCard;
	}
	
	/**
	 * Pesca un ulteriore carta movimento dal mazzo
	 * <p>se il mazzo delle carte movimento è esaurito, viene rigenerato nuovamente
	 * @return carta movimento pescata
	 */
	public MovementCard peekNextMovementCard(){
		//prendo i primi e gli ultimi prima di iniziare a muovere i cavalli
		ArrayList<String> firstStables = getFirstStableNames();
		ArrayList<String> lastStables = getLastStableNames();
		//se il mazzo di movement card si è esaurito lo rigenero
		if(movementCardDeck.isEmpty()){
			prepareMovementList(CardGenerator.generateMovementCardDeck());
		}
		MovementCard peekedMovementCard = movementCardDeck.poll();
		ArrayList<Integer> movements = peekedMovementCard.getAllMovements();
		//conterrà la riga dall'oddsspaces
		ArrayList<StableCard> stablesOnTheRow;
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			stablesOnTheRow = oddsBlackBoard.getStablesOn(i);
			for(int j=0; j<stablesOnTheRow.size(); j++){
				//estraggo dalla oddsSpace la scuderia piazzata su questa riga i
				StableCard stableOnThisRow = stablesOnTheRow.get(j);
				if(lastStables.contains(stableOnThisRow.getCardName()) && allTheLanes.get(getLaneIndexOfThisStable(stableOnThisRow)).hasHelpLastCard()){
					allTheLanes.get(getLaneIndexOfThisStable(stableOnThisRow)).goAheadOf(4);
					continue;
				}
				if(firstStables.contains(stableOnThisRow.getCardName()) && allTheLanes.get(getLaneIndexOfThisStable(stableOnThisRow)).hasStopFirstCard()){
					continue;
				}
				allTheLanes.get(getLaneIndexOfThisStable(stableOnThisRow)).goAheadOf(movements.get(i));
			}
		}
		return peekedMovementCard;
	}
	
	/**
	 * Lancia i dadi sprint e muove le scuderie uscite.
	 * <p>dopo lo sprint controlla quali scuderie hanno terminato la corsa e aggiorna la classifica
	 * @return ArryList con il risultato dei dadi (indici delle scuderie da muovere)
	 */
	public ArrayList<Integer> sprint(){
		ArrayList<Integer> resultDices = SprintDices.roll();
		//devo tener conto che se esce 2 volte la stessa lane non deve sprintare due volte
		if(resultDices.get(0)==resultDices.get(1)){
			allTheLanes.get(resultDices.get(0)).giveSprint();
		}else{
			allTheLanes.get(resultDices.get(0)).giveSprint();
		    allTheLanes.get(resultDices.get(1)).giveSprint();
		}
		
		//a questo punto, dopo aver fatto lo sprint posso fermare i cavalli che hanno superato la linea
		//d'arrivo
		for(Lane lane: allTheLanes){
			lane.checkIfIsFinishLineReached();
		}
		updateRanking();
		return resultDices;
	}

	/**
	 * Cerca le scuderie in ultima posizione
	 * @return ArrayList con i nomi delle scuderie in ultima posizione
	 */
	private ArrayList<String> getLastStableNames(){
			ArrayList<String> lastStables = new ArrayList<String>();
			int last;
			
			last = allTheLanes.get(0).getLanePawnPosition();
			
			//trovo l'ultima posizione occupata da uno stable
			for(int i=0; i<allTheLanes.size(); i++){
				if(allTheLanes.get(i).getLanePawnPosition()<last){
					last = allTheLanes.get(i).getLanePawnPosition();
				}
			}
			//adesso ho l'ultima posizione occupata, metto in un array tutti gli stable che sono in quella posizione
			for(int i=0; i<allTheLanes.size(); i++){
				if(allTheLanes.get(i).getLanePawnPosition()==last){
					lastStables.add(allTheLanes.get(i).getLaneOfStable().getCardName());
				}
			}
			return lastStables;
		}
	
	/**
	 * Cerca le scuderie in prima posizione
	 * @return ArrayList con i nomi delle scuderie in prima posizione
	 */
	private ArrayList<String> getFirstStableNames(){
		ArrayList<String> firstStables = new ArrayList<String>();
		int first;
		
		first = 0;
		
		for(int i=0; i<allTheLanes.size(); i++){
			if(allTheLanes.get(i).getLanePawnPosition()>first){
				first = allTheLanes.get(i).getLanePawnPosition();
			}
		}
		for(int i=0; i<allTheLanes.size(); i++){
			if(allTheLanes.get(i).getLanePawnPosition()==first){
				firstStables.add(allTheLanes.get(i).getLaneOfStable().getCardName());
			}
		}
		return firstStables;
	}
	
	/**
	 * Prepara un nuovo turno di corsa (dall'inizio al traguardo)
	 */
	public void prepareNewRace(){
		this.ranking=new ArrayList<StableCard>();
		for(Lane lane: allTheLanes){
			lane.resetLane();
		}
	}
	
}