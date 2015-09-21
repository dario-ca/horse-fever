/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic;

import java.util.*;
/**
 * La classe <code>SprintDices</code> rappresenta i due dadi sprint.
 * Contiene i metodi per il lancio dei dadi.
 */
public class SprintDices {
	
	/**
	 * Lancia i dadi e restituisce l'indice delle scuderie 
	 * per le quali deve avvenire lo sprint.
	 * Il risultato dei dadi non sono due colori, ma due numeri casuali
	 * che indicheranno le lane nell'ArrayList di corsie.
	 * @return ArrayList di due interi, indici delle corsie per cui deve avvenire lo sprint
	 */
	public static ArrayList<Integer> roll(){
		ArrayList<Integer> resultDices = new ArrayList<Integer>();
		//oggetto Random
		Random randomGenerator = new Random();
		//setto il seed con l'orario
		randomGenerator.setSeed(new Date().getTime());
		
		//aggiungo 2 numeri casuale intero tra 0 e 5 nell'array
		resultDices.add(Math.abs(randomGenerator.nextInt()%6));
		resultDices.add(Math.abs(randomGenerator.nextInt()%6));
		
		return resultDices;
	}
}
