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

import java.io.Serializable;

/**
 * La classe <code>Bet</code> rappresenta una scommessa.
 * Una scommessa è caratterizzata da un ammontare di Danari da scommettere,
 * da una scuderia su cui scommettere, da una variabile che indica se la scommessa
 * è di tipo Vincente o Piazzato. 
 * E' inoltre presente una variabile che indica se una scommessa è stata vinta.
 * <p>implementa le interfacce <code>Serializable</code> e <code>Cloneable</code>
 * @see Serializable
 * @see Cloneable
 */
public class Bet implements Serializable,Cloneable{

	private static final long serialVersionUID = 1L;
	private Integer betAmount;
	private Boolean isBetToWin;
	private StableCard betStable;
	private Boolean isBetWon;
	
	@Override
	public Bet clone(){
		return new Bet(betAmount, isBetToWin, betStable, isBetWon);
	}
	
	/**
	 * Costruttore di una nuova scommessa
	 * @param betAmount Danari da scommettere
	 * @param isBetToWin true se la scommessa è vincente, false se è piazzata
	 * @param betStable scuderia su cui scommettere
	 * @param isBetWon true se la scommessa è vinta, false altrimenti
	 */
	public Bet(Integer betAmount, Boolean isBetToWin, StableCard betStable, Boolean isBetWon){
		setBetAmount(betAmount);
		setIsBetToWin(isBetToWin);
		setBetStable(betStable);
		this.isBetWon = isBetWon;
	}

	private void setBetAmount(Integer betAmount) {
		this.betAmount = betAmount;
	}

	private void setIsBetToWin(Boolean isBetToWin) {
		this.isBetToWin = isBetToWin;
	}

	private void setBetStable(StableCard betStable) {
		this.betStable = betStable;
	}
	
	/**
	 * Dichiara la scommessa vinta portando a true la relativa variabile
	 * @param betWon true se la scommessa è vinta, false altrimenti
	 */
	public void setBetWon(Boolean betWon){
		this.isBetWon = betWon;
	}
	
	/**
	 * Restituisce la quantità di danari giocati
	 * @return quantità di ddanari giocati
	 */
	public Integer getBetAmount() {
		return betAmount;
	}
	
	/**
	 * Indica se la scommessa è vinta
	 * @return true se la scommessa è vinta, false altrimenti
	 */
	public Boolean isBetWon(){
		return isBetWon;
	}
	
	/**
	 * Indica se la scommessa è vincente o piazzata
	 * @return true se la scommessa è vincente, false se è piazzata
	 */
	public Boolean getIsBetToWin() {
		return isBetToWin;
	}
	
	/**
	 * Restituisce la scuderia sulla quale si è scommesso
	 * @return scuderia su cui si è scommesso
	 */
	public StableCard getBetStable() {
		return betStable;
	}
}
