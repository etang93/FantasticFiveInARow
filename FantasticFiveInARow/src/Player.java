
public class Player {
	private String name;
	private int turnVal;
	
	Player(String _name, int _turnVal){
		name = _name;
		turnVal = _turnVal;
	}
	
	public int getTurnVal(){
		return turnVal;
	}
	
	public String getName(){
		return name;
	}
	
	
}
