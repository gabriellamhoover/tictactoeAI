package project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import dsUtils.TTTGUI;
import dsUtils.TTTPosition;
import edu.princeton.cs.algs4.StdOut;

public class HumanPlayer implements Player {
	ArrayList<TTTPosition> playerMoves;
	int player;
	public HumanPlayer(int p) {
		this.player = p;
		this.playerMoves = new ArrayList<TTTPosition>();
	}

	@Override
	public ArrayList<TTTPosition> getMoves() {
		// TODO Auto-generated method stub
		return playerMoves;
	}
	@Override
	public void clearMoves() {
		// TODO Auto-generated method stub
		this.playerMoves =  new ArrayList<TTTPosition>();
	}
	@Override
	public void move(Board b, TTTGUI g) {
		TTTPosition p = g.getMove();
		boolean legalMove = b.checkMoveLegality(p);
		if(!legalMove) {
			StdOut.println("Error! that spot has already been taken please select another");
			move(b, g);
		}
		else {
			g.makeMove(p, this.player);
			this.playerMoves.add(p);
			b.removeMove(p);
			b.updateBoardStatus(p, this.player);
		}
	}

	@Override
	public boolean CheckGameOver(Board b, Player other) {
		for(int i = 0; i < playerMoves.size(); i ++) {
			TTTPosition temp = playerMoves.get(i);
			int tempRow = temp.getRow();
			int tempCol = temp.getCol();
			int rowCount = 0;
			int colCount = 0;
			for(int j = i +1; j < playerMoves.size(); j++) {
				TTTPosition temp2 = playerMoves.get(j);
				if(temp2.getRow() == tempRow) {
					rowCount ++;
				}
				if(temp2.getCol() == tempCol) {
					colCount ++;
				}
			}
			if (rowCount == 2 | colCount == 2) {
				StdOut.println("Player " + this.player + ", you win!");
				if(other.isHuman()) {
					StdOut.println("Player " + other.getNumber() + ", you lose");
				}
				if(other.isLearningPlayer()) {
					b.updateScores(false, other);
				}
				return true;
			}

		}
		int diagonalLeft = 0;
		int diagonalRight = 0;
		for(TTTPosition p : playerMoves) {
			if(p.getRow() == 0 && p.getCol() == 2) {
				diagonalRight++;
			}
			if(p.getRow() == 2 && p.getCol() == 0) {
				diagonalRight++;
			}
			if(p.getRow() == 1 && p.getCol()== 1){
				diagonalLeft++;
				diagonalRight++;
			}
			else if(p.getRow() == p.getCol()) {
				diagonalLeft++;

			}

			if(diagonalLeft == 3 | diagonalRight == 3) {
				StdOut.println("Player " + this.player + ", you win!");
				if(other.isHuman()) {
					StdOut.println("Player " + other.getNumber() + ", you lose");
				}
				if(other.isLearningPlayer()) {
					b.updateScores(false, other);
				}
				return true;
			}
		}
		if(b.getSize() == 0) {
			StdOut.println("Player " + this.player + ", it is a draw.");
			if(other.isHuman()) {
				StdOut.println("Player " + other.getNumber() + ", it is a draw.");
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean isHuman() {
		// TODO Auto-generated method stub
		return true;
	}
	public int getNumber() {
		return this.player;
	}

	@Override
	public boolean isLearningPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<BoardConfigs> getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerfectPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setScoresH(Map<BoardConfigs, LinkedList<BoardConfigs>> graph) {
		// TODO Auto-generated method stub
		
	}
}
