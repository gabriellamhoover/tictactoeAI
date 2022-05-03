package project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import dsUtils.TTTGUI;
import dsUtils.TTTPosition;
import edu.princeton.cs.algs4.StdOut;

public class RandomPlayer implements Player {
	ArrayList<TTTPosition> playerMoves;
	int player;
	boolean isHuman = false;
	public RandomPlayer(int p) {
		this.player = p;
		this.playerMoves = new ArrayList<TTTPosition>();
	}
	@Override
	public void move(Board b, TTTGUI g) {
		int index;
		if(b.getSize() > 1) {
			int range = b.getSize()- 1;
			Random rand = new Random();
			index = rand.nextInt(range);
		}
		else {
			index = 0;
		}
		TTTPosition randPos = b.getPos(index);
		g.makeMove(randPos, this.player);
		this.playerMoves.add(randPos);
		b.removeMove(randPos);
		b.updateBoardStatus(randPos, this.player);
		
	}
	@Override
	public ArrayList<TTTPosition> getMoves() {
		// TODO Auto-generated method stub
		return playerMoves;
	}
	@Override
	public void clearMoves() {
		// TODO Auto-generated method stub
		this.playerMoves = new ArrayList<TTTPosition>();
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
				if(other.isHuman()) {
					StdOut.println("Player " + other.getNumber() + ", you lose");
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
				if(other.isHuman()) {
					StdOut.println("Player " + other.getNumber() + ", you lose.");
				}
				if(other.isLearningPlayer()) {
					b.updateScores(false, other);
				}
				return true;
			}
		}
		if(b.getSize() == 0) {
			if(other.isHuman()) {
				StdOut.println("Player " + other.getNumber() + ", it is a draw.");
			}
			if(other.isLearningPlayer()) {
				b.updateScores(false, other);
			}
			return true;
		}

		return false;
	}
	@Override
	public boolean isHuman() {
		// TODO Auto-generated method stub
		return false;
	} 
	public int getNumber() {
		return player;
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




