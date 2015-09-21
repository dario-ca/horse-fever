/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * La classe astratta <code>Card</code> rappresenta una generica carta del gioco
 *  (ad esclusione delle carte di tipo movimento <code>MovementCard</code>.
 * Una generica carta è caratterizzata da un numero identificativo unico,
 * un nome (anch'esso unico), e una immagine di fronte.
 * <p>Implementa l'interfaccia <code>Serializable</code>
 * @see Serializable
 */
public abstract class Card implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String cardName;
	private Integer cardNumber;
	private ImageIcon cardFrontImage;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cardName == null) ? 0 : cardName.hashCode());
		result = prime * result
				+ ((cardNumber == null) ? 0 : cardNumber.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null){
				return false;
			}
		} else if (!cardName.equals(other.cardName)){
			return false;
		}
		if (cardNumber == null) {
			if (other.cardNumber != null){
				return false;
			}
		} else if (!cardNumber.equals(other.cardNumber)){
			return false;
		}
		return true;
	}
	
	/**
	 * Costruttore di una generica carta <code>Card</code>
	 * @param cardName nome della carta
	 * @param cardNumber numero della carta
	 * @param cardFrontImage immagine di fronte della carta
	 */
	public Card(String cardName,Integer cardNumber,ImageIcon cardFrontImage){
		this.cardName=cardName;
		this.cardNumber=cardNumber;
		this.cardFrontImage=cardFrontImage;
	}
	
	/**
	 * Restituisce l'immagine di fronte della carta
	 * @return immagine di fronte della carta
	 */
	public ImageIcon getCardFrontImage() {
		ImageIcon tempImage = new ImageIcon(cardFrontImage.getImage());
		return tempImage;
	}

	/**
	 * Restituisce il nome della carta
	 * @return nome della carta
	 */
	public String getCardName(){
		return cardName;
	}
	
	/**
	 * Restituisce il numero identificativo della carta
	 * @return numero della carta
	 */
	public Integer getCardNumber(){
		return this.cardNumber;
	}
}
