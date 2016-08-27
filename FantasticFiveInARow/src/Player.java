
public class Player {
	protected String name;
	protected int turnVal;
	protected boolean isComputer;
	
	Player(String _name, int _turnVal, boolean _isComputer){
		name = _name;
		turnVal = _turnVal;
		isComputer = _isComputer;
	}
	
	public int getTurnVal(){
		return turnVal;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean getIsComputer(){
		return isComputer;
	}
	
	public void move(int[][] board){
		//players just click onto the grid
	}
	
}
