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
		int maxKey = -10000;
		;
		int minKey = 10000;
		// create a deep copy of the array because java is stupid
		for (int i = 0; i < _board.length; i++) {
			for (int j = 0; j < _board[i].length; j++) {
				if (_board[i][j] == 0) {
					board[i][j] = 0;
				} else if (_board[i][j] == 1) {
					board[i][j] = 1;
				} else {
					board[i][j] = 2;
				}
			}
		}

		int test = minimax(_board, 0, true, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
		for (int key : scores.keySet()) {
			if (key > maxKey) {
				maxKey = key;
			}
			if (key < minKey) {
				minKey = key;
			}
		}

		String location = "";
		System.out.println("maxKey: " + maxKey);
		/*
		 * if(maxKey < -1*minKey){ location = scores.get(minKey); }else{
		 */
		location = scores.get(maxKey);
		// }

		String[] locationsSplit = location.split(" ");
		moveX = Integer.parseInt(locationsSplit[0]);
		moveY = Integer.parseInt(locationsSplit[1]);

		boardStates.clear();
		scores.clear();
	}

	public int getMoveX() {
		return moveX;
	}

	public int getMoveY() {
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

		// if(depth == 0){
		saveState(_board, depth);
		// }

		if (depth <= MAX_DEPTH) {
			int[][] tmpBoard = _board;

			check.setTmpBoard(tmpBoard);
			for (int i = 0; i < _board.length; i++) {
				for (int j = 0; j < _board[i].length; j++) {
					// tmpBoard = check.getBoardVals();
					// There are no stones nearby, so don't place them there and
					// don't waste time calculating
					if (!checkForNearbyStones(check, i, j, MAX_DEPTH)) {
						scores.put(-10000, i + " " + j);

					} else {

						/*
						 * if(depth != 0){ saveState(tmpBoard, depth); }
						 */

						if (tmpBoard[i][j] == 2) {
							if (computerTurn) {
								check.setBoardVal(i, j, stoneVal);
								tmpBoard = check.getBoardVals();
								currentStone = stoneVal;
								/*
								 * if(tmpBoard[0][0] == 1 && tmpBoard[0][1] == 1
								 * && tmpBoard[0][2] == 1) { System.out.println(
								 * "Error here"); }
								 */
								// saveState(check.getBoardVals(), depth);
							} else {
								check.setBoardVal(i, j, opponentStone);
								tmpBoard = check.getBoardVals();
								currentStone = opponentStone;
								// saveState(check.getBoardVals(), depth);
							}

							/*
							 * If there is a win on the board return There isn't
							 * a need to continue calculating
							 */
							if (check.Win(i, j, currentStone)) {
								if (currentStone == opponentStone) {
									int myScore = getScore(check, stoneVal, i, j);
									int opponentScore = getScore(check, opponentStone, i, j);
									return opponentScore - myScore - 100;
								} else {
									int myScore = getScore(check, stoneVal, i, j);
									int opponentScore = getScore(check, opponentStone, i, j);
									return opponentScore - myScore + 100;
								}
							}
							if (depth == MAX_DEPTH) {
								int myScore = getScore(check, stoneVal, i, j);
								int opponentScore = getScore(check, opponentStone, i, j);
								return opponentScore - myScore;
							}
							// decrement score by 1 each time it does a level
							// deeper
							int scoreAdjuster = 10;
							if (computerTurn) {
								scoreAdjuster = -10;
							}
							score = minimax(check.getBoardVals(), depth + 1, !computerTurn, alpha, beta)
									+ scoreAdjuster;
									/*
									 * if (computerTurn) { alpha =
									 * Math.max(alpha, score); // prune the game
									 * state if (beta <= alpha) { return 0; }
									 * }else{ beta = Math.min(beta, score);
									 * if(beta <= alpha){ return 0; } }
									 */

							// if(depth != 0){
							check.setTmpBoard(_board);
							// check.setTmpBoard(getBoardState(depth));
							// }
							if (depth == 1) {
								if (!scores.containsKey(score)) {
									scores.put(score, i + " " + j);
									System.out.println("Score is :" + score);
								} else {
									scores.replace(score, i + " " + j);
								}
							}
						}
					}

				}
			}
		} // Reached the as far as we want to go
			// Add the final value to the scores list

		return score;
	}

	private void saveState(int[][] tmpBoard, int depth) {
		int[][] someBoard = new int[19][19];
		for (int i = 0; i < tmpBoard.length; i++) {
			for (int j = 0; j < tmpBoard[i].length; j++) {
				if (tmpBoard[i][j] == 0) {
					someBoard[i][j] = 0;
				} else if (tmpBoard[i][j] == 1) {
					someBoard[i][j] = 1;
				} else {
					someBoard[i][j] = 2;
				}
			}
		}
		if (boardStates.containsKey(depth)) {
			boardStates.replace(depth, someBoard);
		} else {
			boardStates.put(depth, someBoard);
		}
	}

	public int[][] getBoardState(int depth) {
		return boardStates.get(depth);
	}

	private boolean twoOnBoard(CheckGameEnd check, int stone) {
		if (checkTwoAcross(check, stone)) {
			return true;
		} else if (checkTwoDown(check, stone)) {
			return true;
		} else if (checkTwoUpDiag(check, stone)) {
			return true;
		} else if (checkTwoDownDiag(check, stone)) {
			return true;
		}

		return false;
	}
	
	// Check if there is 3 stone available on the board
	private boolean threeOnBoard(CheckGameEnd check, int stone) {
		if (checkThreeAcross(check, stone)) {
			return true;
		} else if (checkThreeDown(check, stone)) {
			return true;
		} else if (checkThreeUpDiag(check, stone)) {
			return true;
		} else if (checkThreeDownDiag(check, stone)) {
			return true;
		}

		return false;
	}

	private boolean fourOnBoard(CheckGameEnd check, int stone) {
		if (checkFourAcross(check, stone)) {
			return true;
		} else if (checkFourDown(check, stone)) {
			return true;
		} else if (checkFourUpDiag(check, stone)) {
			return true;
		} else if (checkFourDownDiag(check, stone)) {
			return true;
		}
		return false;
	}

	private int getScore(CheckGameEnd check, int stone, int x, int y) {
		int score = 0;
		if (fourOnBoard(check, stone)) {
			score += 75;
		} else if (threeOnBoard(check, stone)) {
			score += 50;
		} else if (twoOnBoard(check, stone)) {
			score += 15;
		}
		return score;
	}

	private boolean checkTwoAcross(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		// check for horizonal left
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					if (stonesInARow == 2) {
						return true;
					}
				} else {
					stonesInARow = 0;
				}
			}
		}
		return false;
	}

	private boolean checkTwoDown(CheckGameEnd check, int stone) {
		int tmp[][] = check.getBoardVals();
		int stonesInARow = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					for (int k = i + 1; k < board.length; k++) {
						if (tmp[k][j] == stone) {
							stonesInARow++;
							if (stonesInARow == 2) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}

				} else {
					stonesInARow = 0;
				}
			}
		}
		return false;
	}

	private boolean checkTwoUpDiag(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY;
		int tmpX;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					tmpX = i;
					tmpY = j;
					for (int k = tmpX + 1; k < tmp.length; k++) {

						tmpY = tmpY + 1;
						if (tmpY >= tmp[k].length) {
							stonesInARow = 0;
							break;
						}

						if (tmp[k][tmpY] == stone) {
							stonesInARow++;
							if (stonesInARow == 2) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}
				}
			}
		}

		return false;
	}

	private boolean checkTwoDownDiag(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY;
		int tmpX;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					tmpX = i;
					tmpY = j;
					for (int k = tmpX + 1; k < tmp.length; k++) {

						tmpY = tmpY - 1;
						if (tmpY < 0) {
							stonesInARow = 0;
							break;
						}
						if (tmp[k][tmpY] == stone) {
							stonesInARow++;
							if (stonesInARow == 2) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}

				}
			}
		}

		return false;
	}
	
	private boolean checkThreeAcross(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		// check for horizonal left
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					if (stonesInARow == 3) {
						return true;
					}
				} else {
					stonesInARow = 0;
				}
			}
		}
		return false;
	}

	private boolean checkThreeDown(CheckGameEnd check, int stone) {
		int tmp[][] = check.getBoardVals();
		int stonesInARow = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					for (int k = i + 1; k < board.length; k++) {
						if (tmp[k][j] == stone) {
							stonesInARow++;
							if (stonesInARow == 3) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}

				} else {
					stonesInARow = 0;
				}
			}
		}
		return false;
	}

	private boolean checkThreeUpDiag(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY;
		int tmpX;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					tmpX = i;
					tmpY = j;
					for (int k = tmpX + 1; k < tmp.length; k++) {

						tmpY = tmpY + 1;
						if (tmpY >= tmp[k].length) {
							stonesInARow = 0;
							break;
						}

						if (tmp[k][tmpY] == stone) {
							stonesInARow++;
							if (stonesInARow == 3) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}
				}
			}
		}

		return false;
	}

	private boolean checkThreeDownDiag(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY;
		int tmpX;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					tmpX = i;
					tmpY = j;
					for (int k = tmpX + 1; k < tmp.length; k++) {

						tmpY = tmpY - 1;
						if (tmpY < 0) {
							stonesInARow = 0;
							break;
						}
						if (tmp[k][tmpY] == stone) {
							stonesInARow++;
							if (stonesInARow == 3) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}

				}
			}
		}

		return false;
	}

	private boolean checkFourAcross(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		// check for horizonal left
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					if (stonesInARow == 4) {
						return true;
					}
				} else {
					stonesInARow = 0;
				}
			}
		}
		return false;
	}

	private boolean checkFourDown(CheckGameEnd check, int stone) {
		int tmp[][] = check.getBoardVals();
		int stonesInARow = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					for (int k = i + 1; k < board.length; k++) {
						if (tmp[k][j] == stone) {
							stonesInARow++;
							if (stonesInARow == 4) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}

				} else {
					stonesInARow = 0;
				}
			}
		}
		return false;
	}

	private boolean checkFourUpDiag(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY;
		int tmpX;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					tmpX = i;
					tmpY = j;
					for (int k = tmpX + 1; k < tmp.length; k++) {

						tmpY = tmpY + 1;
						if (tmpY >= tmp[k].length) {
							stonesInARow = 0;
							break;
						}

						if (tmp[k][tmpY] == stone) {
							stonesInARow++;
							if (stonesInARow == 4) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}
				}
			}
		}

		return false;
	}

	private boolean checkFourDownDiag(CheckGameEnd check, int stone) {
		int[][] tmp = check.getBoardVals();
		int stonesInARow = 0;
		int tmpY;
		int tmpX;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				if (tmp[i][j] == stone) {
					stonesInARow++;
					tmpX = i;
					tmpY = j;
					for (int k = tmpX + 1; k < tmp.length; k++) {

						tmpY = tmpY - 1;
						if (tmpY < 0) {
							stonesInARow = 0;
							break;
						}
						if (tmp[k][tmpY] == stone) {
							stonesInARow++;
							if (stonesInARow == 4) {
								return true;
							}
						} else {
							stonesInARow = 0;
							break;
						}
					}

				}
			}
		}

		return false;
	}

	// if there aren't any stones nearby, why traverse?
	private boolean checkForNearbyStones(CheckGameEnd check, int x, int y, int depth) {
		if (checkNearbyAcross(check, x, y, depth)) {
			return true;
		} else if (checkNearbyDown(check, x, y, depth)) {
			return true;
		} else if (checkNearbyUpDiag(check, x, y, depth)) {
			return true;
		} else if (checkNearbyDownDiag(check, x, y, depth)) {
			return true;
		}
		return false;
	}

	// depth used to check x spaces away
	private boolean checkNearbyAcross(CheckGameEnd check, int x, int y, int depth) {
		int[][] tmp = check.getBoardVals();
		// check for horizonal left
		for (int i = y - 1; i > y - depth; i--) {
			if (i < 0) {
				break;
			}
			if (tmp[x][i] != 2) {
				return true;
			}
		}
		// check for horizontal right
		for (int i = y + 1; i < y + depth; i++) {
			if (i >= tmp.length) {
				break;
			}
			if (tmp[x][i] != 2) {
				return true;
			}
		}

		return false;
	}

	private boolean checkNearbyDown(CheckGameEnd check, int x, int y, int depth) {
		int tmp[][] = check.getBoardVals();

		for (int i = x - 1; i > x - depth; i--) {
			if (i < 0) {
				break;
			}
			if (tmp[i][y] != 2) {
				return true;
			}
		}
		// check up to 2 spaces below the place stone
		for (int i = x + 1; i < x + depth; i++) {
			if (i >= tmp.length) {
				break;
			}
			if (tmp[i][y] != 2) {
				return true;
			}
		}
		return false;
	}

	private boolean checkNearbyUpDiag(CheckGameEnd check, int x, int y, int depth) {
		int[][] tmp = check.getBoardVals();
		int tmpY = y;
		// check diagonal bottom to top; right 4 and up 4 / <- that type of
		// direction
		for (int i = x - 1; i > x - depth; i--) {
			if (i < 0) {
				break;
			}
			tmpY++;
			// System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY >= tmp[i].length) {
				break;
			}
			if (tmp[i][tmpY] != 2) {
				return true;
			}
		}
		// reset tmpY to y
		tmpY = y;
		//// check diagonal bottom to top; left 4 and down 4 / <- that type of
		//// direction
		for (int i = x + 1; i < x + depth; i++) {
			if (i >= tmp.length) {
				break;
			}
			tmpY--;
			// System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY < 0) {
				break;
			}
			if (tmp[i][tmpY] != 2) {
				return true;
			}
		}
		return false;
	}

	private boolean checkNearbyDownDiag(CheckGameEnd check, int x, int y, int depth) {
		int[][] tmp = check.getBoardVals();
		int tmpY = y;
		// check diagonal bottom to top; right 4 and down 4 \ <- that type of
		// direction
		for (int i = x - 1; i > x - depth; i--) {
			if (i < 0) {
				break;
			}
			tmpY--;
			// System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY < 0) {
				break;
			}
			if (tmp[i][tmpY] != 2) {
				return true;
			}
		}
		// reset y value
		tmpY = y;
		// check diagonal bottom to top; left 4 and up 4 \ <- that type of
		// direction
		for (int i = x + 1; i < x + depth; i++) {
			if (i >= tmp.length) {
				break;
			}
			tmpY++;
			// System.out.println(String.format("i: %d tmpY: %d", i, tmpY));
			if (tmpY >= tmp[i].length) {
				break;
			}
			if (tmp[i][tmpY] != 2) {
				return true;
			}
		}
		return false;
	}
}
