/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic;

import it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui.GUI;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.gui.InitialWindow;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.guiRMI.GuiRMI;


public class Main {


	public static void main(String[] args) {
		new InitialWindow();	
	}
	
	public static void startLocalGame(){
		Controller modelController = new Controller();
		new GUI(modelController);
	}
	
	public static void startRemoteGame(){
		new GuiRMI();
	}

}
