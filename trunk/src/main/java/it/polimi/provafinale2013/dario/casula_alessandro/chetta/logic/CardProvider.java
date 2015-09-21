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
 * La classe <code>CardProvider</code> si occupa di effettuare delle operazioni
 * sulle carte azione di una corsia, come eliminare le carte azione con la
 * stessa lettera o fornire carte azione di un certo tipo o con un certo effetto.
 */
public class CardProvider {
	
	/**
	 * Rimuove dalle carte azione di una corsia quelle con la stessa lettera
	 * @param laneActionCards carte azione della corsia
	 * @return carte azione della corsia senza le carte rimosse
	 */
	public static ArrayList<ActionCard> removeSameLetterCards(ArrayList<ActionCard> laneActionCards){
		boolean found;
		for(int i=0; i<laneActionCards.size(); i++){
			found=false;
			for(int j=i+1; j<laneActionCards.size(); j++){
				if((laneActionCards.get(i).getActionLetter().equals(laneActionCards.get(j).getActionLetter())) && (laneActionCards.get(j).getActionEffect()!=Effect.SPECIAL)){
					laneActionCards.remove(j);
					found=true;
				}
			}
			if(found){
				laneActionCards.remove(i);
			}
 		}
		return laneActionCards;
	}
	
	/**
	 * Rimuove le carte azione del tipo <code>effectToRemove</code> dalla corsia
	 * @param effectToRemove tipo dell'effetto delle carte azione che si vuole rimuovere
	 * @param laneActionCards carte azione presenti sulla corsia desiderata
	 * @return
	 */
	public static ArrayList<ActionCard> removeCardsWhichAre(Effect effectToRemove, ArrayList<ActionCard> laneActionCards){
		ArrayList<ActionCard> actionCardsWithoutTheEffect = new ArrayList<ActionCard>();
		for(int i=0; i<laneActionCards.size(); i++){
			if(laneActionCards.get(i).getActionEffect()!=effectToRemove){
				actionCardsWithoutTheEffect.add(laneActionCards.get(i));
			}
		}
		
		return actionCardsWithoutTheEffect;
	}
	
	/**
	 * Fornisce le carte azione del tipo <code>actionType</code>
	 * presenti tra le carte azione della corsia scelta
	 * @param actionType tipo delle carte azione che si desidera
	 * @param laneActionCards carte azione della corsia scelta
	 * @return carte azione del tipo desiderato presenti sulla corsia
	 */
	public static ArrayList<ActionCard> provideCardsOn(ActionType actionType, ArrayList<ActionCard> laneActionCards){
		ArrayList<ActionCard> resultArray = new ArrayList<ActionCard>();
		
		for(int i=0; i<laneActionCards.size(); i++){
			if(laneActionCards.get(i).getActionType()==actionType){
				resultArray.add(laneActionCards.get(i));
			}
		}
		return resultArray;
			
	}
}
