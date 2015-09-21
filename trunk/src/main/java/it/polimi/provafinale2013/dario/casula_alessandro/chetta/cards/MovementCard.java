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
import java.util.*;
import javax.swing.ImageIcon;

/**
 * La classe <code>MovementCard</code> rappresenta una carta movimento.
 * Una carta movimento è rappresentata dal numero di passi di cui avanzano 
 * le scuderie alla quotazione corrispondente, oltre che da un numero univoco
 * e da un'immagine frontale.
 * I passi di avanzamento sono rappresentati tramite un ArrayList di Integer:
 *  ogni intero indica il numero di passi di avanzamento e il suo indice nell'Array
 * indica la quotazione della scuderia da far avanzare.
 * <p>Implementa l'interfaccia <code>Serializable</code>
 * @see Serializable
 */
public class MovementCard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer cardNumber;
	private ArrayList<Integer> allMovements = new ArrayList<Integer>();
	private ImageIcon cardFrontImage;
	
	/**
	 * Costruttore di una carta movimento
	 * @param cardNumber numero della carta
	 * @param cardFrontImage immagine frontale della carta
	 * @param allMovements ArrayList di movimenti di avanzamento
	 */
	public MovementCard(Integer cardNumber, ImageIcon cardFrontImage, ArrayList<Integer> allMovements){
		setCardNumber(cardNumber);
		setCardFrontImage(cardFrontImage);
		setMovements(allMovements);
	}
	
	private void setCardNumber(Integer cardNumber){
		this.cardNumber = cardNumber;
	}
	
	private void setCardFrontImage(ImageIcon cardFrontImage){
		ImageIcon tempImage = new ImageIcon(cardFrontImage.getImage());
		this.cardFrontImage = tempImage;
	}
	
	private void setMovements(ArrayList<Integer> allMovements){
		this.allMovements = allMovements;
	}
	
	/**
	 * Restituisce il numero della carta movimento
	 * @return numero della carta movimento
	 */
	public Integer getCardNumber(){
		return this.cardNumber;
	}
	
	/**
	 * Restituisce l'immagine frontale della carta movimento
	 * @return immagine frontale della carta
	 */
	public ImageIcon getCardFrontImage(){
		return this.cardFrontImage;
	}
	
	/**
	 * Restituisce i passi di avanzamento indicati sulla carta movimento
	 * @return ArrayList di Integer che rappresentano i passi di avanzamento
	 */
	public ArrayList<Integer> getAllMovements(){
		return this.allMovements;
	}
}
