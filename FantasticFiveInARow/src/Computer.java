import java.util.ArrayList;
import java.util.HashMap;

public class Computer extends Player {

	private int board[][];
	private HashMap<Integer, int[][]> boardStates;
	private HashMap<Integer, String> scores;
	private int moveX;
	private int moveY;

	Computer(String _name, int _turnVal, boolean _isComputer) {
		super(_name, _turnVal, _isComputer);
		scores = new HashMap<Integer, String>();
		boardStates = new HashMap<Integer, int[][]>();
	}
	
	public void move(int[][] _board) {
		board = new int[19][19];
		int maxKey = 0;;
		int minKey = 10000;
		//create a deep copy of the array because java is stupid
		for(int i = 0; i < _board.length; i++){
			for(int j = 0; j < _board[i].length; j++){
				if(_board[i][j] == 0){
					board[i][j] = 0;
				} else if(_board[i][j] == 1){
					board[i][j] = 1;
				} else{
					board[i][j] = 2;
				}
			}
		}
		
		int test = minimax(_board, 0, true, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
		for (int key : scores.keySet()) {
		    if(key>maxKey){
		    	maxKey = key;
		    }
		    if(key < minKey){
		    	minKey = key;
		    }
		}
		
		String location = "";
		
		if(maxKey < -1*minKey){
			location = scores.get(minKey);
		}else{
			location = scores.get(maxKey);
		}
		
		String[] locationsSplit = location.split(" ");
		moveX = Integer.parseInt(locationsSplit[0]);
		moveY = Integer.parseInt(locationsSplit[1]);
		
		boardStates.clear();
		scores.clear();
	}

	public int getMoveX(){
		return moveX;
	}
	
	public int getMoveY(){
		return moveY;
	}
	
	private int minimax(int[][] _board, int depth, boolean computerTurn, double alpha, double beta) {
		int stoneVal = this.turnVal;
		int opponentStone = 0;
		int MAX_DEPTH = 3;
		int currentStone = 0;
		int score = 0;
		CheckGameEnd check = new CheckGameEnd();
		// Poorly planned how to get opponents stone value
		if (stoneVal == 0) {
			opponentStone = 1;
		}

		if (depth <= MAX_DEPTH) {
			int[][] tmpBoard = _board;

			check.setTmpBoard(tmpBoard);
			for (int i = 0; i < _board.length; i++) {
				for (int j = 0; j < _board[i].length; j++) {
					tmpBoard = check.getBoardVals();
					if (tmpBoard[i][j] == 2) {
						if (computerTurn) {
							check.setBoardVal(i, j, stoneVal);
							currentStone = stoneVal;
							saveState(check.getBoardVals(), depth);
						} else {
							check.setBoardVal(i, j, opponentStone);
							currentStone = opponentStone;
							saveState(check.getBoardVals(), depth);
						}
						/*
						 * If there is a win on the board return There isn't a
						 * need to continue calculating
						 */
						if (check.Win(i, j, currentStone)) {
							if (currentStone == opponentStone) {
								return score - 10;
							} else {
								return score + 10;
							}
						}
						if (depth == MAX_DEPTH) {
							return getScore(check, currentStone, i, j);
						}
						// decrement score by 1 each time it does a level deeper
						score = minimax(check.getBoardVals(), depth+1, !computerTurn, alpha, beta) - 1;
					/*	if (computerTurn) {
							alpha = Math.max(alpha, score);
							// prune the game state
							if (beta <= alpha) {
								return 0;
							}
						}else{
							beta = Math.min(beta, score);
							if(beta <= alpha){
								return 0;
							}
						} */
						if(depth != 0){
							check.setTmpBoard(getBoardState(depth-1));
						}
						if (depth == 1) {
							if(!scores.containsKey(score)){
								scores.put(score, i+" "+j);
							}
						}
					}
				}
			}
		} // Reached the as far as we want to go
			// Add the final value to the scores list
		
		return score;
	}
	
	private void saveState(int[][] tmpBoard, int depth){
		int[][] someBoard = new int[19][19];
		for(int i = 0; i < tmpBoard.length; i++){
			for(int j = 0; j < tmpBoard[i].length; j++){
				if(tmpBoard[i][j] == 0){
					someBoard[i][j] = 0;
				} else if(tmpBoard[i][j] == 1){
					someBoard[i][j] = 1;
				} else{
					someBoard[i][j] = 2;
				}
			}
		}
		if(boardStates.containsKey(depth)){
			boardStates.replace(depth, someBoard);
		}else{
			boardStates.put(depth, someBoard);
		}
	}
	
	public int[][] getBoardState(int depth){
		return boardStates.get(depth);
	}
	
	//Check if there is 3 stone available on the board
	private boolean threeOnBoard(CheckGameEnd check, int stone, int x, int y) {
		if(checkThreeAcross(check, stone, x, y)){
			return true;
		} else if(checkThreeDown(check, stone, x, y)){
			return true;
		} else if(checkThreeUpDiag(check, stone, x, y)){
			return true;
		}else if(checkThreeDownDiag(check, stone, x, y)){
			return true;
		}
		
		return false;
	}

	private boolean fourOnBoard(CheckGameEnd check, int stone, int x, int y) {
		if(checkFourAcross(check, stone, x, y)){
			return true;
		} else if(checkFourDown(check, stone, x, y)){
			return true;
		} else if(checkFourUpDiag(check, stone, x, y)){
			return true;
		}else if(checkFourDownDiag(check, stone, x, y)){
			return true;
		}
		return false;
	}

	private int getScore(CheckGameEnd check, int stone, int x, int y) {
		int score = 0;
		if (fourOnBoard(check, stone, x, y)) {
			score = 9;
		} else if (threeOnBoard(check, stone, x, y)) {
			score = 5;
		} else {
			score = 0;
		}
		return score;
	}
	
	private boolean checkThreeAcross(CheckGameEnd check, int stone, int x, int y){
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		//check for horizonal left
		for(int i = y-1; i > y-2; i--){
			if (i < 0) {
				break;
			}
			if(tmp[x][i] == stone){
				stonesInARow++;
			}else{
				break;
			}
		}
		//check for horizontal right
		for(int i = y+1; i < y+2; i++){
			if (i >= tmp.length) {
				break;
			}
			if(tmp[x][i] == stone){
				stonesInARow++;
			} else{
				break;
			}
		}
		if(stonesInARow >= 3){
			return true;
		}
		return false;
	}
	
	private boolean checkThreeDown(CheckGameEnd check, int stone, int x, int y){
		int tmp[][] = check.getBoardVals();
		int stonesInARow = 0;
		
		for (int i = x - 1; i > i - 2; i--) {
			if (i < 0) {
				break;
			}	
			if (tmp[i][y] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		// check up to 2 spaces below the place stone
		for (int i = x + 1; i < i + 2; i++) {
			if (i >= tmp.length) {
				break;
			}
			if (tmp[i][y] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}

		if (stonesInARow >= 3) {
			return true;
		}
		return false;
	}
	
	private boolean checkThreeUpDiag(CheckGameEnd check, int stone, int x, int y){
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY = y;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = x - 1; i > i - 2; i--) {
			if (i < 0) {
				break;
			}
			tmpY++;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY >= tmp[i].length) {
				break;
			}
			if (tmp[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		// reset tmpY to y
		tmpY = y;
		//// check diagonal bottom to top; left 4 and down 4 / <- that type of
		//// direction
		for (int i = x + 1; i < i + 2; i++) {
			if (i >= tmp.length) {
				break;
			}
			tmpY--;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY < 0) {
				break;
			}
			if (tmp[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//System.out.println(String.format("CheckUpward: x: %d y: %d stoneCount: %d", x, y, stonesInARow));
		if (stonesInARow >= 3) {
			return true;
		}
		return false;
	}
	
	private boolean checkThreeDownDiag(CheckGameEnd check, int stone, int x, int y){
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY = y;
		// check diagonal bottom to top; right 4 and down 4 \ <- that type of
		// direction
		for (int i = x - 1; i > i - 2; i--) {
			if (i < 0) {
				break;
			}
			tmpY--;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY < 0) {
				break;
			}
			if (tmp[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//reset y value
		tmpY = y;
		// check diagonal bottom to top; left 4 and up 4 \ <- that type of
		// direction
		for (int i = x + 1; i < i + 2; i++) {
			if (i >= tmp.length) {
				break;
			}
			tmpY++;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY >= tmp[i].length) {
				break;
			}
			if (tmp[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//System.out.println(String.format("CheckDownward: x: %d y: %d stoneCount: %d", x, y, stonesInARow));
		if (stonesInARow >= 3) {
			return true;
		}
		return false;
	}
	
	private boolean checkFourAcross(CheckGameEnd check, int stone, int x, int y){
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		//check for horizonal left
		for(int i = y-1; i > y-3; i--){
			if (i < 0) {
				break;
			}
			if(tmp[x][i] == stone){
				stonesInARow++;
			}else{
				break;
			}
		}
		//check for horizontal right
		for(int i = y+1; i < y+3; i++){
			if (i >= tmp.length) {
				break;
			}
			if(tmp[x][i] == stone){
				stonesInARow++;
			} else{
				break;
			}
		}
		if(stonesInARow >= 4){
			return true;
		}
		return false;
	}
	
	private boolean checkFourDown(CheckGameEnd check, int stone, int x, int y){
		int tmp[][] = check.getBoardVals();
		int stonesInARow = 0;
		
		for (int i = x - 1; i > i - 3; i--) {
			if (i < 0) {
				break;
			}	
			if (tmp[i][y] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		// check up to 2 spaces below the place stone
		for (int i = x + 1; i < i + 3; i++) {
			if (i >= tmp.length) {
				break;
			}
			if (tmp[i][y] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}

		if (stonesInARow >= 4) {
			return true;
		}
		return false;
	}
	
	private boolean checkFourUpDiag(CheckGameEnd check, int stone, int x, int y){
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY = y;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = x - 1; i > i - 3; i--) {
			if (i < 0) {
				break;
			}
			tmpY++;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY >= tmp[i].length) {
				break;
			}
			if (tmp[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		// reset tmpY to y
		tmpY = y;
		//// check diagonal bottom to top; left 4 and down 4 / <- that type of
		//// direction
		for (int i = x + 1; i < i + 3; i++) {
			if (i >= tmp.length) {
				break;
			}
			tmpY--;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY < 0) {
				break;
			}
			if (tmp[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//System.out.println(String.format("CheckUpward: x: %d y: %d stoneCount: %d", x, y, stonesInARow));
		if (stonesInARow >= 4) {
			return true;
		}
		return false;
	}
	
	private boolean checkFourDownDiag(CheckGameEnd check, int stone, int x, int y){
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY = y;
		// check diagonal bottom to top; right 4 and down 4 \ <- that type of
		// direction
		for (int i = x - 1; i > i - 3; i--) {
			if (i < 0) {
				break;
			}
			tmpY--;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY < 0) {
				break;
			}
			if (tmp[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//reset y value
		tmpY = y;
		// check diagonal bottom to top; left 4 and up 4 \ <- that type of
		// direction
		for (int i = x + 1; i < i + 3; i++) {
			if (i >= tmp.length) {
				break;
			}
			tmpY++;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY >= tmp[i].length) {
				break;
			}
			if (tmp[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//System.out.println(String.format("CheckDownward: x: %d y: %d stoneCount: %d", x, y, stonesInARow));
		if (stonesInARow >= 4) {
			return true;
		}
		return false;
	}
}
