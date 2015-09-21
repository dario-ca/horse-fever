/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 * La classe <code>StableCard</code> rappresenta una carta scuderia e quindi la scuderia stessa.
 * La scuderia è caratterizzata da un colore e da un'immagine del logo.
 *<p>Estende la classe astratta <code>Card</code> e implementa l'interfaccia <code>Cloneable</code>
 *@see Card
 *@see Cloneable
 *@see Color
 */
public class StableCard extends Card implements Cloneable{
	
	private static final long serialVersionUID = 1L;
	private Color stableColor;
	private ImageIcon cardLogoImage;
	
	/**
	 * Costruttore di una carta Scuderia
	 * @param cardName nome della scuderia
	 * @param cardNumber numero della carta
	 * @param cardFrontImage immagine frontale della carta
	 * @param stableColor colore della scuderia
	 * @param cardLogoImage immagine del logo della scuderia
	 */
	public StableCard(String cardName, Integer cardNumber, ImageIcon cardFrontImage, Color stableColor, ImageIcon cardLogoImage){
		super(cardName, cardNumber, cardFrontImage);
		this.cardLogoImage=cardLogoImage;
		this.stableColor = stableColor;
	}
	
	/**
	 * Restituisce l'immagine del logo della scuderia
	 * @return immagine del logo della scuderia
	 */
	public ImageIcon getCardLogoImage(){
		return this.cardLogoImage;
	}
	
	@Override
	public StableCard clone(){
		return new StableCard(getCardName(), getCardNumber(), getCardFrontImage(), this.stableColor, this.cardLogoImage);
	}
	
	/**
	 * Restituisce il colore della scuderia
	 * @return colore della scuderia
	 * @see Color
	 */
	public Color getStableColor(){
		return this.stableColor;
	}
	
}
