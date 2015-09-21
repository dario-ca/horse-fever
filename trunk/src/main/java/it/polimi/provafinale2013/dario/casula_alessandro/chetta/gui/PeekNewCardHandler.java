/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.HorseFeverController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PeekNewCardHandler implements ActionListener {
	
	private final HorseFeverController modelController;

	public PeekNewCardHandler(HorseFeverController modelController){
		this.modelController=modelController;
	}
	
	public void actionPerformed(ActionEvent e){
		modelController.continueGame();
	}
}
