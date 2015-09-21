/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards;

import javax.swing.*;
/**
 * La classe <code>Actioncard</code> rappresenta una carta di tipo azione.
 * La carta azione è caratterizzata da un carattere <code>actionLetter</code>, 
 * da un'immagine del retro <code>cardBackImage</code>,
 * da un effetto <code>actionEffect</code>,
 * da un tipo di azione <code>actionType</code>,
 * da un numero di passi <code>actionSteps</code>.
 * L'attributo <code>isStepsAdder</code> indica se la carta determina un avanzamento della scuderia
 * <p>Estende la classe astratta <code>Card</code> ed implementa l'interfaccia <code>Cloneable</code>
 * @see Card
 * @see Cloneable
 * @see Effect
 * @see ActionType
 */
public class ActionCard extends Card implements Cloneable{
	static final long serialVersionUID=1;
	private String actionLetter;
	private ImageIcon cardBackImage;
	private Effect actionEffect;
	private ActionType actionType;
	private Integer actionSteps;
	private Boolean isStepsAdder; //campo aggiunto in fase di sviluppo per esprimere l'effettivo assegnamento degli steps dell'effetto
	
	@Override
	public ActionCard clone() {
		return new ActionCard(getCardName(), getCardNumber(), getCardFrontImage(), getCardBackImage(), this.actionLetter, this.actionEffect, this.actionType, this.actionSteps, this.isStepsAdder);
	}

	/**
	 * Costruttore della carta di tipo <code>ActionCard</code>
	 * @param cardName nome della carta
	 * @param cardNumber numero della carta
	 * @param cardFrontImage immagine frontale della carta
	 * @param cardBackImage immagine del retro della carta
	 * @param actionCharacter lettera della carta
	 * @param actionEffect indica se la carta è positiva, negativa o speciale
	 * @param actionType indica il tipo dell'effetto della carta
	 * @param actionSteps incremento o decremento di posizione del cavallo a causa della carta
	 * @param isStepsAdder indica se la carta è indipendente dalla carta movimento scoperta
	 */
	public ActionCard(String cardName, Integer cardNumber, ImageIcon cardFrontImage, ImageIcon cardBackImage, String actionCharacter, Effect actionEffect, ActionType actionType, Integer actionSteps, Boolean isStepsAdder){
		super(cardName, cardNumber, cardFrontImage);
		this.actionEffect=actionEffect;
		this.actionType=actionType;
		this.actionLetter=actionCharacter;
		this.actionSteps=actionSteps;
		this.isStepsAdder=isStepsAdder;
		this.cardBackImage=cardBackImage;
	}
	
	/**
	 * Restituisce la lettera della carta azione
	 * @return lettera della carta azione
	 */
	public String getActionLetter(){
		return actionLetter;	
	}
	
	/**
	 * Restituisce l'effetto della carta azione: <code>POSITIVE, NEGATIVE, SPECIAL</code>
	 * @return effetto della carta azione
	 * @see Effect
	 */
	public Effect getActionEffect(){
		return actionEffect;
	}
	
	/**
	 * Restituisce il tipo della carta azione
	 * @return tipo della carta azione
	 * @see ActionType
	 */
	public ActionType getActionType(){
		return actionType;
	}
	
	/**
	 * Restituisce il numero di passi della carta azione
	 * @return numero di passi della carta azione
	 */
	public Integer getActionSteps(){
		return actionSteps;
	}
	
	/**
	 * Restituisce l'immagine del retro della carta azione
	 * @return immagine del retro della carta azione
	 */
	public ImageIcon getCardBackImage() {
		ImageIcon tempImage = new ImageIcon(cardBackImage.getImage());
		return tempImage;
	}
	
	/**
	 * Indica se la carta fa avanzare la scuderia della corsia su cui la carta azione è giocata
	 * del numero di passi indicato sulla carta stessa
	 * @return true se determina un avanzamento, false altrimenti
	 */
	public Boolean isStepsAdder(){
		return isStepsAdder;
	}
}