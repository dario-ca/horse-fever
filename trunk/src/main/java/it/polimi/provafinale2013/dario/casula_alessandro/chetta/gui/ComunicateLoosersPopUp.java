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

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ComunicateLoosersPopUp {
		
	
		public ComunicateLoosersPopUp(ArrayList<Integer> loosersPlayerIndexes, HorseFeverController modelController){
			JDialog comunicateLoosersPopUpFrame = new JDialog();
			JPanel comunicateLoosersPopUpPanel = new JPanel(new GridLayout(loosersPlayerIndexes.size()+2, 1));
			
			comunicateLoosersPopUpPanel.add(new JLabel("I seguenti giocatori sono stati eliminati."));
			comunicateLoosersPopUpPanel.add(new JLabel("Non dispongono di Danari a sufficienza per sostenere la prossima scommessa."));

			for(int looserPlayerIndex: loosersPlayerIndexes){
				String playerName = modelController.getNameOfPlayer(looserPlayerIndex);
				comunicateLoosersPopUpPanel.add(new JLabel(playerName));
			}
			
			comunicateLoosersPopUpFrame.add(comunicateLoosersPopUpPanel);
			comunicateLoosersPopUpFrame.pack();
			comunicateLoosersPopUpFrame.setLocationRelativeTo(null);
			comunicateLoosersPopUpFrame.setVisible(true);
		}

}
