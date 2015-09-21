/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI;

import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class YouLooseFrame {
	public YouLooseFrame(){
		JDialog comunicateTheWinnerPopUpFrame = new JDialog();
		JPanel comunicateTheWinnerPopUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		comunicateTheWinnerPopUpPanel.add(new JLabel("HAI  PERSO!"));
		
		comunicateTheWinnerPopUpFrame.add(comunicateTheWinnerPopUpPanel);
		comunicateTheWinnerPopUpFrame.setLocationRelativeTo(null);
		comunicateTheWinnerPopUpFrame.pack();
		comunicateTheWinnerPopUpFrame.setVisible(true);
	}
}
