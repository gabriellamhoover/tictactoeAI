package project;
import java.util.ArrayList;
import dsUtils.TTTPosition;


public class BoardConfigs{


		int h = 0;
		int [] boardStatus;
		public BoardConfigs(int [] board) {
			this.boardStatus = board;
		}
		@Override
		public int hashCode() {
			if(this.h != 0) {
				return h;
			}

			int result = 0;
			for(int i = 0; i < 9; i++) {
				int ans = ((int) Math.pow(3, i)) * this.boardStatus[i];
				result += ans;
			}
			h = result;
			return result;
		}
		public int getLength() {
			return this.boardStatus.length;
		}
		public int [] getBoardConfigs(){
			return this.boardStatus;
		}
		@Override
		public boolean equals(Object obj) {
			if(this == obj) {
				return true;
			}
			if(obj == null) {
				return false;
			}
			if(getClass() != obj.getClass()) {
				return false;
			}
			BoardConfigs other = (BoardConfigs) obj;
			if(other.getLength() != this.getLength()) {
				return false;
			}
			for(int i = 0; i < other.getLength(); i++) {
				if(other.getBoardConfigs()[i] != this.boardStatus[i]) {
					return false;
				}
			}
			return true;
		}
		public String toString() {
			String s = "";
			for(int i : this.boardStatus) {
				s += i;
				s += " ";
			}
			return s;
		}
		public void sort(ArrayList<TTTPosition> positions, int start, int end) {
			for(int i = start; i <= end; i++) {
				TTTPosition min = positions.get(i);
				int index = i;
				for(int j = i; j <= end; j++) {
					if(positions.get(j).getRow() <= min.getRow()) {
						if(positions.get(j).getCol() < min.getCol()){
							min = positions.get(j);
							index = j;
						}
					}
				}
				TTTPosition temp = positions.get(i);
				positions.set(i, min);
				positions.set(index, temp);

			}
		}
		public boolean gameOver() {
			//check for 3 in a row
			for(int i = 0; i < this.getLength(); i += 3 ) {
				int rowCount = 0;
				int temp = this.boardStatus[i];
				for(int j = i; j < i + 3; j++) {
					if(this.boardStatus[j] == temp && temp != 0) {
						rowCount++;
					}
				}
				if(rowCount == 3) {
					return true;
				}

			}
			//check for columns
			for(int i = 0; i < 3; i++) {
				int colCount = 0;
				int temp = this.boardStatus[i];
				for(int j = i; j <= i + 6; j += 3) {
					if(this.boardStatus[j] == temp && temp!= 0) {
						colCount ++;
					}
				}
				if(colCount == 3) {
					return true;
				}
			}
			//check for diagonals
			if(this.boardStatus[4] != 0) {
				if(this.boardStatus[0] == this.boardStatus[4] && this.boardStatus[0] == this.boardStatus[8]) {
					return true;

				}
				if(this.boardStatus[2] == this.boardStatus[4] && this.boardStatus[2] == this.boardStatus[6]) {
					return true;
				}
			}
			//check if draw
			int draw = 0;
			for(int i : this.boardStatus) {
				if(i != 0) {
					draw++;
				}
			}
			if(draw == 9) {
				return true;
			}
			return false;
		}
		public int currentTurn() {
			int p1 = 0;
			int p2 = 0;
			for(int i : this.boardStatus) {
				if(i == 1) {
					p1++;
				}
				if(i == 2) {
					p2++;
				}
			}
			if(p1 > p2) {
				return 2;
			}
			return 1;
		}
		public int score(int playerTurn) {
			for(int i = 0; i < this.getLength(); i += 3 ) {
				int rowCount = 0;
				int temp = this.boardStatus[i];
				for(int j = i; j < i + 3; j++) {
					if(this.boardStatus[j] == temp && temp != 0) {
						rowCount++;
					}
				}
				if(rowCount == 3) {
					if(temp == playerTurn) {
						return 1;
					}
					else {
						return -1;
					}
				}

			}
			for(int i = 0; i < 3; i++) {
				int colCount = 0;
				int temp = this.boardStatus[i];
				for(int j = i; j <= i + 6; j += 3) {
					if(this.boardStatus[j] == temp && temp != 0) {
						colCount ++;
					}
				}
				if(colCount == 3) {
					if(temp == playerTurn) {
						return 1;
					}
					else {
						return -1;
					}
				}
			}
			if(this.boardStatus[4] != 0) {
				if(this.boardStatus[0] == this.boardStatus[4] && this.boardStatus[0] == this.boardStatus[8]) {
					if(this.boardStatus[0] == playerTurn) {
						return 1;
					}
					else {return -1;

					}
				}
				if(this.boardStatus[2] == this.boardStatus[4] && this.boardStatus[2] == this.boardStatus[6]) {
					if(this.boardStatus[2] == playerTurn) {
						return 1;
					}
					else {return -1;

					}
				}
			}
			return 0;
		}
	}
