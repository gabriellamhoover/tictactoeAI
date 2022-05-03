package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;


import dsUtils.TTTGUI;
import dsUtils.TTTPosition;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private ArrayList<TTTPosition> availableMoves;
	private int [] currentBoard;
	private HashMap <BoardConfigs, Integer> scores;
	private HashMap <BoardConfigs, Integer> perfectPlayerScores;
	private Map<BoardConfigs, LinkedList<BoardConfigs>> boardGraph;
	private HashSet<BoardConfigs> verticesVisited;
	private HashSet<BoardConfigs> scoresVisited;


	public Board(){
		this.availableMoves = new ArrayList<TTTPosition>();
		this.scores = new HashMap<BoardConfigs, Integer>();
		this.perfectPlayerScores = new HashMap<BoardConfigs, Integer>();
		this.verticesVisited = new HashSet<BoardConfigs>();
		this.scoresVisited = new HashSet<BoardConfigs>();
		this.boardGraph = new HashMap <BoardConfigs, LinkedList<BoardConfigs>>();
		this.currentBoard = new int[9];
		for(int i = 0; i < this.currentBoard.length; i++) {
			this.currentBoard[i] = 0;
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				TTTPosition p = new TTTPosition(i, j);
				availableMoves.add(p);
			}
		}
	}
	public int [] getCurrBoard(){
		return currentBoard;
	}

	public boolean checkMoveLegality(TTTPosition pos) {
		for(TTTPosition p : this.availableMoves) {
			if(p.getRow() == pos.getRow() && p.getCol() == pos.getCol()) {
				return true;
			}
		}
		return false;
	}
	public void clearBoard(TTTGUI g){
		g.clearBoard();
		this.currentBoard = new int[9];
		for(int i = 0; i < this.currentBoard.length; i++) {
			this.currentBoard[i] = 0;
		}
		this.availableMoves = new ArrayList<TTTPosition>();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				TTTPosition p = new TTTPosition(i, j);
				availableMoves.add(p);
			}
		}
	}
	public ArrayList<TTTPosition> getAvailableMoves(){
		return availableMoves;
	}
	public void removeMove(TTTPosition pos) {
		for(int i = 0; i < this.availableMoves.size(); i++) {
			TTTPosition temp = this.availableMoves.get(i);
			if(temp.getRow() == pos.getRow() && temp.getCol() == pos.getCol()) {
				availableMoves.remove(i);
			}
		}
	}
	public void updateBoardStatus(TTTPosition pos, int playerNum){
		int index = this.PositiontoNum(pos);
		if(playerNum == 1) {
			this.currentBoard[index] = 1;
		}
		else {
			this.currentBoard[index] = 2;
		}
	}
	public void removeMove(int index) {
		availableMoves.remove(index);
	}
	public int getSize() {
		return this.availableMoves.size();
	}
	public TTTPosition getPos(int index){
		return this.availableMoves.get(index);
	}
	public boolean contains(TTTPosition pos) {
		for(TTTPosition p : this.availableMoves) {
			if(p.getRow() == pos.getRow() && p.getCol() == pos.getCol()) {
				return true;
			}
		}

		return false;
	}
	public HashMap<BoardConfigs, Integer> getScores(){
		return this.scores;
	}
	public boolean containsScore(BoardConfigs key) {
		return this.scores.containsKey(key);
	}
	public TTTPosition findBestMove(LearningPlayer l) {
		TTTPosition move = null;
		int count = 0;
		int max = 0;
		int playerNum = l.getNumber();
		int [] boardStatus = null;
		for(TTTPosition p : this.availableMoves) {
			int [] temp = new int[]{0,0,0,0,0,0,0,0,0};
			for(int i = 0; i < 9; i++) {
				temp[i] = this.currentBoard[i];
			}
			int newMoveIndex = this.PositiontoNum(p);
			temp[newMoveIndex] = playerNum;
			BoardConfigs possibleBoard = new BoardConfigs(temp);
			if(this.scores.containsKey(possibleBoard) == false) {
				this.scores.put(possibleBoard, 0);
			}
			if(count == 0) {
				max = scores.get(possibleBoard);
				move = p;
				boardStatus = temp;
				count++;
			}
			else {
				if(scores.get(possibleBoard) > max) {
					max = scores.get(possibleBoard);
					move = p;
					boardStatus = temp;
				}
			}
		}
		l.addKey(boardStatus);
		return move;
	}
	public void updateScores(boolean win, Player p) {
		if(p.isLearningPlayer() == false) {
			return;
		}
		if(win == true) {
			for(BoardConfigs b : p.getKeys()) {
				int score = this.scores.get(b);
				score++;
				this.scores.put(b, score);
			}
		}
		else {
			for(BoardConfigs b : p.getKeys()) {
				int score = this.scores.get(b);
				score--;
				this.scores.put(b, score);
			}
		}
	}

	public void sortBoardPositions(ArrayList<TTTPosition> positions, int start, int end) {
		for(int i = start; i <= end; i++) {
			TTTPosition min = positions.get(i);
			int index = i;
			for(int j = i; j <= end; j++) {
				if(positions.get(i).getRow() <= min.getRow()) {
					if(positions.get(i).getCol() < min.getCol()){
						min = positions.get(i);
						index = j;
					}
				}
			}
			TTTPosition temp = positions.get(i);
			positions.set(i, min);
			positions.set(index, temp);

		}
	}

	public boolean Player1Turn(ArrayList<TTTPosition> moves) {
		if(moves.size() % 2 == 0) {
			return true;
		}
		return false;
	}

	public int PositiontoNum(TTTPosition pos) {
		int positionNum = 0;
		if(pos.getRow() == 0) {
			positionNum = pos.getCol();
		}
		if(pos.getRow() == 1) {
			positionNum = pos.getCol() + pos.getRow() + 2;
		}
		if(pos.getRow() == 2) {
			positionNum = pos.getCol() + pos.getRow() + 4;
		}
		return positionNum;
	}
	public Player getPlayerType(char pType, int playerNum) {
		char type = Character.toLowerCase(pType);
		if(type == 'l') {
			StdOut.println("Player " + playerNum + " is a learning player.");
			return new LearningPlayer(playerNum);
		}
		if(type == 'r') {
			StdOut.println("Player " + playerNum + " is a random player.");
			return new RandomPlayer(playerNum);
		}
		if(type == 'p') {
			StdOut.println("Player " + playerNum + " is a perfect player.");
			return new PerfectPlayer(playerNum);
		}
		StdOut.println("Player " + playerNum + " is a human player.");
		return new HumanPlayer(playerNum);
	}
	public void populateBoardGraphH() {
		this.populateBoardGraph(1, this.currentBoard);
	}
	public void populateBoardGraph(int pNum, int [] tempBoard) {
		LinkedList<BoardConfigs> tempList = new LinkedList<BoardConfigs>();
		BoardConfigs vertexKey = new BoardConfigs(tempBoard);
		for(int i = 0; i < tempBoard.length; i++) {
			if(tempBoard[i] == 0) {
				int [] temp = new int [9];
				for(int j = 0; j < 9; j++) {
					temp[j] = tempBoard[j];
				}
				temp[i] = pNum;
				BoardConfigs vertex = new BoardConfigs(temp);
				if(this.verticesVisited.contains(vertex) == false) {
					if(vertex.gameOver()) {
						//LinkedList<BoardConfigs> gameOverList = new LinkedList<BoardConfigs>();
						//gameOverList.addFirst(vertex);
						this.boardGraph.put(vertex, null);
						this.verticesVisited.add(vertex);
					}
					else {
						if(pNum == 1) {
							this.populateBoardGraph(2, temp);
						}
						else {
							this.populateBoardGraph(1, temp);
						}
					}
				}
				tempList.add(vertex);
			}
		}
		this.boardGraph.put(vertexKey, tempList);
		this.verticesVisited.add(vertexKey);

	}
	public void testGraph(BoardConfigs b) {
		StdOut.println(this.boardGraph.containsKey(b));
		if(this.boardGraph.containsKey(b)) {
			StdOut.println("The parent is this: " + b.toString());
			StdOut.println("The children are: ");
			//int size = this.boardGraph.get(b).size();
			StdOut.println(this.boardGraph.get(b));
		}
	}
	public void setScoresH(int playernum) {
		BoardConfigs b = new BoardConfigs(this.currentBoard);
		setScores(b, playernum);
	}
	public int setScores(BoardConfigs vertex, int playernum) {

		if(vertex.gameOver() == true) {
			int score = vertex.score(playernum);
			if(this.scoresVisited.contains(vertex) == false) {
				this.perfectPlayerScores.put(vertex, score);
				this.scoresVisited.add(vertex);
			}
			return score;
		}
		int turn = vertex.currentTurn();
		BoardConfigs initial = this.boardGraph.get(vertex).getFirst();
		int ans;
		if(this.scoresVisited.contains(initial)) {
			ans = this.perfectPlayerScores.get(initial);
		}
		else {
			ans = setScores(initial, playernum);
		}
		for(BoardConfigs v : this.boardGraph.get(vertex)) {
			int temp;
			if(this.scoresVisited.contains(v)){
				temp = this.perfectPlayerScores.get(v);
			}
			else {
				temp = setScores(v, playernum);
			}
			if(turn == playernum) {
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
		if(this.scoresVisited.contains(vertex) == false) {
			this.scoresVisited.add(vertex);
			this.perfectPlayerScores.put(vertex, ans);
		}
		return ans;



	}
	public void testScores(BoardConfigs v) {
		if(this.perfectPlayerScores.containsKey(v)) {
			StdOut.println(this.perfectPlayerScores.containsKey(v));
			StdOut.println("The Score is " + this.perfectPlayerScores.get(v));
		}
		else {
			StdOut.println(this.perfectPlayerScores.containsKey(v));
		}
	}
	public Map<BoardConfigs, LinkedList<BoardConfigs>> getGraph(){
		return this.boardGraph;
	}
}



