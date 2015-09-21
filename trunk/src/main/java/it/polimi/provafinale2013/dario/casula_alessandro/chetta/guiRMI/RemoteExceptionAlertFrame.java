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

public class RemoteExceptionAlertFrame {
	public RemoteExceptionAlertFrame(String error){
		JDialog popUpFrame = new JDialog();
		JPanel popUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		popUpPanel.add(new JLabel(error));
		popUpFrame.add(popUpPanel);
		popUpFrame.pack();
		popUpFrame.setLocationRelativeTo(null);
		popUpFrame.setVisible(true);
	}
	
	public RemoteExceptionAlertFrame(){
		JDialog popUpFrame = new JDialog();
		JPanel popUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


		popUpFrame.add(popUpPanel);
		popUpFrame.pack();
		popUpFrame.setLocationRelativeTo(null);
		popUpFrame.setVisible(true);
	}
}
