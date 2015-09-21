/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui.*;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.rmi.ControllerHorseFeverRMI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BetsManagerPopUpRMI implements PopUp{
	private JFrame betsManagerFrame;
	private JPanel mainPanel;
	private JButton confirmationButton;
	private final ControllerHorseFeverRMI modelController;
	private final GuiHorseFeverRMI gui;
	private Boolean isMandatory;
	private TextField betAmountTextField;
	private JLabel stableChosen;
	private JLabel typeChosen;
	private Boolean isBetToWin=true;
	private String error;
	
	public BetsManagerPopUpRMI(ControllerHorseFeverRMI modelController, GuiHorseFeverRMI gui, Boolean isMandatory, String error){
		this.gui = gui;
		this.modelController = modelController;
		this.isMandatory = isMandatory;
		this.error = error;
		setup();
	}
	

	public BetsManagerPopUpRMI(ControllerHorseFeverRMI modelController, GuiHorseFeverRMI gui, Boolean isMandatory){
		this.gui = gui;
		this.modelController = modelController;
		this.isMandatory = isMandatory;
		this.error = "";
		setup();
	}
	
	private void setup(){
		betsManagerFrame = new JFrame("Horse Fever - inserimento scommesse");
		betsManagerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		betsManagerFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(GuiRMI.icona));
		betsManagerFrame.setLocationRelativeTo(null);
		betsManagerFrame.setSize(400, 320);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		//estraggo le info sul giocatore
		try {
		String currentPlayerName = modelController.getPlayerName(gui);
		String currentPlayerMinimumBetAmount = modelController.getCurrentPlayerMinimumBetAmount();
		//panel che comunica chi deve scommettere e la scommessa minima
		JPanel playerDataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//compongo la label dell'header
		JLabel headerLabel = new JLabel();
		String header = new String("Scommessa di "+currentPlayerName);

		if(!error.isEmpty())
			{
				header=header.concat(", ");
				header=header.concat(error);
				headerLabel.setForeground(Color.RED);
			}
		headerLabel.setText(header);
		playerDataPanel.add(headerLabel);
        
		//panel per la scelta dell'ammontare
		//creo il listener per questi bottoni
		AmountButtonsHandlerRMI amountButtonHandler = new AmountButtonsHandlerRMI(this);
		JPanel amountChoicePanel = new JPanel(new GridLayout(4,1));
		JButton plus100 = new JButton("+100");
		plus100.addActionListener(amountButtonHandler);
		plus100.setActionCommand("100");
		JButton plus500 = new JButton("+500");
		plus500.addActionListener(amountButtonHandler);
		plus500.setActionCommand("500");
		JButton plus1000 = new JButton("+1000");
		plus1000.addActionListener(amountButtonHandler);
		plus1000.setActionCommand("1000");
		betAmountTextField = new TextField(currentPlayerMinimumBetAmount);
		betAmountTextField.setEditable(false);
		amountChoicePanel.add(plus100);
		amountChoicePanel.add(plus500);
		amountChoicePanel.add(plus1000);
		amountChoicePanel.add(betAmountTextField);
		
		//panel per la scelta della lane
		StableChoiceButtonHandlerRMI stableChoiceButtonHandler = new StableChoiceButtonHandlerRMI(this);
		JPanel stableChoicePanel = new JPanel(new GridLayout(7,1));
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
		
		stableChosen = new JLabel();
		stableChoicePanel.add(blackStable);
		stableChoicePanel.add(blueStable);
		stableChoicePanel.add(greenStable);
		stableChoicePanel.add(redStable);
		stableChoicePanel.add(yellowStable);
		stableChoicePanel.add(whiteStable);
		stableChoicePanel.add(stableChosen);
		//panel per la scelta del tipo di scommessa
		BetTypeButtonHandlerRMI betTypeButtonHandler = new BetTypeButtonHandlerRMI(this);
		JPanel betTypeChoice = new JPanel(new GridLayout(3,1));
		typeChosen = new JLabel();
		typeChosen.setIcon(new ImageIcon(GuiRMI.betToWinLogo));
		JButton betToWin = new JButton("Vincente");
		JButton betToShow = new JButton("Piazzato");
		betToWin.addActionListener(betTypeButtonHandler);
		betToWin.setActionCommand("betToWin");
		betToShow.addActionListener(betTypeButtonHandler);
		betToShow.setActionCommand("betToShow");
		betTypeChoice.add(betToWin);
		betTypeChoice.add(betToShow);
		betTypeChoice.add(typeChosen);
		//panel per i bottoni finali
		ManageBetButtonHandlerRMI manageBetButtonHandler = new ManageBetButtonHandlerRMI(this);
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		confirmationButton = new JButton("Conferma");
		confirmationButton.setEnabled(false);
		confirmationButton.addActionListener(manageBetButtonHandler);
		confirmationButton.setActionCommand("confirm");
		JButton cancelBetButton = new JButton("Salta Scommessa");
		cancelBetButton.addActionListener(manageBetButtonHandler);
		cancelBetButton.setActionCommand("cancel");
		if(isMandatory)
			{
			cancelBetButton.setEnabled(false);
			}
		bottomPanel.add(confirmationButton);
		bottomPanel.add(cancelBetButton);
		
		mainPanel.add(playerDataPanel,BorderLayout.NORTH);
		mainPanel.add(amountChoicePanel,BorderLayout.WEST);
		mainPanel.add(stableChoicePanel,BorderLayout.CENTER);
		mainPanel.add(betTypeChoice,BorderLayout.EAST);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		betsManagerFrame.add(mainPanel);
		betsManagerFrame.setVisible(true);
		} catch (Exception e) {
            new RemoteExceptionAlertFrame();
        }
	}
	
	public void updateBetAmount(Integer plusThis){
		Integer betAmountInteger = Integer.parseInt(betAmountTextField.getText());
		betAmountInteger += plusThis;
		betAmountTextField.setText(betAmountInteger.toString());
	}
	
	public void updateBetType(Boolean isToWin){
		if(isToWin)
		{
			typeChosen.setIcon(new ImageIcon(GuiRMI.betToWinLogo));
		}
		else
			{
			typeChosen.setIcon(new ImageIcon(GuiRMI.betToShowLogo));
			}
		this.isBetToWin=isToWin;
	}
	
	public void updateStableChosen(Color colorOfStable){
		stableChosen.setBackground(colorOfStable);
		stableChosen.setOpaque(true);
		confirmationButton.setEnabled(true);
	}
	
	public void close(){
		betsManagerFrame.dispose();
		try{
			modelController.addBet();
		}catch(Exception e){
			new RemoteExceptionAlertFrame();
		}
	}
	
	public void confirmBet(){
		//organizzo i dati da comunicare al controller
		Integer betAmount = Integer.parseInt(betAmountTextField.getText());
		Color stableColor = stableChosen.getBackground();
		try{
			modelController.addBet(stableColor, betAmount, isBetToWin);
		}catch(Exception e){
			new RemoteExceptionAlertFrame("Errore nella presa della scommessa");
			e.printStackTrace();
		}
		betsManagerFrame.dispose();
	}
}
