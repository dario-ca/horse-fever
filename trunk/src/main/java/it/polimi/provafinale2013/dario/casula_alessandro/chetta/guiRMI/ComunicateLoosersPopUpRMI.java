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

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class ComunicateLoosersPopUpRMI {
		
	
		public ComunicateLoosersPopUpRMI(ArrayList<Integer> loosersPlayerIndexes, ControllerHorseFeverRMI modelController){
			JDialog comunicateLoosersPopUpFrame = new JDialog();
			JPanel comunicateLoosersPopUpPanel = new JPanel(new GridLayout(loosersPlayerIndexes.size()+2, 1));
			
			comunicateLoosersPopUpPanel.add(new JLabel("I seguenti giocatori sono stati eliminati."));
			comunicateLoosersPopUpPanel.add(new JLabel("Non dispongono di Danari a sufficienza per sostenere la prossima scommessa."));

			for(int looserPlayerIndex: loosersPlayerIndexes){
				try{
					String playerName = modelController.getNameOfPlayer(looserPlayerIndex);
					comunicateLoosersPopUpPanel.add(new JLabel(playerName));
				} catch (Exception e) {
		            new RemoteExceptionAlertFrame();
		        }
			}
			
			comunicateLoosersPopUpFrame.add(comunicateLoosersPopUpPanel);
			comunicateLoosersPopUpFrame.pack();
			comunicateLoosersPopUpFrame.setLocationRelativeTo(null);
			comunicateLoosersPopUpFrame.setVisible(true);
		}
}
