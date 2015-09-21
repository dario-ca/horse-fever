/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic;

/**
 * La classe <code>TurnManager</code> si occupa della gestione dei turni dei giocatori.
 * Contiene il numero dei giocatori, l'indice del primo giocatore,
 * l'indice del giocatore corrente e l'indice del giocatore 
 * che risulta il primo facendo un giro inverso dei turni.
 * Una variabile boolean indica se si sta compiendo un giro diretto o inverso.
 */
public class TurnManager {
	private final Integer numberOfPlayers;
	private Integer firstPlayer;
	private Integer currentPlayer;
	private Boolean isReverseWay;
	private Integer firstInReverse;
	
	/**
	 * Costruttore del gestore dei turni.
	 * @param numberOfPlayers numero di giocatori nella partita
	 * @param firstPlayer indice del primo giocatore
	 * @param isReverseWay variabile che indica se si sta compiendo un giro diretto o inverso
	 */
	public TurnManager(Integer numberOfPlayers, Integer firstPlayer, Boolean isReverseWay){		
		this.numberOfPlayers=numberOfPlayers;
		this.isReverseWay=isReverseWay;
		this.firstPlayer=firstPlayer;
		this.currentPlayer = firstPlayer;
		if(isReverseWay){
			goNext();
			firstInReverse=getCurrentPlayer();
		}
	}
	
	/**
	 * Restituisce l'indice del giocatore corrente.
	 * @return indice del giocatore corrente
	 */
	public Integer getCurrentPlayer(){
		return currentPlayer;
	}
	
	/**
	 * Verifica se si è in fondo al giro di turni, controllando se il giocatore
	 * corrente possiede un giocatore successivo.
	 * @return true se è presente un giocatore successivo, false altrimenti
	 */
	public Boolean hasNext(){
		if(!isReverseWay){
			Integer wouldBeNext=1+currentPlayer;
			if(wouldBeNext>=numberOfPlayers){
				wouldBeNext=0;
			}
			if(wouldBeNext.equals(firstPlayer)){
				return false;
			}
		}
		if(isReverseWay){
			Integer wouldBeNext=-1+currentPlayer;
			if(wouldBeNext<0){
				wouldBeNext=-1+numberOfPlayers;
			}
			if(wouldBeNext.equals(firstInReverse)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Definisce il nuovo giocatore corrente, cioè il prossimo giocatore nel giro dei turni.
	 */
	public void goNext(){
		if(isReverseWay){
			currentPlayer--;
			if(currentPlayer<0){
				currentPlayer=-1+numberOfPlayers;
			}
		}else{
			currentPlayer++;
			if(currentPlayer.equals(numberOfPlayers)){
				currentPlayer=0;
			}
		}
	}
	
	/**
	 * Restituisce l'indice del primo giocatore
	 * @return indice del primo giocatore
	 */
	public Integer getFirstPlayer(){
		return this.firstPlayer;
	}
	
}
