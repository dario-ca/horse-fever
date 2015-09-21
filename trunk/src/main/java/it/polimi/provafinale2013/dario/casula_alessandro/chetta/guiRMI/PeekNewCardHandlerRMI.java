/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.rmi.ControllerHorseFeverRMI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PeekNewCardHandlerRMI implements ActionListener {
	
	private final ControllerHorseFeverRMI modelController;

	public PeekNewCardHandlerRMI(ControllerHorseFeverRMI modelController){
		this.modelController=modelController;
	}
	
	public void actionPerformed(ActionEvent e){
		try{

			modelController.continueGame();
		} catch (Exception ex) {
			new RemoteExceptionAlertFrame("errore nel continue in peeknewcard"+ex.getMessage());
		}
	}
}
