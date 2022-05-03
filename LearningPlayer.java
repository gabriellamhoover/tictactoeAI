package project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import dsUtils.TTTGUI;
import dsUtils.TTTPosition;
import edu.princeton.cs.algs4.StdOut;

public class LearningPlayer implements Player{
	ArrayList<TTTPosition> playerMoves;
	ArrayList<BoardConfigs> keysUsed;
	int player;
	boolean isHuman = false;
	public LearningPlayer(int p) {
		this.player = p;
		this.playerMoves = new ArrayList<TTTPosition>();
		this.keysUsed = new ArrayList<BoardConfigs>();
	}
	@Override
	public ArrayList<TTTPosition> getMoves() {
		// TODO Auto-generated method stub
		return playerMoves;
	}

	@Override
	public void clearMoves() {
		this.playerMoves = new ArrayList<TTTPosition>();
		this.keysUsed = new ArrayList<BoardConfigs>();
	}
	public void addKey(int [] board) {
		BoardConfigs key = new BoardConfigs(board);
		this.keysUsed.add(key);
	}
	@Override
	public void move(Board b, TTTGUI g) {
		TTTPosition move = b.findBestMove(this);
		g.makeMove(move, this.player);
		this.playerMoves.add(move);
		b.removeMove(move);
		b.updateBoardStatus(move, this.player);
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
				if(other.isLearningPlayer()) {
					b.updateScores(false, other);
				}
				b.updateScores(true, this);
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
				b.updateScores(true, this);
				return true;
			}
		}
		if(b.getSize() == 0) {
			if(other.isHuman()) {
				StdOut.println("Player " + other.getNumber() + ", it is a draw.");
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean isHuman() {
		return false;
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return this.player;
	}

	@Override
	public boolean isLearningPlayer() {
		// TODO Auto-generated method stub
		return true;
	}
	public ArrayList<BoardConfigs> getKeys(){
		return this.keysUsed;
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
