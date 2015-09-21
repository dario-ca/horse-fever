/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.MovementCard;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface GuiHorseFeverRMI extends Remote{
	
	public void setStableTabToShow(Integer decode) throws RemoteException;
	public void setMainFrameVisible(Boolean isVisible) throws RemoteException;
	public void updateCurrentData() throws RemoteException;
	public void startBetsManagerPopUp(String error, Boolean b) throws RemoteException;
	public void startActionCardManagerPopUp() throws RemoteException;
	public void controlButtonEnabled(Boolean b) throws RemoteException;
	public void setShowActionCards(Boolean b) throws RemoteException;
	public void setControlButtonName(String string) throws RemoteException;
	public void setMovementCard(MovementCard movementCard) throws RemoteException;
	public void setDicesColor(ArrayList<Color> colorArray) throws RemoteException;
	public void startComunicateLoosersPopUp(
			ArrayList<Integer> loosersPlayerIndexes) throws RemoteException;
	public void startComunicateTheWinner(String currentWinner) throws RemoteException;
	public void startYouLoseFrame() throws RemoteException;
}
