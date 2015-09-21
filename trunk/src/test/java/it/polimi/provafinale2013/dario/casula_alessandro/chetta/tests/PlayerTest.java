/******************************************************************
 * @date: Milan, 18/06/2013
 * @title: Horse Fever
 * @school: Politecnico di Milano
 * @course: Prova Finale 2013
 * @author: Alessandro Chetta 758461
 * @author: Dario Casula 757731
 ******************************************************************/

package it.polimi.provafinale2013.dario.casula_alessandro.chetta.tests;

import static org.junit.Assert.*;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.ActionType;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.Effect;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.cards.StableCard;
import it.polimi.provafinale2013.dario.casula_alessandro.chetta.logic.*;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.junit.Test;

public class PlayerTest {
	ArrayList<Bet> playerBetsTest = new ArrayList<Bet>();
	ArrayList<ActionCard> actionCardsTest = new ArrayList<ActionCard>();
	Player playerTest = new Player("Name for Test", null, 500, null, 5, actionCardsTest, playerBetsTest);
	ImageIcon image = new ImageIcon("");

	@Test
	public void addPlayerBetTest(){
		
		StableCard secondStableCard = new StableCard("The Blue Blood", 56, image, Color.BLUE, null);
		Bet betTest = new Bet(500, true, secondStableCard, false);
		playerTest.addPlayerBet(betTest);
		Bet playerAddedBet = playerTest.getPlayerBets().get(0);
		boolean condition = ((playerAddedBet.getBetAmount()==500) && (playerAddedBet.getIsBetToWin()==true) && (playerAddedBet.getBetStable().getCardName()=="The Blue Blood"));
		assertTrue(condition);
	}
	
	@Test
	public void removeThisActionCardTest(){
		ActionCard actionCardTest = new ActionCard("Alfio Allibratore", 15, image, image, null, Effect.SPECIAL, ActionType.ODDSUP, 0, false);
		this.actionCardsTest.add(actionCardTest);
		this.playerTest.setActionCards(actionCardsTest);
		this.playerTest.removeThisActionCard(actionCardTest);
		assertTrue(this.playerTest.getPlayerActionCards().isEmpty());
	}
	
	@Test
	public void increasePlayerDanariAmountTest(){
		this.playerTest.increasePlayerDanariAmount(500);
		//per evitare che assertEquals sia ambiguo
		Integer oneThousand = new Integer(1000);
		assertEquals(oneThousand,this.playerTest.getPlayerDanariAmount());
	}
	
	@Test
	public void increasePlayerVictoryPointsTest(){
		this.playerTest.increasePlayerVictoryPoints(5);
		//per evitare che assertEquals sia ambiguo
		Integer ten = new Integer(10);
		assertEquals(ten,this.playerTest.getPlayerVictoryPoints());
	}
	
	@Test
	public void decreasePlayerDanariAmountTest(){
		this.playerTest.decreasePlayerDanariAmount(300);
		//per evitare che assertEquals sia ambiguo
		Integer twoHundred = new Integer(200);
		assertEquals(twoHundred,this.playerTest.getPlayerDanariAmount());
	}
	
	@Test
	public void decreasePlayerVictoryPointsTest(){
		this.playerTest.decreasePlayerVictoryPoints(3);
		//per evitare che assertEquals sia ambiguo
		Integer two = new Integer(2);
		assertEquals(two,this.playerTest.getPlayerVictoryPoints());
	}
	
	@Test
	public void getMinimumBetTest() {
		Integer minimumBet=this.playerTest.getMinimumBet();
		//per evitare che assertEquals sia ambiguo
		Integer fiveHundred=new Integer(500);
		assertEquals(fiveHundred, minimumBet);
	}

}
