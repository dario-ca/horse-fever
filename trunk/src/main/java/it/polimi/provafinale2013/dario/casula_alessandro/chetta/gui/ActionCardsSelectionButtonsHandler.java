/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionCardsSelectionButtonsHandler implements ActionListener{
	private final ActionCardsManagerPopUp actionCardsManager;
	
	public ActionCardsSelectionButtonsHandler(ActionCardsManagerPopUp actionCardsManager){
		this.actionCardsManager=actionCardsManager;
	}

	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("selectionButton0"))
			{
			actionCardsManager.updateActionCardImage(0);
			}
		if(e.getActionCommand().equals("selectionButton1"))
			{
			actionCardsManager.updateActionCardImage(1);
			}
	}
}
