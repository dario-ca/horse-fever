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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class PlayerManagerPopUp implements ActionListener{
	//creo la finestra popup
	private JFrame playerInfoTaker = new JFrame("Horse Fever - inserimento giocatori");
	//Intestazione 
	private final String header="Inserire minimo 2 giocatori";
	//creo il pannello
	private JPanel playerInfoTakerPanel = new JPanel();
	//controller di riferimento
	private HorseFeverController modelController;
	//textfields
	private ArrayList<JTextField> players;
	//bottone
	private JButton confirmationButton;
	//intestazione o eventuale errore sara' contenuto in
	private String error;
	
	public PlayerManagerPopUp(HorseFeverController modelController, String error){
		this.modelController=modelController;
		//assegno il modello del MVC
		this.modelController=modelController;
		this.error = error;
		setup();
	}
	
	private void setup(){
	playerInfoTaker.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.icona));
	//setto il layout
   	playerInfoTakerPanel.setLayout(new GridLayout(14, 1));
   	//setto la dimensione della finestra
    playerInfoTaker.setSize(250, 400);
    playerInfoTaker.setResizable(false);
    //setto la label che conterra' l'intestazione o l'enevtuale errore
    JLabel headerLabel = new JLabel(header);
    if(!this.error.isEmpty())
    {
    	headerLabel = new JLabel(error);
    }
    //aggiungo l'header
    playerInfoTakerPanel.add(headerLabel);
    //inizializzo l'arraylist di textfields
    players = new ArrayList<JTextField>();
    //genero le text fiels e le aggiungo al panel
    for(int i=0; i<HorseFeverController.maxNumberOfPlayers; i++){
    	//creo una textfield da aggiungere all'array
    	JTextField player = new JTextField();
    	//la aggiungo all'array
    	players.add(player);
		playerInfoTakerPanel.add(new JLabel("Giocatore "+(1+i)));
		playerInfoTakerPanel.add(player);
	}
	//creo il bottone
	confirmationButton = new JButton("Avanti");
	playerInfoTaker.getRootPane().setDefaultButton(confirmationButton);
	//associo il bottone all'hendler
	confirmationButton.addActionListener(this);
	
	//aggiungo il bottone al panel
	playerInfoTakerPanel.add(confirmationButton);
	
	//aggiungo il pannello alla finestra
	playerInfoTaker.add(playerInfoTakerPanel);
	
	playerInfoTaker.setLocationRelativeTo(null);
	playerInfoTaker.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		//leggo ciò che l'utente ha inserito nelle textfield e inserisco i nuovi giocatori nel modello
		for(int i=0; i<HorseFeverController.maxNumberOfPlayers; i++){
			if(!players.get(i).getText().equals(""))
				{
					modelController.addNewPlayer(players.get(i).getText());
				}
		}
		//comunico al controllore che da adesso può iniziare a contare i turni perchè il numero di 
		//players è adesso  noto
		try {
			this.modelController.setTurnManager();
		}catch(Exception illegalStateException){
			this.modelController.continueGame("Numero di giocatori insufficiente!");
		}
		this.playerInfoTaker.setVisible(false);
		this.playerInfoTaker.dispose();
	   	
	}
}
