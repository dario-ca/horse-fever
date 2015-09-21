/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui.PopUp;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.rmi.ControllerHorseFeverRMI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ActionCardsManagerPopUpRMI implements PopUp{
	private final ControllerHorseFeverRMI modelController;
	private final GuiHorseFeverRMI gui;
	private JFrame actionCardsManagerFrame;
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel stableChoicePanel;
	private JLabel selectedActionCardLabel;
	private ActionCard selectedActionCard;

	
	
	public ActionCardsManagerPopUpRMI(ControllerHorseFeverRMI modelController, GuiHorseFeverRMI gui) {
		this.modelController=modelController;
		this.gui=gui;
		setup();
	}
	
	private void setup(){
		actionCardsManagerFrame = new JFrame("Horse Fever - posizionamento carte azione");
		actionCardsManagerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		actionCardsManagerFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(GuiRMI.icona));
		
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		topPanel = createTopPanel();
		centerPanel = createCenterPanel();
		stableChoicePanel = createStableChoicePanel();
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(stableChoicePanel,BorderLayout.SOUTH);
		actionCardsManagerFrame.add(mainPanel);
		actionCardsManagerFrame.pack();
		actionCardsManagerFrame.setLocationRelativeTo(null);
		actionCardsManagerFrame.setVisible(true);
	}
	
	private JPanel createTopPanel(){
		//acquisisco le/la carte/a azione del giocatore corrente
		List<ActionCard> currentPlayerActionCards;
		try {
			currentPlayerActionCards = modelController.getPlayerActionCards(gui);

		//salvo l'action card di default
		selectedActionCard=currentPlayerActionCards.get(0);
		//inizializzo il top panel
		topPanel = new JPanel(new GridLayout(2,1));

		//creo il pannello per il nome
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			namePanel.add(new JLabel(modelController.getPlayerName(gui)+" posa le carte azione"));

		//creo il pannello dei bottoni
		JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
		//creo l'action listener associato ai bottoni
		ActionCardsSelectionButtonsHandlerRMI actionCardsSelectionButtonsHandler = new ActionCardsSelectionButtonsHandlerRMI(this);

		//creo i bottoni
		for(int i=0; i<currentPlayerActionCards.size(); i++)
		{
			JButton button = new JButton(currentPlayerActionCards.get(i).getCardName());
			button.addActionListener(actionCardsSelectionButtonsHandler);
			button.setActionCommand("selectionButton"+i);
			buttonPanel.add(button);
		}

		topPanel.add(namePanel);
		topPanel.add(buttonPanel);
		return topPanel;
		} catch (Exception e) {
			new RemoteExceptionAlertFrame("Errore in ActionCardsManagerPopUpRMI.createTopPanel() ");
		}
		return null;
	}
	
	private JPanel createCenterPanel(){
		centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		selectedActionCardLabel = new JLabel();
		selectedActionCardLabel.setIcon(selectedActionCard.getCardFrontImage());
		centerPanel.add(selectedActionCardLabel);
		return centerPanel;
	}
	
	private JPanel createStableChoicePanel(){
		StableChoiceButtonHandlerRMI stableChoiceButtonHandler = new StableChoiceButtonHandlerRMI(this);
		JPanel stableChoicePanel = new JPanel(new GridLayout(6,1));
		JButton blackStable = new JButton("Nero");
		blackStable.addActionListener(stableChoiceButtonHandler);
		blackStable.setActionCommand("BLACK");
		JButton blueStable = new JButton("Blu");
		blueStable.setActionCommand("BLUE");
		blueStable.addActionListener(stableChoiceButtonHandler);
		JButton greenStable = new JButton("Verde");
		greenStable.setActionCommand("GREEN");
		greenStable.addActionListener(stableChoiceButtonHandler);
		JButton redStable = new JButton("Rosso");
		redStable.setActionCommand("RED");
		redStable.addActionListener(stableChoiceButtonHandler);
		JButton yellowStable = new JButton("Giallo");
		yellowStable.setActionCommand("YELLOW");
		yellowStable.addActionListener(stableChoiceButtonHandler);
		JButton whiteStable = new JButton("Bianco");
		whiteStable.addActionListener(stableChoiceButtonHandler);
		whiteStable.setActionCommand("WHITE");
		stableChoicePanel.add(blackStable);
		stableChoicePanel.add(blueStable);
		stableChoicePanel.add(greenStable);
		stableChoicePanel.add(redStable);
		stableChoicePanel.add(yellowStable);
		stableChoicePanel.add(whiteStable);
		return stableChoicePanel;
		
	}
	
	public void updateStableChosen(Color selectedColor){
		try{
		modelController.addActionCardOnLane(selectedActionCard, selectedColor);
    } catch (Exception e) {
        new RemoteExceptionAlertFrame("Errore in updateStableChosen");
    }
		actionCardsManagerFrame.dispose();
	}
	
	public void updateActionCardImage(int actionCardIndex){
		//acquisisco le/la carte/a azione del giocatore corrente
		try{
		List<ActionCard> currentPlayerActionCards = modelController.getPlayerActionCards(gui);

		//imposto l'immagine
		selectedActionCardLabel.setIcon(currentPlayerActionCards.get(actionCardIndex).getCardFrontImage());
	    } catch (Exception e) {
	        new RemoteExceptionAlertFrame("Errore in updateActionCardImage");
	    }
		}
}
