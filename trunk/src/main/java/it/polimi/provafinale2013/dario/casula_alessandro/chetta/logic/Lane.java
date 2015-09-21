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

import java.util.ArrayList;

/**
 * La classe <code>Lane</code> rappresenta una corsia di corsa.
 * contiene tutti i metodi che permottono di gestire la corsa sulla singola corsia.
 * Contiene la scuderia associata alla corsia e mantiene la posizione della
 * relativa pedina. Inoltre contiene le carte azione che vengono posate sulaa corsia.
 */
public class Lane {
	
	private Integer lanePawnPosition;
	private StableCard laneOfStable;
	private ArrayList<ActionCard> laneActionCards;
	private boolean isFinishLineReached;
	
	/**
	 * Costruttore di un oggeto della classe <code>Lane</code>
	 * @param laneOfStable scuderia associata alla Lane
	 */
	public Lane(StableCard laneOfStable){
		this.laneOfStable = laneOfStable;
		this.laneActionCards=new ArrayList<ActionCard>(); 
		resetLane();
	}
	
	/**
	 * Reset di un oggetto di tipo <code>Lane</code>
	 * <p>Pone la scuderia alla posizione 0 di partenza,
	 * svuota l'ArrayList con le carte azione posate sulla corsia,
	 * inizializza l'attributo di raggiungimento del traguardo a <code>false</code>
	 */
	public void resetLane(){
		lanePawnPosition = 0;
		laneActionCards.clear();
		isFinishLineReached=false;
	}
	
	/**
	 * Aggiunge alla corsia una carta azione
	 * @param actionCard carta azione da aggiungere
	 */
	public void addActionCardOnLane(ActionCard actionCard){
		laneActionCards.add(actionCard);
	}
	
	/**
	 * Restituisce la variabile che incdica se il traguardo è stato raggiunto
	 * @return true se il traguardo è raggiunto, false altrimenti
	 */
	public boolean isFinishLineReached(){
		return isFinishLineReached;
	}
	
	/**
	 * Imposta la posizione della pedina sulla corsia
	 * @param pawnPosition posizione della pedina da impostare
	 */
	public void setLanePawnPosition(Integer pawnPosition){
		this.lanePawnPosition = pawnPosition;
	}
	
	/**
	 * Restituisce la posizione della pedina sulla corsia
	 * @return posizione della pedina
	 */
	public Integer getLanePawnPosition(){
		return lanePawnPosition;
	}
	
	/**
	 * ritorna la stable sulla lane corrente
	 * @return la stable della lane corrente
	 */
	public StableCard getLaneOfStable(){
		return laneOfStable.clone();
	}
	
	/**
	 * Restituisce le carte azione sulla corsia
	 * @return carte azione sulla corsia
	 */
	public ArrayList<ActionCard> getLaneActionCards(){
		//con laneActionCards.clone i riferimenti alle carte rimangono uguali
		//dobbiamo clonare tutte le carte a mano
		ArrayList<ActionCard> cloneOflaneActionCards = new ArrayList<ActionCard>();
		for(int i=0; i<laneActionCards.size(); i++){
			cloneOflaneActionCards.add(laneActionCards.get(i).clone());
		}
		return cloneOflaneActionCards;
	}
	
	/**
	 * Determina il primo avanzamento della stable sulla corsia (momento di START)
	 * <p>Fa avanzare la stable sulla corsia di un numero di passi
	 * pari a quelli decretati dalla carta movimento, inoltre
	 * controlla se è possibile un incremento o decremento o stallo 
	 * di avanzamento dovuto alle carte azione
	 * @param steps numero di caselle di avanzamento della stable sulla corsia
	 */
	//metodo da chiamare solo dopo aver eliminato le carte con lettere uguali 
	//attraverso il metodo removeSameLetterCards()
	public void startOf(Integer steps){
		//assegno alla lane un numero di steps pari a quello indicato nella carta movimento
		lanePawnPosition=steps;
		
		//adesso controllo se ci sono carte sulla Lane che possono variare gli steps
		//ottengo l'array delle carte azione attive sullo START
		ArrayList<ActionCard> startCardsOnLane = CardProvider.provideCardsOn(ActionType.START, laneActionCards);
		//cerco prima la carta che impone un numero di steps fisso
		for(int i=0; i<startCardsOnLane.size(); i++){
				if(!startCardsOnLane.get(i).isStepsAdder()){
						lanePawnPosition=startCardsOnLane.get(i).getActionSteps();
				}
		}
		
		//adesso cerco la carta che aggiunge o toglie uno step in partenza
		//tenendo conto che il cavallo non può mai retrocedere
		for(int i=0; i<startCardsOnLane.size(); i++){
			if(startCardsOnLane.get(i).isStepsAdder()){
				if(startCardsOnLane.get(i).getActionEffect()==Effect.POSITIVE){
					lanePawnPosition+=startCardsOnLane.get(i).getActionSteps();
				}else{
					if(lanePawnPosition!=0){
						lanePawnPosition-=startCardsOnLane.get(i).getActionSteps();			
					}
				}
			}
		}
	}
	
	/**
	 * Determina la continuazione dell'avanzamento della stable sulla corsia
	 * <p>Fa avanzare la stable sulla corsia di un numero di passi
	 * pari a quelli decretati dalla carta movimento, inoltre
	 * controlla se è possibile un incremento o decremento o stallo 
	 * di avanzamento dovuto alle carte azione
	 * @param steps numero di caselle di avanzamento della stable sulla corsia
	 */
	public void goAheadOf(Integer steps){
		if(!isFinishLineReached){
			if(hasFinishLineCard(Effect.NEGATIVE)&&(lanePawnPosition+steps>=HorseFeverController.finishLine)){
					lanePawnPosition=HorseFeverController.finishLine;
					return;
			}
			lanePawnPosition+=steps;
			if((lanePawnPosition>=HorseFeverController.finishLine)&&(hasFinishLineCard(Effect.POSITIVE))){//se ho fustis et radix
					lanePawnPosition+=2;
			}
		}
	}
	
	/**
	 * Determina l'avanzamento della scuderia della Lane grazie allo sprint
	 * <p>Considera anche eventuali carte azione che agiscono allo SPRINT
	 */
	public void giveSprint(){
		Integer stepsToAdd=1;
		
		//ottengo il sottoinsieme delle carte azione che agiscono sullo sprint
		ArrayList<ActionCard> sprintCardsOnLane = CardProvider.provideCardsOn(ActionType.SPRINT, laneActionCards);
		//inizio con le carte che impongono uno sprint pari a 2
		for(int i=0; i<sprintCardsOnLane.size(); i++){
			if(!sprintCardsOnLane.get(i).isStepsAdder()){
				stepsToAdd=sprintCardsOnLane.get(i).getActionSteps();
			}
		}
		//ora controllo se ci sono carte azione che aggiungono o tolgono steps 
		//tenendo conto che il cavallo non può andare indietro
		for(int i=0; i<sprintCardsOnLane.size(); i++){
				if(sprintCardsOnLane.get(i).isStepsAdder()){
					if(sprintCardsOnLane.get(i).getActionEffect()==Effect.POSITIVE){
						stepsToAdd+=sprintCardsOnLane.get(i).getActionSteps();
					}else{
						if(stepsToAdd!=0){
							stepsToAdd-=sprintCardsOnLane.get(i).getActionSteps();
						}
					}
				}
		}
		//aggiungo gli steps generati dallo sprint
		goAheadOf(stepsToAdd);
	}
	
	/**
	 * Rimuove le carte azione con la stessa lettera
	 */
	public void removeSameLetterCards(){
		laneActionCards=CardProvider.removeSameLetterCards(laneActionCards);
	}
	
	/**
	 * Controlla se la lane corrente ha delle carte azione che rimuovono tutte le carte azione positive o tutte le negative
	 */
	//viene chiamato dalla Race
	public void checkRemoveCards(){
		if(hasRemovePositiveCards()){
			laneActionCards = CardProvider.removeCardsWhichAre(Effect.POSITIVE, laneActionCards);
		}
		if(hasRemoveNegativeCards()){
			laneActionCards = CardProvider.removeCardsWhichAre(Effect.NEGATIVE, laneActionCards);
		}
	}
	
	/**
	 * Controlla se la lane ha carte azione che rimuovono tutte le carte azione positive
	 * @return true se ci sono carte azione che rimuovono tutte le carte azione positive, false altrimenti
	 */
	private Boolean hasRemovePositiveCards(){
		ArrayList<ActionCard> removePositiveCard = CardProvider.provideCardsOn(ActionType.REMOVEPOSITIVE, laneActionCards);
		if(removePositiveCard.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * Controlla se la lane ha carte azione che rimuovono tutte le carte azione negative
	 * @return true se ci sono carte azione che rimuovono tutte le carte azione negative, false altrimenti
	 */
	private Boolean hasRemoveNegativeCards(){
		ArrayList<ActionCard> removeNegativeCard = CardProvider.provideCardsOn(ActionType.REMOVENEGATIVE, laneActionCards);
		if(removeNegativeCard.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * Controlla se ci sono carte azione che si attivano al Fotofinish
	 * @param effect effetto delle carte da cercare (POSITIVE,NEGATIVE,SPECIAL)
	 * @return 	true se sono presenti carte azione che si attivano al fotofinish, false altrimenti
	 */
	public Boolean hasPhotoFinishCard(Effect effect){
		ArrayList<ActionCard> photoFinishCards = CardProvider.provideCardsOn(ActionType.PHOTOFINISH, laneActionCards);
		for(int i=0; i<photoFinishCards.size(); i++){
			if(photoFinishCards.get(i).getActionEffect()==effect){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Controlla se ci sono carte azione che si attivano al Traguardo
	 * @param effect effetto delle carte da cercare (POSITIVE,NEGATIVE,SPECIAL)
	 * @return 	true se sono presenti carte azione che si attivano al traguardo, false altrimenti
	 */
	private Boolean hasFinishLineCard(Effect effect){
		ArrayList<ActionCard> finishLineCards = CardProvider.provideCardsOn(ActionType.FINISHLINE, laneActionCards);
		for(int i=0; i<finishLineCards.size(); i++){
			if(finishLineCards.get(i).getActionEffect()==effect){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Controlla se ci sono carte azione che aiutano la scuderia in ultima posizione
	 * @return true se ci sono carte azione che aiutano la scuderia in ultima posizione,false altrimenti
	 */
	public Boolean hasHelpLastCard(){
		ArrayList<ActionCard> helpLastCard = CardProvider.provideCardsOn(ActionType.HELPLAST, laneActionCards);
		if(helpLastCard.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * Controlla se ci sono carte azione che frenano la scuderia in prima posizione
	 * @return true se ci sono carte azione che frenano la scuderia in prima posizione,false altrimenti
	 */
	public Boolean hasStopFirstCard(){
		ArrayList<ActionCard> stopFirstCard = CardProvider.provideCardsOn(ActionType.STOPFIRST, laneActionCards);
		if(stopFirstCard.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * Controlla se ci sono carte azione che alzano la quotazione della scuderia
	 * @return true se ci sono carte azione che alzano la quotazione della scuderia,false altrimenti
	 */
	public Boolean hasOddsUpActionCard(){
		ArrayList<ActionCard> oddsUpActionCard = CardProvider.provideCardsOn(ActionType.ODDSUP, laneActionCards);
		if(oddsUpActionCard.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * Controlla se ci sono carte azione che abbassano la quotazione della scuderia
	 * @return true se ci sono carte azione che abbassano la quotazione della scuderia,false altrimenti
	 */
	public Boolean hasOddsDownActionCard(){
		ArrayList<ActionCard> oddsDownActionCard = CardProvider.provideCardsOn(ActionType.ODDSDOWN, laneActionCards);
		if(oddsDownActionCard.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * Controlla se la scuderia è arrivata al traguardo e pone a <code>true</code> la variabile <code>isFinishLineReached</code>
	 */
	public void checkIfIsFinishLineReached(){
		if(lanePawnPosition>=HorseFeverController.finishLine){
			this.isFinishLineReached=true;
		}
	}
}
