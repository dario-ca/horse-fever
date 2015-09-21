/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui;


import it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI.GuiHorseFeverRMI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;


public class ActionCardBottomButtonsHandler implements ActionListener {
	private HorseFeverGUI gui;
	private GuiHorseFeverRMI guiRMI;
	
	public ActionCardBottomButtonsHandler(GUI gui){
		this.gui=gui;
	}
	
	public ActionCardBottomButtonsHandler(GuiHorseFeverRMI guiRMI){
		this.guiRMI=guiRMI;
	}
	
	public void actionPerformed(ActionEvent e){
		if(gui!=null)
		{
			gui.setStableTabToShow(Integer.decode(e.getActionCommand()));
		}
		if(guiRMI!=null){
			try {
				guiRMI.setStableTabToShow(Integer.decode(e.getActionCommand()));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
	}
	}
}
