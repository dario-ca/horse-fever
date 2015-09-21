/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.MovementCard;

import java.awt.Color;
import java.util.ArrayList;

public interface HorseFeverGUI {
	public void setMainFrameVisible(Boolean isVisible);
	public void updateCurrentData();
	public void startTakePlayers(String error);
	public void startBetsManagerPopUp(String error,Boolean isMandatory);
	public void startActionCardManagerPopUp();
	public void controlButtonEnabled(Boolean isEnabled);
	public void setMovementCard(MovementCard movementCard);
	public void setControlButtonName(String buttonName);
	public void disposeFrame();
	public void setDicesColor(ArrayList<Color> sprintDicesColor);
	public void startComunicateLoosersPopUp(ArrayList<Integer> loosersPlayerIndexes);
	public void startComunicateTheWinner(String winner);
	public void setShowActionCards(Boolean areActionCardsVisible);
	public void setStableTabToShow(Integer index);
}
