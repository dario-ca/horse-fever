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

public class BetTypeButtonHandler implements ActionListener {
	private final BetsManagerPopUp betsManager;
	
    public BetTypeButtonHandler(BetsManagerPopUp betsManager){
		this.betsManager = betsManager;
	}
    
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("betToWin"))
			{
			betsManager.updateBetType(true);
			}
		if(e.getActionCommand().equals("betToShow"))
			{
			betsManager.updateBetType(false);
			}
	}
}
