/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards;
import javax.swing.ImageIcon;

/**
 * La classe <code>CharacterCard</code> rappresenta una carta personaggio.
 * Una carta personaggio è rappresentata da un ammontare di Danari posseduti dal personaggio
 * e dalla quotazione della scuderia di cui il personaggio è proprietario
 * <p>Estende la classe astratta <code>Card</code> ed implementa l'interfaccia <code>Cloneable</code>
 * @see Card
 * @see Cloneable
 */
public class CharacterCard extends Card implements Cloneable{
	
	private static final long serialVersionUID = 1L;
	private Integer characterDanariAmount;
	private Integer stableOdds;
	
	@Override
	public CharacterCard clone() {
		return new CharacterCard(getCardName(), getCardNumber(), getCardFrontImage(), this.characterDanariAmount, this.stableOdds);
	}
	
	/**
	 * Costruttore di un oggetto di tipo <code>CharacterCard</code>
	 * @param cardName nome della carta
	 * @param cardNumber numero della carta
	 * @param cardFrontImage immagine frontale della carta
	 * @param characterDanariAmount Danari del personaggio
	 * @param stableOdds quotazione della scuderia posseduta
	 * @see CharacterCard
	 */
	public CharacterCard(String cardName, Integer cardNumber, ImageIcon cardFrontImage, Integer characterDanariAmount, Integer stableOdds){
		super(cardName, cardNumber, cardFrontImage);
		this.characterDanariAmount = characterDanariAmount;
		this.stableOdds = stableOdds;
	}
	
	/**
	 * Restituisce la quotazione della scuderia posseduta
	 * @return quotazione della scuderia posseduta
	 */
	public Integer getStableOdds() {
		return stableOdds;
	}
	
	/**
	 * restituisce l'ammontare dei Danari posseduti dal personaggio
	 * @return Danari posseduti dal personaggio
	 */
	public Integer getCharacterDanariAmount() {
		return characterDanariAmount;
	}	
}
