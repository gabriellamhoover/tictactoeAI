package project;

import dsUtils.TTTGUI;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deliverable3 {
	public static void main(String[] args) {
		Player player1;
		Player player2;
		Board b = new Board();
		StdOut.print("Player 1 (H)uman, (R)andom, (L)earning, or (P)erfect: ");
		char c1 = StdIn.readLine().charAt(0);
		player1 = b.getPlayerType(c1, 1);
		StdOut.print("Player 2 (H)uman, (R)andom, (L)earning, or (P)erfect: ");
		char c2 = StdIn.readLine().charAt(0);
		player2 = b.getPlayerType(c2, 2);
		TTTGUI gui = new TTTGUI(150);
		boolean GameOver;
		if(player1.isPerfectPlayer() | player2.isPerfectPlayer()) {
			b.populateBoardGraphH();
		}
		if(player1.isPerfectPlayer()) {
			player1.setScoresH(b.getGraph());
		}
		if(player2.isPerfectPlayer()) {
			player2.setScoresH(b.getGraph());
		}
		do {
			b.clearBoard(gui);
			player1.clearMoves();
			player2.clearMoves();
			GameOver = false;
			while(GameOver == false) {
				player1.move(b,gui);
				GameOver = player1.CheckGameOver(b, player2);
				if(GameOver == false) {
					player2.move(b, gui);
					GameOver = player2.CheckGameOver(b, player1);
				}
			}
		} while (gui.askPlayAgain() | GameOver == false);
		System.exit(0);
	}}

