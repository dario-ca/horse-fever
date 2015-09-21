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

import java.util.*;
/**
 * La classe <code>Player</code> rappresenta un giocatore.
 * Un giocatore è caratterizzato da un nome, una scuderia, Danari per le scommesse,
 * Punti Vittoria, una carta personaggio, due carte azione e 
 * una lista di scommesse effettuate.
 */
public class Player{

	private String playerName;
	private StableCard playerStable;
	private Integer playerDanariAmount;
	private CharacterCard playerCharacter;
	private Integer playerVictoryPoints;
	private ArrayList<ActionCard> playerActionCards;
	private ArrayList<Bet> playerBets;
	
	/**
	 * Costruttore del <code>player</code>.
	 * @param playerName nome del giocatore
	 * @param playerStable scuderia del giocatore
	 * @param playerCharacter Personaggio del giocatore
	 * @param playerVictoryPoints Punti Vittoria del giocatore
	 * @param playerActionCards ArrayList di carte azione del giocatore
	 */
	public Player(String playerName,StableCard playerStable, CharacterCard playerCharacter, Integer playerVictoryPoints, ArrayList<ActionCard> playerActionCards){
		setPlayerName(playerName);
		setPlayerStable(playerStable);
		setPlayerDanariAmount(playerCharacter.getCharacterDanariAmount());
		setPlayerCharacter(playerCharacter);
		setPlayerVictoryPoints(playerVictoryPoints);
		setPlayerActionCards(playerActionCards);
		playerActionCards = new ArrayList<ActionCard>();
		playerBets = new ArrayList<Bet>();
	}
	
	/**
	 * Overloading del costruttore del player: questo è usato per fare la clonazione dell'oggetto <code>player</code>
	 * @param playerName nome del giocatore
	 * @param playerStable scuderia del giocatore
	 * @param playerDanariAmount Danari del giocatore
	 * @param playerCharacter Personaggio del giocatore
	 * @param playerVictoryPoints Punti Vittoria del giocatore
	 * @param playerActionCards ArrayList di carte azione del giocatore
	 * @param playerBets ArrayList delle scommesse del giocatore
	 */
	public Player(String playerName,StableCard playerStable, Integer playerDanariAmount, CharacterCard playerCharacter, Integer playerVictoryPoints, ArrayList<ActionCard> playerActionCards, ArrayList<Bet> playerBets){
		setPlayerName(playerName);
		setPlayerStable(playerStable);
		setPlayerDanariAmount(playerDanariAmount);
		setPlayerCharacter(playerCharacter);
		setPlayerVictoryPoints(playerVictoryPoints);
		setPlayerActionCards(playerActionCards);
		setPlayerBets(playerBets);
		
	}
	
	/**
	 * setter del nome del giocatore
	 * @param playerName nome del giocatore
	 */
	private void setPlayerName(String playerName){
		this.playerName = playerName;
	}
	
	/**
	 * setter della scuderia del giocatore
	 * @param playerStable scuderia del giocatore
	 */
	private void setPlayerStable(StableCard playerStable){
		this.playerStable = playerStable;
	}
	
	/**
	 * setter dei Danari del giocatore
	 * @param playerDanariAmount Danari del giocatore
	 */
	private void setPlayerDanariAmount(Integer playerDanariAmount){
		this.playerDanariAmount = playerDanariAmount;
	}
	
	/**
	 * setter del personaggio del giocatore
	 * @param playerCharacter personaggio del giocatore
	 */
	private void setPlayerCharacter(CharacterCard playerCharacter){
		this.playerCharacter = playerCharacter;
	}
	
	/**
	 * setter dei Punti Vittoria del giocatore
	 * @param playerVictoryPoints Punti Vittoria del giocatore
	 */
	private void setPlayerVictoryPoints(Integer playerVictoryPoints){
		this.playerVictoryPoints = playerVictoryPoints;
	}
	
	/**
	 * setter dell'ArrayList di carte azione del giocatore
	 * @param playerActionCards ArrayList di carte azione del giocatore
	 */
	private void setPlayerActionCards(ArrayList<ActionCard> playerActionCards){
		this.playerActionCards = playerActionCards;
	}
	
	/**
	 * setter dell'ArrayList di scommesse del giocatore
	 * @param playerBets ArrayList di scommesse del giocatore
	 */
	private void setPlayerBets(ArrayList<Bet> playerBets){
		this.playerBets = playerBets;
	}
	
	/**
	 * Aggiunge la scommessa <code>newBet</code> all'ArrayList<Bet> di scommesse del giocatore
	 * @param newBet scommessa da aggiungere al giocatore
	 */
	public void addPlayerBet(Bet newBet){
		this.playerBets.add(newBet);
	}
	
	/**
	 * rimuove <code>actionCardToBeRemoved</code> dall'ArrayList di carte azione del giocatore 
	 * @param actionCardToBeRemoved carta azione che si vuole rimuovere
	 */
	public void removeThisActionCard(ActionCard actionCardToBeRemoved){
		for(int i=0; i<playerActionCards.size(); i++){
			if(playerActionCards.get(i).equals(actionCardToBeRemoved)){
				playerActionCards.remove(i);
			}
		}
	}
	
	/**
	 * Aggiunge al giocatore una carta azione
	 * @param actionCardspassed carta azione da aggiungere
	 */
	public void setActionCards(ArrayList<ActionCard> actionCardspassed){
		this.playerActionCards = actionCardspassed;
	}
	
	/**
	 * Restituisce il nome del giocatore
	 * @return nome del giocatore
	 */
	public String getPlayerName(){
		return this.playerName;
	}
	
	/**
	 * Restituisce la scuderia del giocatore
	 * @return scuderia del giocatore
	 */
	public StableCard getPlayerStable(){
		return this.playerStable.clone();
	}
	
	/**
	 * Restituisce la quantità di danari posseduta del giocatore
	 * @return danari posseduti dal giocatore
	 */
	public Integer getPlayerDanariAmount(){
		return this.playerDanariAmount;
	}
	
	/**
	 * Restituisce la carta personaggio posseduta dal giocatore
	 * @return carta personaggio posseduta dal giocatore
	 */
	public CharacterCard getPlayerCharacter(){
		return this.playerCharacter.clone();
	}
	
	/**
	 * Restituisce il numero di punti vittoria posseduti dal giocatore
	 * @return punti vittoria posseduti dal giocatore
	 */
	public Integer getPlayerVictoryPoints(){
		return this.playerVictoryPoints;
	}
	
	/**
	 * Restituisce le carte azione possedute dal giocatore
	 * @return ArrayList delle carte azione possedute dal giocatore
	 */
	public ArrayList<ActionCard> getPlayerActionCards(){
		ArrayList<ActionCard> copyOfPlayerActionCards = new ArrayList<ActionCard>();
		for(int i=0; i<playerActionCards.size(); i++){
			copyOfPlayerActionCards.add(playerActionCards.get(i).clone());
		}
		return copyOfPlayerActionCards;
	}
	
	/**
	 * Resrtituisce le scommesse effettuate dal giocatore
	 * @return ArrayList delle scommesse effettuate dal giocatore
	 */
	public ArrayList<Bet> getPlayerBets(){
		ArrayList<Bet> copyOfPlayerBets = new ArrayList<Bet>();
		for(int i=0; i<playerBets.size(); i++){
			copyOfPlayerBets.add(playerBets.get(i).clone());
		}
		return copyOfPlayerBets;
	}
	
	/**
	 * Dichiara vinta una delle scommesse del giocatore
	 * @param betIndex indice della scommessa all'interno dell'array delle scommesse del giocatore
	 */
	public void setWonBet(int betIndex){
		playerBets.get(betIndex).setBetWon(true);
	}
	
	/**
	 * Incrementa del valore <code>danariToAdd</code> la quantità di danari del giocatore.
	 * @param danariToAdd danari da aggiungere
	 */
	public void increasePlayerDanariAmount(Integer danariToAdd){
		this.playerDanariAmount += danariToAdd;
	}
	
	/**
	 * Decrementa la quantità di danari del giocatore del valore <code>danariToSubtract</code>
	 * @param danariToSubtract quantità di danari da sottrarre
	 * @throws IllegalStateException lanciata se la quantità di danari del giocatore non è sufficiente
	 */
	public void decreasePlayerDanariAmount(Integer danariToSubtract){
		if(this.playerDanariAmount>=danariToSubtract){
			this.playerDanariAmount -= danariToSubtract;
		}else{
			throw new IllegalStateException("You don't have enough Danari for this bet!");
		}
	}
	
	/**
	 * Aumenta il numero dei punti vittoria del giocatore del valore <code>victoryPointsToAdd</code>
	 * @param victoryPointsToAdd punti vittoria da aggiungere
	 */
	public void increasePlayerVictoryPoints(Integer victoryPointsToAdd){
		this.playerVictoryPoints += victoryPointsToAdd;
	}
	
	/**
	 * Decrementa il numero di punti vittoria del giocatore del valore <code>victoryPointsToSubtract</code>
	 * @param victoryPointsToSubtract quantità di punti vittoria da sottrarre
	 * @throws IllegalStateException lanciata se la quantità di punti vittoria del giocatore non è sufficiente
	 */
	public void decreasePlayerVictoryPoints(Integer victoryPointsToSubtract){
		if(this.playerVictoryPoints>=victoryPointsToSubtract){
			this.playerVictoryPoints -= victoryPointsToSubtract;
		}else{
			throw new IllegalStateException("You don't have enough Victory Points!! You loose the game!");
		}
	}
	
	/**
	 * Calcola e restituisce la scommessa minima
	 * @return scommessa minima
	 */
	public Integer getMinimumBet(){
		return this.playerVictoryPoints*100;
	}
	/**
	 * Elimina tutte le scommesse del giocatore
	 * <p>usato quando viene iniziato un nuovo round di corsa
	 */
	//chiamato da game per resettare le scommesse quando inizia un nuovo round
	public void resetPlayerBets(){
		playerBets=new ArrayList<Bet>();
	}
}