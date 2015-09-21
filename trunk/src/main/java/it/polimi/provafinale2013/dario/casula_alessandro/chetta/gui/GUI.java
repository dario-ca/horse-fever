/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.MovementCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.Bet;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.CardGenerator;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.HorseFeverController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;



public class GUI implements HorseFeverGUI{
	private JFrame frame = new JFrame("Horse Fever");
	private final String movementCardBackImage = "img/Movimento/retro.jpg";
	public static final  String betToWinLogo = "img/SegnaliniScommessa/Vincente.jpg";
	public static final  String betToShowLogo ="img/SegnaliniScommessa/Piazzata.jpg";
	public static final  String icona = "img/icona.jpg";
	private final String lanesBackgroundImage = "img/tabellone_piccolo_allargato2_piccolo_equidistante.jpg";
	private final Integer numberOfRows = 14;
	//variabile booleana che mi dice se devo mostrare il fronte delle carte azione o no
	Boolean areActionCardsVisible;
	//IMMAGINI & LABELS
    private ArrayList<JLabel> actionCardLabels;
    private ImageIcon characterCard;
    private JLabel characterCardLabel = new JLabel();
    private ImageIcon stableCard;
    private JLabel stableCardLabel = new JLabel();
    private ArrayList<JLabel> betsLabel = new ArrayList<JLabel>();
    private JLabel currentPlayerInfoLabel = new JLabel();
    private JLabel movementCardLabel;
    //insieme di label che mostrano le posizioni in quotazione
    private ArrayList<ArrayList<JLabel>> oddsLabels;
    //insieme di label che mostrano le posizioni di arrivo
    private ArrayList<JLabel> rankingLabels;
    //matrice di label che rappresenta il tabellone
    private ArrayList<ArrayList<JLabel>> tabLabels;
    //insieme delle label che contengono le informazioni sugli ltri utenti
    private ArrayList<JLabel> otherPlayersLabels;
    //insieme della label che conterranno le immagini delle carte azione posizionate sulle lane
    private ArrayList<ArrayList<JLabel>> lanesLabels;
    //è il bottone che fa muovere le pedine e lo sprint
    private JButton controlButton;
    //dadi sprint
    private ArrayList<JLabel> sprintDices;
    
    //PANEL
	private JPanel mainPanel = new JPanel();
	private JPanel leftPanel = new JPanel(); //va nel main
	private JPanel bottomPanel;
	private JPanel centerPanel = new JPanel(); //va nel main (contiene tabpanel e bottom panel)
	private ImagePanel tabPanel; //contiene il tabellone
	private JPanel oddsBlackBoardPanel; 
	private JPanel myCardsPanel;
	private JPanel myActionCardsPanel;
	private JPanel betsPanel;
	private ArrayList<JPanel> stablePanelArray;
	private ArrayList<JScrollPane> stablePaneArray;
	private JPanel otherPlayersPanel;
	//TabbedPane
	private JTabbedPane tabbedPane;
	//Riferimento al controllore del MVC
	private HorseFeverController modelController;
	
	public GUI(HorseFeverController modelController){
		this.areActionCardsVisible=false;
		this.modelController=modelController;
		this.modelController.registerGui(this);
		setup();
	}
	
    public void setup(){
    	//FRAME
		//imposto la chiusura alla pressione di x
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//imposto l'icona del frame
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(icona));		
		//blocco il resize del frame
		frame.setResizable(false);
		//PANEL
		//LAYOUT DEI PANEL
		//setto il layout di mainpanel
		mainPanel.setLayout(new BorderLayout());
		//setto il layout del panel di sinistra
		leftPanel.setLayout(new BorderLayout());
		//setto il layout del center panel contiene il tabellone e il bottom
		centerPanel.setLayout(new BorderLayout());
		
		//faccio creare il bottom panel
		createBottomPanel();
		//faccio creare la blackboard panel
		createOddsBlackBoardPanel();
		//faccio creare il panel del tabellone
		createTabPanel();
		//faccio creare il pannello che mostra la situazione degli altri giocatori
		createOtherPlayersPanel();
		//imposto i panel che vanno nel tabbedpane
		createMyActionCardsPanel();
		createMyCardsPanel();
		createBetsPanel();
		createStablePanelArray();
		createJTabbedPane();

		//aggiungo la blackBoardPanel al panel di sinistra
		leftPanel.add(oddsBlackBoardPanel,BorderLayout.NORTH);
		leftPanel.add(currentPlayerControl(),BorderLayout.CENTER);
		leftPanel.add(tabbedPane,BorderLayout.SOUTH);
		
		//aggiungo a center panel il tabellone e i bottoni
		centerPanel.add(tabPanel,BorderLayout.NORTH);
		centerPanel.add(bottomPanel,BorderLayout.CENTER);
		//aggiungo TABPANEL al panel
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		//aggiungo left panel al panel principale della lavagna al panel
		mainPanel.add(leftPanel,BorderLayout.WEST);
		
		//aggiungo il panel al frame
		frame.add(mainPanel);
		
		//posiziono al centro
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);
    }

    public void setMainFrameVisible(Boolean isVisible){
    	frame.setVisible(isVisible);
    }
    public void setStableTabToShow(Integer stableTabIndex){
    	int intStableTabIndex = stableTabIndex;
    	this.tabbedPane.setSelectedComponent(stablePaneArray.get(intStableTabIndex));
    }
    
    //Metodi che fanno partire le vadre finestre di dialogo
    public void startTakePlayers(String error){
    	new PlayerManagerPopUp(modelController, error);
    }
    
    public void startBetsManagerPopUp(String error, Boolean isMandatory){
    	new BetsManagerPopUp(modelController, isMandatory, error);
    }
    
    public void startActionCardManagerPopUp(){
    	new ActionCardsManagerPopUp(modelController);
    }
    
    public void startComunicateLoosersPopUp(ArrayList<Integer> loosersPlayerIndexes){
    	new ComunicateLoosersPopUp(loosersPlayerIndexes, modelController);
    }
    
    public void startComunicateTheWinner(String winner){
    	new ComunicateTheWinnerPopUp(winner);
    }
    
    //Metodi creatori/setter di panel__________________________________________________________________________________
   private void createJTabbedPane()
   {
	   tabbedPane= new JTabbedPane();
		//aggiungo a TABBEDPANE tutte le tabelle
		tabbedPane.add("mie carte", myCardsPanel);
		tabbedPane.add("mie carte azione", myActionCardsPanel);
		tabbedPane.add("scommesse!", betsPanel);
		//Aggiungo il pane dedicato alle info sugli altri giocatori
		tabbedPane.add("altri giocatori", otherPlayersPanel);
		//Aggiungo i pane dedicati alle lane
		for(int i=0; i<6; i++){
			tabbedPane.add(stablePaneArray.get(i));
			tabbedPane.setBackgroundAt(4+i,CardGenerator.colorOfStable(i));		
		}
   }
    private JPanel currentPlayerControl(){
    	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	panel.add(currentPlayerInfoLabel);
    	panel.setPreferredSize(new Dimension(100, 15));
    	controlButton = new JButton("Start");
    	controlButton.setEnabled(false);
    	controlButton.addActionListener(new PeekNewCardHandler(modelController));
    	panel.add(controlButton);
    	//setto i dadi
    	sprintDices = new ArrayList<JLabel>();
    	for(int i=0; i<2; i++)
    	{
    		JLabel sprintDie = new JLabel("  ");
    		sprintDie.setOpaque(true);
    		sprintDices.add(sprintDie);
    		panel.add(sprintDie);
    	}
    	return panel;
    } 
    
    private void createMyActionCardsPanel(){
    	//iniziallizzo
    	myActionCardsPanel = new JPanel();
		//setto il layout del panel myactioncards
		myActionCardsPanel.setLayout(new GridLayout(1, 2));
		//inizializzo le label
		actionCardLabels = new ArrayList<JLabel>();
		//metto le immagini
		for(int i=0; i<2; i++)
		{
			actionCardLabels.add(new JLabel());
			myActionCardsPanel.add(actionCardLabels.get(i));
    	}
		
    }
    
    private void createMyCardsPanel(){
    	//iniziallizzo
    	myCardsPanel = new JPanel();
		//setto il layout del panel mycards
		myCardsPanel.setLayout(new GridLayout(1,2));
		//metto le immagini
		myCardsPanel.add(characterCardLabel);
		myCardsPanel.add(stableCardLabel);
    }
    
    private void createBetsPanel(){
    	//inizializzo
    	betsPanel = new JPanel();
		//setto il layout del panel myBets
		betsPanel.setLayout(new GridLayout(18,1));
		//variabile per alternare il colore ogni 2
		Boolean changeColor = true;
		Color bgColor = new Color(200, 200, 200);
		
		//imposto scommesse
		for(int i=0; i<18; i++){
			if(i%3==0)
				{
				changeColor = !changeColor;
				}
			JLabel label = new JLabel();
			if(changeColor)
				{
				label.setBackground(bgColor);
				}
			label.setOpaque(true);
			betsLabel.add(label);
			betsPanel.add(label);
		}
    }
    
    private void createBottomPanel(){
    	//inizializzo
    	bottomPanel = new JPanel();
		//setto il layout del bottom panel
		bottomPanel.setLayout(new GridLayout(1,6));
		//istanza del handler per tutti i bottoni
		ActionCardBottomButtonsHandler actionCardBottomButtonsHandler = new ActionCardBottomButtonsHandler(this);
		//riempio bottom
		for(int i=0; i<HorseFeverController.numberOfStables; i++)
			{
			JButton buttonToAdd = new JButton("carte");
			buttonToAdd.addActionListener(actionCardBottomButtonsHandler);
			Integer iInteger = i;
			buttonToAdd.setActionCommand(iInteger.toString());
			 bottomPanel.add(buttonToAdd);
			 
			}
    }
    
    private void createStablePanelArray(){
    	//inizializzo
    	stablePanelArray = new ArrayList<JPanel>();
		//imposto i pannelli per le varie scuderie
    	//inizializzole label da mettere nei vari panel ci possono essere al massimo 12 carte azione su una lane
    	lanesLabels = new ArrayList<ArrayList<JLabel>>();
    	for(int i=0; i<HorseFeverController.numberOfStables;i++){
    		ArrayList<JLabel> labelArray = new ArrayList<JLabel>();
    		JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			stablePanelArray.add(p);
    		for(int j=0; j<12; j++){
    			labelArray.add(new JLabel());
    			stablePanelArray.get(i).add(labelArray.get(j));
    		}
    		lanesLabels.add(labelArray);
    	}
		//inizializzo
		stablePaneArray = new ArrayList<JScrollPane>();
		//imposto i pane per le varie scuderie
		for(int i=0; i<HorseFeverController.numberOfStables; i++){
			JScrollPane p = new JScrollPane(stablePanelArray.get(i));
			p.setPreferredSize(new Dimension(202, 350));
			p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER );
			stablePaneArray.add(p);
		}
    }
    
    private void createOddsBlackBoardPanel(){
    	//creo il panel che andra' in mezzo e conterra' le quotazioni
    	JPanel oddsPanel = createOddsPanel();
    	//creo il panel di destra che conterra' la classifica
    	JPanel rankingPanel = createRankingPanel();
    	
    	oddsBlackBoardPanel = new JPanel();
    	oddsBlackBoardPanel.setLayout(new BorderLayout());
    	oddsBlackBoardPanel.setPreferredSize(new Dimension(200, 200));
    	oddsBlackBoardPanel.setBorder(BorderFactory.createEtchedBorder(0));
    	//inizializzo la label che conterrà la carta iniziale
    	movementCardLabel = new JLabel(new ImageIcon(movementCardBackImage));
    	oddsBlackBoardPanel.add(movementCardLabel,BorderLayout.WEST);
    	oddsBlackBoardPanel.add(oddsPanel, BorderLayout.CENTER);
    	oddsBlackBoardPanel.add(rankingPanel, BorderLayout.EAST);
    }
    
    private JPanel createOddsPanel(){
    	//cre un oggetto panel
    	JPanel tempPanel = new JPanel();
    	//setto il layout
    	tempPanel.setLayout(new GridLayout(6,1));
    	//inizializzo l'array di label
    	oddsLabels = new ArrayList<ArrayList<JLabel>>();
    	//variabile per il cambio di colore
    	Color bgColor1 = Color.GRAY;
    	Color bgColor2 = Color.DARK_GRAY;
    	//mi servirà per scrivere la quotazione
    	String position = "1:";
    	//inizializzo le label che conterranno le posizioni delle 6 scuderie
    	//e le inserisco nel panel
    	for(int i=0; i<HorseFeverController.numberOfStables; i++)
    	{
    		Color bgColor;
    		if(i%2==0)
    			{
    			bgColor = bgColor1;
    			}
    		else{
    			bgColor= bgColor2;
    			}
    		//creo il pannello sul quale metto i loghi
    		JPanel rowOBBPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    		rowOBBPanel.setBackground(bgColor);
    		rowOBBPanel.setOpaque(true);
    		//aggiungo una prima label che indica la quotazione della stable
    		Integer numberOfOdds = 2+i;
    		JLabel oddsNumberLabel = new JLabel(position+numberOfOdds.toString());
    		oddsNumberLabel.setForeground(Color.WHITE);
    		rowOBBPanel.add(oddsNumberLabel);
    		//creo l'array di jlabel 
    		ArrayList<JLabel> labelForThisRow = new ArrayList<JLabel>();
    		oddsLabels.add(labelForThisRow);
    		for(int j=0; j<HorseFeverController.numberOfStables; j++)
    			{
    				oddsLabels.get(i).add(new JLabel());
    				rowOBBPanel.add(oddsLabels.get(i).get(j));
    			}
    		
    		tempPanel.add(rowOBBPanel);
    	}
    	return tempPanel;
    }
    
    private JPanel createRankingPanel(){
    	//cre un oggetto panel
    	JPanel tempPanel = new JPanel();
    	//setto il layout
    	tempPanel.setLayout(new GridLayout(6,1));
    	//inizializzo l'array di label
    	rankingLabels = new ArrayList<JLabel>();
    	//variabile per il cambio di colore
    	Color bgColor1 = Color.GRAY;
    	Color bgColor2 = Color.DARK_GRAY;
    	for(int i=0; i<HorseFeverController.numberOfStables; i++)
    	{
    		Color bgColor;
    		if(i%2==0)
    			{
    			bgColor = bgColor1;
    			}
    		else{
    			bgColor= bgColor2;
    			}
    		JLabel label = new JLabel();
    		label.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
    		label.setBackground(bgColor);
    		label.setOpaque(true);
    		rankingLabels.add(label);
    		tempPanel.add(rankingLabels.get(i));
    	}
    	return tempPanel;
    }
    
    private void createTabPanel(){
    	//creo l'immagine di sfondo
    	ImageIcon tabImage = new ImageIcon(lanesBackgroundImage);
    	//inizializzo con l'immagine di sfondo
    	tabPanel = new ImagePanel(tabImage.getImage());
		//setto il layout del tabellone
		tabPanel.setLayout(new GridLayout(numberOfRows,HorseFeverController.numberOfStables));
		tabPanel.setPreferredSize(new Dimension(647, 536));
		//inizializzo l'array tabLabels
		tabLabels = new ArrayList<ArrayList<JLabel>>();
		//inizializzo tutte le label che saranno visualizzate nelle celle del tabellone
		for(int i=0; i<HorseFeverController.numberOfStables; i++)
		{
			//creo una lane
			ArrayList<JLabel> lane = new ArrayList<JLabel>();
			for(int j=0; j<numberOfRows; j++)
			{
				//creo la label
				JLabel labelInThisBox =new JLabel("",JLabel.CENTER);
				//popolo la lane di labels
				lane.add(labelInThisBox);
			}
			//aggiungo l'array a tabLabels
			tabLabels.add(lane);
		}
		//aggiungo le lable al pannello
		for(int i=0; i<numberOfRows; i++)
			{
				for(int j=0; j<HorseFeverController.numberOfStables; j++){
					tabPanel.add(tabLabels.get(j).get(i));

				}
			}
    }
    
    private void createOtherPlayersPanel(){
    	//creo il panel
    	otherPlayersPanel=new JPanel();
    	//setto il layout
    	otherPlayersPanel.setLayout(new GridLayout(10, 1));
    	//inizializzo l'array di label
    	otherPlayersLabels = new ArrayList<JLabel>();
    	//inizializzo e aggiungo le label al panel
    	for(int i=0; i<10; i++)
    	{
    		JLabel label = new JLabel();
    		otherPlayersLabels.add(label);
    		otherPlayersPanel.add(otherPlayersLabels.get(i));
    	} 
    }
    //Fine metodi creatori di panel______________________________________________________________________________
   
    
    //Metodi per l'update dei contenuti del frame________________________________________________________________
    public void updateCurrentData(){
    	//aggiorno le action cards del giocatore corrente
    	updateCurrentPlayerActionCards();
    	//aggiorno le carte dell'utente corrente
    	updateCurrentPlayerCards();
    	//aggiorno le scommesse fatte dall'utente corrente
    	updateBets();	
    	//aggiorno la label che contiene i dati del giocatore corrente
    	updateCurrentPlayerInfo();
    	//aggiorno la classifica delle quotazioni
    	updateOddsBlackBoard();
    	//aggiorno la classifica di arrivo
    	updateRanking();
    	//aggiorno il tabellone
    	updateTab();
    	//aggiorno il panel otherplayers
    	updateOtherPlayers();
    	//aggiorno i pannelli delle scuderie
    	updateLanesActionCards();
    }
    
    private void updateCurrentPlayerActionCards(){
		//chiedo al controllore le carte azione del currentplayer
		ArrayList<ActionCard> actionCards = modelController.getCurrentPlayerActionCards();
		for(JLabel label: actionCardLabels)
			{
				label.setIcon(null);
			}
		for(int i=0; i<actionCards.size(); i++){
			ImageIcon actionCard = actionCards.get(i).getCardFrontImage();
			//setto le nuove carte nelle label
			actionCardLabels.get(i).setIcon(actionCard);
		}
    }
    
    private void updateCurrentPlayerCards(){
		//chiedo al controller la carta personaggio del currentplayer
		characterCard = modelController.getCurrentPlayerCharacterCard().getCardFrontImage();
		characterCardLabel.setIcon(characterCard);
		//chiedo al controller la carta scuderia del currentplayer
		stableCard=modelController.getCurrentPlayerStableCard().getCardFrontImage();
		stableCardLabel.setIcon(stableCard);
    }
    
    private void updateBets(){
		//ottengo il numero di giocatori
		Integer numberOfPlayers = modelController.getNumberOfPlayers();
		//pulisco le labels
		for(JLabel label: betsLabel){
			label.setText("");
			label.setForeground(Color.DARK_GRAY);
		}
		//per ognuno prendo l'array di scommesse effettuate
		
		for(int k=0,j=0; k<numberOfPlayers; k++,j+=3)
		{
			ArrayList<Bet> betOfThisPlayer = modelController.getBetsOfThePlayer(k);
			String nameOfThisPlayer = modelController.getNameOfPlayer(k)+":";
			betsLabel.get(j).setText(nameOfThisPlayer);
			for(int i=0; i<betOfThisPlayer.size(); i++)
			{
				String betInformation = betOfThisPlayer.get(i).getBetAmount().toString();
				betInformation+= " su "+betOfThisPlayer.get(i).getBetStable().getCardName();
				String betType;

				if(betOfThisPlayer.get(i).getIsBetToWin())
				{
						betType=" Vincente! ";
						}
				else{
						betType=" Piazzato ";
					}
				betInformation= betInformation.concat(betType);
				//se è stata vinta aggiungo una clausola vinta
				if(betOfThisPlayer.get(i).isBetWon())
				{
					betInformation= betInformation.concat(" VINTA!");
				}
				//setto il colore del testo uguale al colore della scuderia sulla quale 
				//si e' scommesso
				Color colorOfTheStable = betOfThisPlayer.get(i).getBetStable().getStableColor();
				betsLabel.get(i+j+1).setForeground(colorOfTheStable);
				betsLabel.get(i+j+1).setText(betInformation);
			}
		}
    }
    
    private void updateCurrentPlayerInfo(){
    	//raccolgo le informazioni dal modello
    	String currentPlayerInfo = "Giocatore corrente: "+modelController.getCurrentPlayerName();
    	currentPlayerInfo += " | Danari: "+modelController.getCurrentPlayerDanariAmount();
    	currentPlayerInfo += " | PV: "+modelController.getCurrentPlayerPV();
    	//aggiorno la label
    	currentPlayerInfoLabel.setText(currentPlayerInfo);
    }
    
    private void updateOddsBlackBoard(){
    	//cancello tutto dalla BB
    	for(ArrayList<JLabel> labelArray: oddsLabels)
    		{
    			for(JLabel label: labelArray){
    				label.setIcon(null);
    			}
    		}
    			
    		
    	//prendo dal modello un array di scuderie per ogni riga della oddsBB
    	for(int i=0; i<HorseFeverController.numberOfStables; i++)
    	{
    		ArrayList<StableCard> stablesOnThisOdds;
    		//chiedo l'array per questa quotazione
    		stablesOnThisOdds = modelController.getStablesCardAt(i);
    		//seleziono la label che conterrà gli stables su questa linea
    		ArrayList<JLabel> stablesOnThisOddsLabel = oddsLabels.get(i);
    		//compongo la stringa delle scuderie su questa linea
    		for(int j=0; j<stablesOnThisOdds.size(); j++)
    			{
    				stablesOnThisOddsLabel.get(j).setIcon(stablesOnThisOdds.get(j).getCardLogoImage());
    			}
    	}
    }
    
    private void updateRanking(){
    	//chiedo al modello tramite il controller la classifica di gara
    	ArrayList<StableCard> ranking = modelController.getRanking();
    	//pulisco la ranking 
    	for(JLabel label: rankingLabels)
    	{
    		label.setIcon(null);
    	}
    	for(int i=0; i<ranking.size(); i++)
    		{	
    			rankingLabels.get(i).setIcon(ranking.get(i).getCardLogoImage());
    		}
    }
    
    private void updateTab(){
    	//chido al modello l'array contentente la posizione di ogni lane
    	ArrayList<Integer> allPawnPosition = modelController.getAllPawnPosition();
    	//cancello la condizione precendente
    	for(ArrayList<JLabel> labelArray: tabLabels)
    		{
    		for(JLabel label: labelArray)
    			{
    				label.setIcon(null);
    				label.setText("");
    			}
    		}
    	//adesso posiziono una pedina sulla casella corrispondente
    	for(int i=0; i<HorseFeverController.numberOfStables; i++)
    		{
    			int positionOfThisStable = allPawnPosition.get(i);
    			if(positionOfThisStable>13)
    			{
    				String numberOfStepsDone=new String("+");
    				numberOfStepsDone+=positionOfThisStable-13;
    				JLabel numberOfStepsDoneLabel = tabLabels.get(i).get(0);
    				numberOfStepsDoneLabel.setText(numberOfStepsDone);
    				numberOfStepsDoneLabel.setForeground(Color.WHITE);
    			}else{
    				ImageIcon pawnImage = new ImageIcon(CardGenerator.giveThePawnImagePath(i));
    				tabLabels.get(i).get(numberOfRows-1-positionOfThisStable).setIcon(pawnImage);
    				tabLabels.get(i).get(numberOfRows-1-positionOfThisStable).repaint();
    			}
    		}
    }
    
    private void updateOtherPlayers(){
    	//pulisco le labels
    	for(JLabel label: otherPlayersLabels){
    		label.setText("");
    		
    	}
    	//chiedo al controllore quanti giocatori ci sono
    	Integer numberOfPlayers = modelController.getNumberOfPlayers();
    	for(int i=0,j=0; i<numberOfPlayers; i++,j+=2){
    		//prendo il nome del giocatore corrente per non mostrargli la sua condizione
    		String currentPlayerName = modelController.getCurrentPlayerName();
    		//prendo i dati del giocatore
    		String playerName = modelController.getNameOfPlayer(i);
    		String playerStableName = modelController.getStableCardOfPlayer(i);
    		if(currentPlayerName.equals(playerName))
    		{
    			j-=2;
    			continue;
    		}
    		String playerDanariAmount = modelController.getDanariAmountOfPlayer(i);
    		String playerVictoryPoint = modelController.getVictoryPointOfPlayer(i);
    		otherPlayersLabels.get(j).setText(playerName);
    		otherPlayersLabels.get(1+j).setText("Danari: "+playerDanariAmount+" | PV: "+playerVictoryPoint+" | Scuderia: "+playerStableName);
    		
    	}
    }
    
    private void updateLanesActionCards(){
    	//pulisco le lane
    	for(ArrayList<JLabel> laneLabelArray: lanesLabels){
    		for(JLabel label: laneLabelArray){
    			label.setIcon(null);
    		}
    	}
    	for(int i=0; i<HorseFeverController.numberOfStables; i++){
    		//ottengo le carte posate su questa lane
    		ArrayList<ActionCard> actionCardsOnThisLane = modelController.getActionCardsOnLane(i);
    		ArrayList<JLabel> labelOfThisLane = lanesLabels.get(i);
    		//elimino le carte precedenti
    		for(JLabel label: labelOfThisLane)
    			{
    				label.setIcon(null);
    			}
    		//metto le immagini contenute nelle action cards su questa lane
    		for(int j=0; j<actionCardsOnThisLane.size(); j++)
    			{
    				if(areActionCardsVisible)
    				{
    					labelOfThisLane.get(j).setIcon(actionCardsOnThisLane.get(j).getCardFrontImage());
    				}else{
    					labelOfThisLane.get(j).setIcon(actionCardsOnThisLane.get(j).getCardBackImage());
    				}
    			}
    	}
    }
    
    public void controlButtonEnabled(Boolean isEnabled){
    	controlButton.setEnabled(isEnabled);
    }
    
    public void setDicesColor(ArrayList<Color> sprintDicesColor){
    	if(sprintDicesColor==null){
        	for(int i=0; i<sprintDices.size(); i++){
        		sprintDices.get(i).setOpaque(false);
        	}
        	return;
    	}
    	for(int i=0; i<sprintDicesColor.size(); i++){
    		sprintDices.get(i).setBackground(sprintDicesColor.get(i));
    		sprintDices.get(i).setOpaque(true);
    	}
   }
    
	public void setControlButtonName(String buttonName){
    	controlButton.setText(buttonName);
    	}
    
    public void setShowActionCards(Boolean areActionCardsVisible){
    	this.areActionCardsVisible=areActionCardsVisible;
    }
    
    public void setMovementCard(MovementCard movementCard){
    	if(movementCard==null){
    		this.movementCardLabel.setIcon(new ImageIcon(movementCardBackImage));
    		return;
    	}
    	this.movementCardLabel.setIcon(movementCard.getCardFrontImage());
    }
    
    public void disposeFrame(){
    	frame.dispose();
    }
}
