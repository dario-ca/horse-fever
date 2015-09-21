/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionType;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.CharacterCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.Effect;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.MovementCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;

import java.awt.Color;
import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;



import org.jdom2.*; 
import org.jdom2.input.*;

/**
 * La classe <code>CardGenerator</code> si occupa di creare 
 * tutte le carte del gioco (e quindi tutti i relativi mazzi).
 * Tutte le informazioni necessarie sono estratte
 * da file di testo strutturati secondo linguaggio XML.
 */
public class CardGenerator {
    private static final String actionCardPath = "XML/ActionCards.xml";
    private static final String characterCardPath = "XML/CharacterCards.xml";
    private static final String stableCardPath = "XML/StableCards.xml";
    private static final String movementCardPath = "XML/MovementCards.xml";    
    
    private static final String pawnImagesPath = "img/pawnImages/";
    private static final String blackPawn = "blackPawn.jpg";
    private static final String bluePawn = "bluePawn.jpg";
    private static final String greenPawn = "greenPawn.jpg";
    private static final String redPawn = "redPawn.jpg";
    private static final String yellowPawn = "yellowPawn.jpg";
    private static final String whitePawn = "whitePawn.jpg";
    
    /**
     * Crea il mazzo delle carte movimento.
     * @return mazzo delle carte movimento
     */
	public static ArrayList<MovementCard> generateMovementCardDeck(){
		ArrayList<MovementCard> newMovementCardDeck = new ArrayList<MovementCard>();
		
		try {
			  //Creo un SAXBuilder  
		      SAXBuilder builder = new SAXBuilder(); 
		      Document document = builder.build(new File(movementCardPath)); 
		      
		      //prendo la radice
		      Element root = document.getRootElement();
		      
		      //Estraggo i figli dalla radice 
		      List<Element> children = root.getChildren(); 
		      Iterator<Element> iterator = children.iterator();
		      
	 
		      while(iterator.hasNext()){ 
		         Element item = (Element)iterator.next(); 
		        
		         Integer cardNumber = Integer.decode(item.getAttributeValue("number"));

		         ArrayList<Integer> allMovements = new ArrayList<Integer>();
		         
		         Integer movement = Integer.decode(item.getChildText("highOdds2"));
		         allMovements.add(movement);
		         movement = Integer.decode(item.getChildText("highOdds3"));
		         allMovements.add(movement);
		         movement = Integer.decode(item.getChildText("highOdds4"));
		         allMovements.add(movement);
		         movement = Integer.decode(item.getChildText("lowOdds5"));
		         allMovements.add(movement);
		         movement = Integer.decode(item.getChildText("lowOdds6"));
		         allMovements.add(movement);
		         movement = Integer.decode(item.getChildText("lowOdds7"));
		         allMovements.add(movement);
		         
		         ImageIcon cardFrontImage = new ImageIcon("img/Movimento/".concat(item.getChildText("front")));

		         MovementCard newMovementCard = new MovementCard(cardNumber, cardFrontImage, allMovements);
		         newMovementCardDeck.add(newMovementCard);
		      } 
		      
			}
			catch (Exception e) { 
					Game.logicLogger.severe("errore nella lettura dei file XML");
			    } 
		return newMovementCardDeck;
	}
	
	/**
     * Crea il mazzo delle carte scuderia.
     * @return mazzo delle carte scuderia
     */
	public static ArrayList<StableCard> generateStableCardDeck(){
		ArrayList<StableCard> newStableCardDeck = new ArrayList<StableCard>();
		
		try {
			  //Creo un SAXBuilder  
		      SAXBuilder builder = new SAXBuilder(); 
		      Document document = builder.build(new File(stableCardPath)); 
		      
		      //prendo la radice
		      Element root = document.getRootElement();
		      
		      //Estraggo i figli dalla radice 
		      List<Element> children = root.getChildren(); 
		      Iterator<Element> iterator = children.iterator();
		      
	 
		      while(iterator.hasNext()){ 
		         Element item = (Element)iterator.next(); 
		         String cardLogoPath = item.getAttributeValue("logo");
		         Integer cardNumber = Integer.decode(item.getAttributeValue("number"));
		         String cardName = item.getChildText("name");
		         String frontCardPath = item.getChildText("front");
		         String stableColorString = item.getChildText("color");
		         Color stableColor = recogniseColorFromString(stableColorString);

		         ImageIcon cardFrontImage = new ImageIcon(frontCardPath);
		         ImageIcon cardLogoImage = new ImageIcon(cardLogoPath);
		         StableCard newStableCard = new StableCard(cardName, cardNumber, cardFrontImage, stableColor, cardLogoImage);
		         newStableCardDeck.add(newStableCard);
		      } 
		      
			}
			catch (Exception e) { 
				Game.logicLogger.severe("errore nella lettura dei file XML");
			    } 
		return newStableCardDeck;
	}
	
	/**
     * Crea il mazzo delle carte personaggio.
     * @return mazzo delle carte personaggio
     */
	public static ArrayList<CharacterCard> generateCharacterCardDeck(){
		ArrayList<CharacterCard> newCharacterCardDeck = new ArrayList<CharacterCard>();
		
		try {
			  //Creo un SAXBuilder  
		      SAXBuilder builder = new SAXBuilder(); 
		      Document document = builder.build(new File(characterCardPath)); 

		      //prendo la radice
		      Element root = document.getRootElement();
		      //Estraggo i figli dalla radice 
		      List<Element> children = root.getChildren(); 
		      Iterator<Element> iterator = children.iterator();
		      
	 
		      while(iterator.hasNext()){ 
		         Element item = (Element)iterator.next(); 		         
		         Integer cardNumber = Integer.decode(item.getAttributeValue("number")); 
		         String cardName = item.getChildText("name");
		         String frontImagePath = item.getChildText("front");
		         Integer characterDanariAmount = Integer.decode(item.getChildText("danari"));
		         Integer stableOdds = Integer.decode(item.getChildText("odds"));
		         
		         ImageIcon cardFrontImage = new ImageIcon(frontImagePath);
		         
		         CharacterCard newCharacterCard = new CharacterCard(cardName, cardNumber, cardFrontImage, characterDanariAmount, stableOdds);
		         newCharacterCardDeck.add(newCharacterCard);
		         
		      } 
		      
			}
			catch (Exception e) { 
				Game.logicLogger.severe("errore nella lettura dei file XML");
			    } 
		return newCharacterCardDeck;
	}
	
	/**
     * Crea il mazzo delle carte azione.
     * @return mazzo delle carte azione
     */
	public static ArrayList<ActionCard> generateActionCardDeck() {
		ArrayList<ActionCard> newActionCardDeck = new ArrayList<ActionCard>();
		
		try {
		  //Creo un SAXBuilder  
	      SAXBuilder builder = new SAXBuilder(); 
	      Document document = builder.build(new File(actionCardPath)); 
	      
	      //prendo la radice
	      Element root = document.getRootElement();
	      //prendo l'attributo della root che è l'indirizzo dell'immagine della carta sul retro 
	      String backImagePath = root.getAttributeValue("back");
	      //creo l'immagine con questo percorso
	      ImageIcon cardBackImage = new ImageIcon(backImagePath);
	      //Estraggo i figli dalla radice 
	      List<Element> children = root.getChildren();
	      Iterator<Element> iterator = children.iterator();
	      
 
	      while(iterator.hasNext()){ 
	         Element item = (Element)iterator.next(); 	         
	         String cardNumberString = item.getAttributeValue("number");
	         Integer cardNumber = Integer.decode(cardNumberString);
	         String cardName = item.getChildText("name");
	         String actionCharacter = item.getChildText("letter");
	         Effect actionEffect = recogniseEffectFromString(item.getChildText("effect"));
	         ActionType actionType = recogniseActionTypeFromString(item.getChildText("actionType"));
	         String actionStepsString = item.getChildText("steps");
	         String frontImagePath = item.getChildText("front");
	         Integer actionSteps = Integer.decode(actionStepsString);
	         Boolean isStepsAdder = recogniseBooleanFromString(item.getChildText("isStepsAdder"));
	         
	         ImageIcon cardFrontImage = new ImageIcon(frontImagePath);
	         
	         ActionCard newActionCard;
	         
	         newActionCard= new ActionCard(cardName, cardNumber, cardFrontImage, cardBackImage, actionCharacter, actionEffect, actionType, actionSteps, isStepsAdder);
	         newActionCardDeck.add(newActionCard);
	      } 
	      
		}
		catch (Exception e) { 
			Game.logicLogger.severe("errore nella lettura dei file XML");
		    } 
	      
	 return newActionCardDeck;
	}
	
	/**
	 * Converte una stringa indicante l'effetto di una carta azione
	 * nel relativo valore <code>Effect</code>
	 * @param effectToRecognise stringa da convertire
	 * @return elemento Effect convertito
	 * @see Effect
	 */
	private static Effect recogniseEffectFromString(String effectToRecognise){
		
		if(effectToRecognise.equals("POSITIVE"))
			{
			return Effect.POSITIVE;
			}
		if(effectToRecognise.equals("NEGATIVE"))
			{
			return Effect.NEGATIVE;	
			}
		return Effect.SPECIAL;
	}
	
	/**
	 * Converte una stringa indicante il tipo di azione di una carta azione
	 * nel relativo valore <code>ActionType</code>
	 * @param actionTypeToRecognise stringa da convertire
	 * @return elemento ActionType convertito
	 * @see ActionType
	 */
	private static ActionType recogniseActionTypeFromString(String actionTypeToRecognise){
		if(actionTypeToRecognise.equals("START"))
			{
			return ActionType.START;
			}
		if(actionTypeToRecognise.equals("SPRINT"))
			{
			return ActionType.SPRINT;
			}
		if(actionTypeToRecognise.equals("PHOTOFINISH"))
			{
			return ActionType.PHOTOFINISH;
			}
		if(actionTypeToRecognise.equals("FINISHLINE"))
			{
			return ActionType.FINISHLINE;
			}
		if(actionTypeToRecognise.equals("HELPLAST"))
			{
			return ActionType.HELPLAST;
			}
		if(actionTypeToRecognise.equals("STOPFIRST"))
			{
			return ActionType.STOPFIRST;
			}
		if(actionTypeToRecognise.equals("ODDSUP"))
			{
			return ActionType.ODDSUP;
			}
		if(actionTypeToRecognise.equals("ODDSDOWN"))
			{
			return ActionType.ODDSDOWN;
			}
		if(actionTypeToRecognise.equals("REMOVEPOSITIVE"))
			{
			return ActionType.REMOVEPOSITIVE;
			}

			return ActionType.REMOVENEGATIVE;
		
	}
	
	/**
	 * Converte una stringa "true" o "false" nel relativo 
	 * valore boolean <code>true</code> o <code>false</code>
	 * @param booleanToRecognise stringa da riconoscere
	 * @return valore convertito
	 */
	private static Boolean recogniseBooleanFromString(String booleanToRecognise){
		if(booleanToRecognise.equals("true"))
			{
			return true;
			}
		return false;
	}
	
	/**
	 * Converte una stringa indicante un colore, nel relativo valore <code>Color</code>
	 * @param colorToRecognise stringa da riconoscere
	 * @return elemento convertito in <code>Color</code>
	 * @see Color
	 */
	public static Color recogniseColorFromString(String colorToRecognise){
		if(colorToRecognise.equals("BLACK"))
			{
			return Color.BLACK;
			}
		if(colorToRecognise.equals("BLUE"))
			{
			return Color.BLUE;
			}
		if(colorToRecognise.equals("GREEN"))
			{
			return Color.GREEN;
			}
		if(colorToRecognise.equals("RED"))
			{
			return Color.RED;
			}
		if(colorToRecognise.equals("YELLOW"))
			{
			return Color.YELLOW;
			}
			return Color.WHITE;
		
	}
	
	/**
	 * restituisce la scuderia del colore indicato
	 * @param colorToFind colore della scuderia da cercare
	 * @return scuderia del colore corrispondente
	 */
	public static StableCard getStableCardOfThisColor(Color colorToFind){
		ArrayList<StableCard> stableCards = generateStableCardDeck();
		for(StableCard stableCard: stableCards){
			if(stableCard.getStableColor().equals(colorToFind))
				{
				return stableCard;
				}
		}
		return null;
	}
	
	/**
	 * Restituisce il percorso dell'immagine della pedina della stable indicata in ingresso.
	 * @param indexOfStable numero della stable da 0 a 5
	 * @return percorso dell'immagine della pedina
	 */
	public static String giveThePawnImagePath(Integer indexOfStable){
		String imagePath=null;
		switch (indexOfStable) {
		case 0: imagePath=pawnImagesPath+blackPawn;			
			break;
		case 1: imagePath=pawnImagesPath+bluePawn;
			break;
		case 2: imagePath=pawnImagesPath+greenPawn;
			break;
		case 3: imagePath=pawnImagesPath+redPawn;
			break;
		case 4: imagePath=pawnImagesPath+yellowPawn;
			break;
		case 5: imagePath=pawnImagesPath+whitePawn;
			break;
		}
		return imagePath;
	}
	
	/**
	 * Converte l'indice di una scuderia nel relativo colore
	 * @param stableNumberIndex indice della scuderia di cui sapere il colore
	 * @return colore della scuderia
	 * @see Color
	 */
	public static Color colorOfStable(Integer stableNumberIndex){
		switch(stableNumberIndex){
		case 0: return Color.BLACK;
		case 1:	return Color.BLUE;
		case 2:	return Color.GREEN;
		case 3: return Color.RED;
		case 4: return Color.YELLOW;
		}
		return Color.WHITE;
	}
	
}
