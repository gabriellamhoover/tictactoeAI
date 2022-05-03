package project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import dsUtils.TTTGUI;
import dsUtils.TTTPosition;

public interface Player {
	ArrayList<TTTPosition> getMoves();
	void clearMoves();
	ArrayList<BoardConfigs> getKeys();
	void move(Board b, TTTGUI g);
	boolean CheckGameOver(Board b, Player other);
	boolean isHuman();
	boolean isLearningPlayer();
	boolean isPerfectPlayer();
	int getNumber();
	void setScoresH(Map<BoardConfigs, LinkedList<BoardConfigs>> graph);
}
