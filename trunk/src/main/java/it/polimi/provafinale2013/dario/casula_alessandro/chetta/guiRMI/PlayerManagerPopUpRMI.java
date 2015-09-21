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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.*;


public class PlayerManagerPopUpRMI implements ActionListener{
	//creo la finestra popup
	private JFrame playerInfoTaker = new JFrame("Horse Fever - inserimento giocatore");
	//Intestazione 
	private final String header="Inserire il nome del giocatore";
	//creo il pannello
	private JPanel playerInfoTakerPanel = new JPanel();
	//controller di riferimento
	private ControllerHorseFeverRMI modelController;
	//gui di riferimento
	private GuiHorseFeverRMI gui;
	//textfields
	JTextField player = new JTextField();
	//bottone
	private JButton confirmationButton;
	//intestazione o eventuale errore sara' contenuto in
	@SuppressWarnings("unused")
	private String error;
	private JLabel headerLabel;
	
	public PlayerManagerPopUpRMI(ControllerHorseFeverRMI modelController, GuiHorseFeverRMI gui, String error){
		this.modelController=modelController;
		this.gui=gui;
		//assegno il modello del MVC
		this.modelController=modelController;
		this.error = error;
		setup();
	}
	
	private void setup(){
	playerInfoTaker.setIconImage(Toolkit.getDefaultToolkit().getImage(GuiRMI.icona));
	//setto il layout
   	playerInfoTakerPanel.setLayout(new GridLayout(3, 1));
    //setto la label che conterra' l'intestazione o l'enevtuale errore
    headerLabel = new JLabel(header);
    //aggiungo l'header
    playerInfoTakerPanel.add(headerLabel);

	playerInfoTakerPanel.add(player);

	//creo il bottone
	confirmationButton = new JButton("Avanti");
	playerInfoTaker.getRootPane().setDefaultButton(confirmationButton);
	//associo il bottone all'hendler
	confirmationButton.addActionListener(this);
	
	//aggiungo il bottone al panel
	playerInfoTakerPanel.add(confirmationButton);
	
	//aggiungo il pannello alla finestra
	playerInfoTaker.add(playerInfoTakerPanel);
	playerInfoTaker.pack();
	playerInfoTaker.setLocationRelativeTo(null);
	playerInfoTaker.setVisible(true);
	
	}
	
	public void actionPerformed(ActionEvent e){
		if(!player.getText().equals(""))
		{
			try {
			
					// registro il client nel server
					try {

						modelController.registerGui(gui, player.getText());
						gui.setMainFrameVisible(true);
					} catch (RemoteException e1) {
						new RemoteExceptionAlertFrame("errore nell'aggiunta dell'utente, problemi di connessione. Spiacente.");
					}
				}catch(IllegalStateException illegalStateException){
					new RemoteExceptionAlertFrame("errore nell'aggiunta dell'utente, numero massimo di giocatori raggiunto. Spiacente.");
				}
		}else{
			headerLabel.setForeground(Color.RED);
			headerLabel.setText(header+ " ERRORE NEL NOME");
			return;
		}

		this.playerInfoTaker.setVisible(false);
		this.playerInfoTaker.dispose();
	   	
	}
}
