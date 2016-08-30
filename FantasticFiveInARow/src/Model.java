
public class Model {
	
	int turn;
	Player[] players;
	CheckGameEnd checker;
	Model(){
		checker = new CheckGameEnd();
		initialize();
	}
	
	private void initialize(){
		//set maximum amount of players to 2
		players = new Player[2];
		turn = 0;
	}
	
	public int setPlayerTurn(){
		if(turn > 1)
			return 1;
		int ret = turn;
		turn++;
		return ret;
	}
	
	public void savePlayer(Player player){
		players[player.getTurnVal()] = player;
		if(player.turnVal == 0)
			turn = 1;
	}
	
	public void addStone(int x, int y, int stone){
		checker.setBoardVal(x, y, stone);
	}
	
	public boolean checkWin(int x, int y, int stone){
		return checker.Win(x, y, stone);
	}
	
	public Player getFirstPlayer(){
		return players[0];
	}
	
	public Player getSecondPlayer(){
		return players[1];
	}
	
	public Player changePlayer(Player player){
		if(player.getTurnVal() == 0)
			return players[1];
		else 
			return players[0];
	}
	
	public void newGame(){
		checker = new CheckGameEnd();
	}
	
	public int[][] getCheckerBoard(){
		return checker.getBoardVals();
	}
	
	public void saveState(int[][] board){
		checker.saveState(board);
	}
	
	public void setOriginalBoardState(){
		checker.setTmpBoard(checker.getState());
	}
	
	public void setOriginalBoardVal(int x, int y, int stone){
		checker.setActualBoardVal(x, y, stone);
	}
	
	public int[][] getCleanState(){
		return checker.getState();
	}
}
