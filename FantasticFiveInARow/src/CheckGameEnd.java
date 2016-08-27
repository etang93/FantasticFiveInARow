import java.util.Arrays;

public class CheckGameEnd {

	private int stonesInARow;
	private int[][] boardVals;
	private int[][] originalState;

	CheckGameEnd() {
		boardVals = new int[19][19];
		originalState = new int[19][19];
		stonesInARow = 1;
		// Create a sea of "empty" slots
		for (int i = 0; i < boardVals.length; i++) {
			for (int j = 0; j < boardVals.length; j++) {
				boardVals[i][j] = 2;
				originalState[i][j] = 2;
			}
		}
	}
	
	
	public static int[][] deepCopy(int[][] boardVals2) {
	    if (boardVals2 == null) {
	        return null;
	    }

	    final int[][] result = new int[boardVals2.length][];
	    for (int i = 0; i < boardVals2.length; i++) {
	        result[i] = Arrays.copyOf(boardVals2[i], boardVals2[i].length);
	    }
	    
	    return result;
	}
	public void saveState(int[][] tmpBoard){
		originalState = deepCopy(boardVals);
	}
	
	public int[][] getState(){
		return originalState;
	}
	
	public void setTmpBoard(int[][] tmp){
		boardVals = deepCopy(tmp);
	}
	
	public int[][] getBoardVals(){
		return boardVals;
	}
	// update this board with the stone placed in the UI
	public void setBoardVal(int x, int y, int stone) {
		boardVals[x][y] = stone;
	}

	public void setActualBoardVal(int x, int y, int stone){
		originalState[x][y] = stone;
	}
	// Only check for the most recently placed stone
	public boolean Win(int x, int y, int stone) {
		boolean win = false;
		if (checkDown(x, y, stone)) {
			win = true;
		}
		if (checkAcross(x, y, stone)) {
			win = true;
		}
		if (checkDownwardDiag(x, y, stone)) {
			win = true;
		}
		if (checkUpwardDiag(x, y, stone)) {
			win = true;
		}

		return win;
	}

	private boolean checkDown(int x, int y, int stone) {
		boolean win = false;

		// check up to 4 spaces to the above the placed stone
		for (int i = x - 1; i > i - 4; i--) {
			if (i < 0) {
				break;
			}	
			////System.out.println(String.format("x: %d Y: %d", i, y));
			if (boardVals[i][y] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		// check up to 4 spaces below the place stone
		for (int i = x + 1; i < i + 4; i++) {
			if (i >= boardVals.length) {
				break;
			}
			//System.out.println(String.format("x: %d Y: %d", i, y));
			if (boardVals[i][y] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//System.out.println(String.format("CheckDown: x: %d y: %d stoneCount: %d", x, y, stonesInARow));
		if (stonesInARow >= 5) {
			win = true;
		}

		// reset the counter to 1
		stonesInARow = 1;
		return win;
	}

	private boolean checkAcross(int x, int y, int stone) {
		boolean win = false;
		// check up to 4 spaces to the left of the placed stone

		for (int i = y - 1; i > i - 4; i--) {

			if (i < 0) {
				break;
			}
			//System.out.println(String.format("x: %d Y: %d", x, i));
			if (boardVals[x][i] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		// check up to 4 spaces to the right of the place stone
		for (int i = y + 1; i < i + 4; i++) {
			if (i >= boardVals.length) {
				break;
			}
			//System.out.println(String.format("x: %d Y: %d", i, x));
			if (boardVals[x][i] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//System.out.println(String.format("Across: x: %d y: %d stoneCount: %d", x, y, stonesInARow));
		if (stonesInARow >= 5) {
			win = true;
		}

		// reset the counter to 1
		stonesInARow = 1;
		return win;
	}

	private boolean checkUpwardDiag(int x, int y, int stone) {
		boolean win = false;
		int tmpY = y;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = x - 1; i > i - 4; i--) {
			if (i < 0) {
				break;
			}
			tmpY++;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY >= boardVals[i].length) {
				break;
			}
			if (boardVals[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		// reset tmpY to y
		tmpY = y;
		//// check diagonal bottom to top; left 4 and down 4 / <- that type of
		//// direction
		for (int i = x + 1; i < i + 4; i++) {
			if (i >= boardVals.length) {
				break;
			}
			tmpY--;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY < 0) {
				break;
			}
			if (boardVals[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//System.out.println(String.format("CheckUpward: x: %d y: %d stoneCount: %d", x, y, stonesInARow));
		if (stonesInARow >= 5) {
			win = true;
		}
		
		// reset counter to 1
		stonesInARow = 1;
		return win;
	}

	private boolean checkDownwardDiag(int x, int y, int stone) {
		boolean win = false;
		int tmpY = y;
		// check diagonal bottom to top; right 4 and down 4 \ <- that type of
		// direction
		for (int i = x - 1; i > i - 4; i--) {
			if (i < 0) {
				break;
			}
			tmpY--;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY < 0) {
				break;
			}
			if (boardVals[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//reset y value
		tmpY = y;
		// check diagonal bottom to top; left 4 and up 4 \ <- that type of
		// direction
		for (int i = x + 1; i < i + 4; i++) {
			if (i >= boardVals.length) {
				break;
			}
			tmpY++;
			//System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY >= boardVals[i].length) {
				break;
			}
			if (boardVals[i][tmpY] == stone) {
				stonesInARow++;
			} else {
				break;
			}
		}
		//System.out.println(String.format("CheckDownward: x: %d y: %d stoneCount: %d", x, y, stonesInARow));
		if (stonesInARow >= 5) {
			win = true;
		}
		// reset counter to 1
		stonesInARow = 1;
		return win;
	}
}
