/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.Main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class InitialWindow implements ActionListener{
	JFrame frame = new JFrame("Horse Fever - modalità di gioco");
	public InitialWindow(){
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton localButton = new JButton("Locale");
		JButton remoteButton = new JButton("Remoto");
		localButton.addActionListener(this);
		localButton.setActionCommand("local");
		remoteButton.addActionListener(this);
		remoteButton.setActionCommand("remote");
		mainPanel.add(localButton);
		mainPanel.add(remoteButton);
		frame.add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("local")){
			frame.dispose();
			Main.startLocalGame();
			return;
		}
		if(e.getActionCommand().equals("remote")){
			frame.dispose();
			Main.startRemoteGame();
			return;
		}
	}
}
