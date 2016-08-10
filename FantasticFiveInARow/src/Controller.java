import java.awt.event.ActionEvent;

public class Controller {

	//
	PlayerNameView playerName;
	Model model;
	ActionControl actionController;
	
	public Controller(){
		//board = new BoardView();
		playerName = new PlayerNameView();
		model = new Model();
		
		//actionController = new ActionControl(board, model);
		actionController = new ActionControl(playerName, model);
		startUp();
	}
	
	public void startUp(){
	//	board.addActionController(actionController);
	}
}
