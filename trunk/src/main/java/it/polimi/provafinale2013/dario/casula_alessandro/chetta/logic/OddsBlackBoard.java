/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <code>OddsBlackBoard</code> rappresenta la tabella delle quotazioni.
 * E' strutturata come un ArrayList di ArrayList:
 * gli indici dell'Arrylist interna indicano la quotazione alla quale
 * sono posizionate le scuderie contenute nelle ArrayList interne.
 * La classe contiene tutti i metodi per la gestione della tabella.
 * Implementa l'interfaccia <code>Cloneable</code>
 * @see Cloneable
 */
public class OddsBlackBoard implements Cloneable{
	private ArrayList<ArrayList<StableCard>> oddsSpaces;
	
	/**
	 * Costruttore della OddsBlackboard, tabella delle quotazioni
	 */
	//il costruttore deve inizialmente mettere le varie scuderie in ordine sparso sulla prima riga della oddsspaces
	public OddsBlackBoard() {
		oddsSpaces = new ArrayList<ArrayList<StableCard>>();
		//genero le carte scuderia
		ArrayList<StableCard> oddsArrayList = CardGenerator.generateStableCardDeck();
		//le mescolo
		Collections.shuffle(oddsArrayList);
		//le inserisco nella prima posizione degli array che rappresentano una riga della stable odds
		setOddsSpacesWithThisArrayList(oddsArrayList);
	}
	
	/**
	 * Costruttore della tabella quotazioni in cui si impongono le quotazioni delle scuderie
	 * <p> <code>oddsSpaces</code> sono le quotazioni delle scuderie. 
	 * E' un ArrayList di ArrayList: gli indici del primo ArrayList 
	 * costituiscono le quote, gli ArrayList ad ogni quota 
	 * contengono le scuderie
	 * @param oddsSpaces quotazioni da imporre
	 */
	public OddsBlackBoard(ArrayList<ArrayList<StableCard>> oddsSpaces){
		this.oddsSpaces = oddsSpaces;
	}
	
	@Override
	public OddsBlackBoard clone(){
		return new OddsBlackBoard(this.oddsSpaces);
	}
	
	/**
	 * Impone le quote delle scuderie
	 * @param oddsArrayList quote da imporre
	 */
	private void setOddsSpacesWithThisArrayList(ArrayList<StableCard> oddsArrayList){
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			ArrayList<StableCard> oddsRow = new ArrayList<StableCard>();
			oddsRow.add(oddsArrayList.get(i));
			this.oddsSpaces.add(oddsRow);
		}
	}
	
	/**
	 * Fornisce la posizione della scuderia nella lavagna delle quotazioni
	 * <p>Non fornisce la quotazione ma la posizione nell'array (da 0 a 5), per la quotazione si aggiunge 2
	 * @param stableToFind scuderia di cui cercare la quotazione
	 * @return posizione della scuderia cercata nella lavagna delle quotazioni
	 */
	public Integer getOddsPositionOfTheStable(StableCard stableToFind){
		Integer oddsStablePosition =0;
		
		for(int i=0; i<oddsSpaces.size(); i++){
			for(int j=0; j<oddsSpaces.get(i).size(); j++){
				if(oddsSpaces.get(i).get(j).equals(stableToFind)){
					oddsStablePosition=i;
				}
			}
		}
		return oddsStablePosition;
	}
	
	/**
	 * Restituisce le scuderie che si trovano alla quotazione voluta <code>positionOnBlackBoard<code>
	 * <p>la quotazione passata non è la vera quotazione, ma la posizione nell'ArrayList
	 * @param positionOnBlackBoard posizione della scuderia sulla tabella quotazioni
	 * @return ArrayList di scuderie che si trovano alla quotazione voluta
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StableCard> getStablesOn(Integer positionOnBlackBoard){
		return (ArrayList<StableCard>) oddsSpaces.get(positionOnBlackBoard).clone();
	}
	
	/**
	 * Incrementa la quotazione della scuderia passata
	 * <p> se la scuderia ha già quotazione massima, la carta non agisce.
	 * @param stableToIncrease scuderia a cui incrementare la quotazione
	 */
	public void oddsUpFor(StableCard stableToIncrease){
		if(getOddsPositionOfTheStable(stableToIncrease)==0){
			return;
		}
		//calcolo la posizione futura della stable che sarà la attuale -1
		Integer futurePosition = getOddsPositionOfTheStable(stableToIncrease)-1;
		//rimuovo temporaneamente lo stable dalla sua posizione per metterlo successivamente alla posizione superiore
		removeStableFromTheOBB(stableToIncrease);
		//adesso lo riuaggiungo nella posizione futureposition
		oddsSpaces.get(futurePosition).add(stableToIncrease);
	}

	/**
	 * Decrementa la quotazione della scuderia passata.
	 * <p> se la scuderia ha già quotazione minima, la carta non agisce.
	 * @param stableToDecrease scuderia a cui decrementare la quotazione
	 */
	public void oddsDownFor(StableCard stableToDecrease){
		if(getOddsPositionOfTheStable(stableToDecrease)==5){
			return;
		}
		//calcolo la posizione futura della stable che sarà la attuale +1
		Integer futurePosition = getOddsPositionOfTheStable(stableToDecrease)+1;
		removeStableFromTheOBB(stableToDecrease);
		oddsSpaces.get(futurePosition).add(stableToDecrease);
	}
	
	/**
	 * Rimuove dalla tabella quotazioni la scuderia passata
	 * @param stableToRemove scuderia da rimuovere
	 */
	private void removeStableFromTheOBB(StableCard stableToRemove){
		//qui salvo la posizione dello stable per rimuoverlo dopo il ciclo
		int iOfTheStable=0;
		int jOfTheStable=0;
		
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			for(int j=0; j<oddsSpaces.get(i).size(); j++){
				if(oddsSpaces.get(i).get(j).equals(stableToRemove)){
					iOfTheStable=i;
					jOfTheStable=j;
				}
			}
		}
		oddsSpaces.get(iOfTheStable).remove(jOfTheStable);
	}

	/**
	 * Aggiorna la tabella delle quotazioni dopo il turno di corsa
	 * <p>Aggiorna la tabella quotazioni considerando la posizione in classifica delle scuderie: 
	 * se la scuderia ha raggiunto una posizione in classifica migliore della quotazione,
	 * la scuderia migliora la propria quotazione; 
	 * se la scuderia ha raggiunto una posizione in classifica peggiore della quotazione, 
	 * la scuderia peggiora la propria quotazione; 
	 * se la posizione in classifica della scuderia è uguale alla quotazione, 
	 * la quotazione rimane invariata.
	 * @param lastRanking classifica di arrivo delle scuderie
	 */
	public void updateOddsBlackBoard(ArrayList<StableCard> lastRanking){
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			StableCard stableFromRanking = lastRanking.get(i);
			Integer positionOfTheStableInTheRanking = i;
			Integer positionOfTheStableInTheOBB = getOddsPositionOfTheStable(stableFromRanking);
			if(positionOfTheStableInTheRanking<positionOfTheStableInTheOBB){
				oddsUpFor(stableFromRanking);
			}
			if(positionOfTheStableInTheRanking>positionOfTheStableInTheOBB){
				oddsDownFor(stableFromRanking);
			}
		}
	}
	
	public ArrayList<ArrayList<StableCard>> getOddsSpaces(){
		return oddsSpaces;
	}
}
