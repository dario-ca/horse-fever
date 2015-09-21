/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI;


import it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui.PopUp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StableChoiceButtonHandlerRMI implements ActionListener{
	private final PopUp popUp;

	
    public StableChoiceButtonHandlerRMI(PopUp betsManager){
		this.popUp = betsManager;
	}
    
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("BLACK"))
		{
			popUp.updateStableChosen(Color.BLACK);
		}
		if(e.getActionCommand().equals("BLUE"))
			{
			popUp.updateStableChosen(Color.BLUE);
			}
		if(e.getActionCommand().equals("GREEN"))
			{
			popUp.updateStableChosen(Color.GREEN);
			}
		if(e.getActionCommand().equals("RED"))
			{
			popUp.updateStableChosen(Color.RED);
			}
		if(e.getActionCommand().equals("YELLOW"))
			{
			popUp.updateStableChosen(Color.YELLOW);
			}
		if(e.getActionCommand().equals("WHITE"))
			{
			popUp.updateStableChosen(Color.WHITE);
			}
	}

}
