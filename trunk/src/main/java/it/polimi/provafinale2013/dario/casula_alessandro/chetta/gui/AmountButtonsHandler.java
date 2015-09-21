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

public class AmountButtonsHandler implements ActionListener{
	private final BetsManagerPopUp betsManager;
	
	public AmountButtonsHandler(BetsManagerPopUp betsManager){
		this.betsManager = betsManager;
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("100"))
			{
			betsManager.updateBetAmount(100);
			}
		if(e.getActionCommand().equals("500"))
			{
			betsManager.updateBetAmount(500);
			}
		if(e.getActionCommand().equals("1000"))
			{
			betsManager.updateBetAmount(1000);
			}
	}

}
