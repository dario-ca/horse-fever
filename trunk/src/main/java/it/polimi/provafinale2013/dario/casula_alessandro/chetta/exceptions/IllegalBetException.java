/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.exceptions;
/**
 * Eccezione <code>checked</code> di scommessa non consentita.
 * Estende la classe <code>Exception</code>
 * @see Exception
 */
public class IllegalBetException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	/**
	 * Costruisce un'eccezione di scommessa non consentita
	 * @param errorMessage messaggio di errore
	 */
	public IllegalBetException(String errorMessage){
		
		super("There's a problem with your bet:");
		this.errorMessage=errorMessage;
	}
	
	@Override
	public String toString(){
		return getMessage() + errorMessage;
	}
}
