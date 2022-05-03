package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import dsUtils.TTTGUI;
import dsUtils.TTTPosition;
import edu.princeton.cs.algs4.StdOut;

public class PerfectPlayer implements Player {
	int player;
	HashMap <BoardConfigs, Integer> perfectPlayerScores;
	HashSet<BoardConfigs> scoresVisited;
	Map<BoardConfigs, LinkedList<BoardConfigs>> boardGraph;
	ArrayList <TTTPosition> playerMoves;

	public PerfectPlayer(int playerNum) {
		this.player = playerNum;
		this.scoresVisited = new HashSet <BoardConfigs>();
		this.perfectPlayerScores = new HashMap<BoardConfigs, Integer>();
		this.playerMoves = new ArrayList<TTTPosition>();

	}

	@Override
	public ArrayList<TTTPosition> getMoves() {
		// TODO Auto-generated method stub
		return this.playerMoves;
	}

	@Override
	public void clearMoves() {
		this.playerMoves = new ArrayList<TTTPosition>();

	}

	@Override
	public ArrayList<BoardConfigs> getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move(Board b, TTTGUI g) {
		BoardConfigs vertex = new BoardConfigs(b.getCurrBoard());
		BoardConfigs initial = b.getGraph().get(vertex).getFirst();
		BoardConfigs nextBoard = initial;
		int score = this.perfectPlayerScores.get(initial);
		for(BoardConfigs v : b.getGraph().get(vertex)) {
			int temp = this.perfectPlayerScores.get(v);
			if(temp > score) {
				score = temp;
				nextBoard = v;
			}
		}
		TTTPosition move = this.getPosition(vertex, nextBoard);
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

	@Override
	public boolean isLearningPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return this.player;
	}
	public void setScoresH(Map<BoardConfigs, LinkedList<BoardConfigs>> boardGraph) {
		BoardConfigs b = new BoardConfigs(new int [] {0,0,0, 0,0,0, 0,0,0});
		setScores(b,boardGraph);
	}
	public int setScores(BoardConfigs vertex,Map<BoardConfigs, LinkedList<BoardConfigs>> boardGraph ) {
		if(this.scoresVisited.contains(vertex)) {
			return this.perfectPlayerScores.get(vertex);
		}
		if(vertex.gameOver() == true) {
			int score = vertex.score(this.player);
			if(this.scoresVisited.contains(vertex) == false) {
				this.perfectPlayerScores.put(vertex, score);
				this.scoresVisited.add(vertex);
			}
			return score;
		}
		BoardConfigs initial = boardGraph.get(vertex).getFirst();
		int ans;
		ans = setScores(initial, boardGraph);
		int turn = vertex.currentTurn();
		for(BoardConfigs v : boardGraph.get(vertex)) {
			int temp;
			temp = setScores(v, boardGraph);
			if(turn == this.player) {
				if(temp > ans) {
					ans = temp;
				}
			}
			else {
				if(temp < ans) {
					ans = temp;
				}
			}
		}
		this.scoresVisited.add(vertex);
		this.perfectPlayerScores.put(vertex, ans);
		return ans;



	}
	public void testScores(BoardConfigs vertex,Map<BoardConfigs, LinkedList<BoardConfigs>> boardGraph) {
		this.setScores(vertex, boardGraph);
		StdOut.println(this.scoresVisited.contains(vertex));
		StdOut.println("Original Board is: " + vertex.toString() + " and the score is: " + this.perfectPlayerScores.get(vertex));
		if(this.scoresVisited.contains(vertex)) {
			for(BoardConfigs v : boardGraph.get(vertex)) {
				StdOut.println("The Board is: " + v.toString());
				StdOut.println("The score is: " + this.perfectPlayerScores.get(v));
				StdOut.println("Game is over: " + v.gameOver());
				if(this.scoresVisited.contains(v) && v.gameOver() == false) {
					this.testScores(v, boardGraph);
				}
			}
		}
	}


	@Override
	public boolean isPerfectPlayer() {
		// TODO Auto-generated method stub
		return true;
	}
	public TTTPosition getPosition(BoardConfigs curr, BoardConfigs next) {
		TTTPosition pos = null;
		int changedPos = 0;
		for(int i = 0; i < curr.getLength(); i++) {
			if(curr.getBoardConfigs()[i] != next.getBoardConfigs()[i]) {
				changedPos = i;
			}
			if(changedPos < 3) {
				pos = new TTTPosition(0, changedPos);
			}
			if(changedPos > 2 && changedPos < 6) {
				pos = new TTTPosition(1, changedPos - 3);
			}
			if(changedPos > 5) {
				pos = new TTTPosition(2, changedPos - 6);
			}
		}
		return pos;

	}
}
