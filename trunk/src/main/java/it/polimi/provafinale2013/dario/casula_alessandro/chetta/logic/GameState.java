/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic;
/**
 * Enumerazione degli stati del gioco.
 * Ogni elemento rappresenta uno specifico momento della partita.
 * Serve per determinare univocamente lo stato del gioco in cui ci si trova.
 */
public enum GameState {
	TAKE_PLAYERS,
	TAKE_FIRST_BETS,
	ADD_ACTION_CARDS_FIRST_TIME,
	ADD_ACTION_CARDS_SECOND_TIME,
	TAKE_SECOND_BETS,
	REMOVE_SAME_LETTER_CARDS,
	CHECK_ACTION_CARDS_BEFORE_RACE,
	START_RACE,
	IN_RACE,
	SPRINT,
	PAYMENT,
	UPDATE_ODDS_BLACK_BOARD,
	REMOVE_LOOSERS,
	START_NEXT_ROUND,
	SAY_THE_WINNER,
	NEW_GAME;
	
}
