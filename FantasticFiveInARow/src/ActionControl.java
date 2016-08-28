import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class ActionControl implements java.awt.event.ActionListener {

	BoardView boardView;
	SettingsView settingsView;
	PlayerNameView playerView;
	Model model;
	Player currentPlayer;

	ActionControl(PlayerNameView _playerview, Model _model) {
		playerView = _playerview;
		model = _model;
		playerView.addActionController(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final JComponent source = (JComponent) e.getSource();

		if (playerView.btnOK == source) {
			int playerTurn = model.setPlayerTurn();
			savePlayer(playerView.getName(), playerTurn);
			if (playerTurn == 0) {
				launchSettings();
			} else {
				if (settingsView.rbnTimerOn.isSelected()) {
					createBoard(true, settingsView.getTime());
				} else {
					createBoard(false, 0);
				}
			}
			playerView.close();
		} else if (settingsView.btnOk == source) {
			if (settingsView.rbn2Players.isSelected()) {
				playerView = new PlayerNameView();
				playerView.addActionController(this);
			} else if (settingsView.rbnComputer.isSelected()) {
				saveComputer(model.setPlayerTurn());
				createBoard(false, 0);
			}
			settingsView.close();
		} else if (source == settingsView.rbn2Players && settingsView.rbn2Players.isSelected()) {
			settingsView.rbnTimerOn.setEnabled(true);
			settingsView.rbnTimerOn.setSelected(true);
			settingsView.rbnTimerOff.setEnabled(true);
			settingsView.spinTime.setEnabled(true);
		} else if (source == settingsView.rbnComputer && settingsView.rbnComputer.isSelected()) {
			settingsView.rbnTimerOn.setEnabled(false);
			settingsView.rbnTimerOff.setEnabled(false);
			settingsView.spinTime.setEnabled(false);
		} else if (source == settingsView.rbnTimerOn && settingsView.rbnTimerOn.isSelected()) {
			settingsView.spinTime.setEnabled(true);
		} else if (source == settingsView.rbnTimerOff && settingsView.rbnTimerOff.isSelected()) {
			settingsView.spinTime.setEnabled(false);
		} else if (source == boardView.rules) {
			boardView.showRules();
		} else if (source == boardView.newGame) {
			if (boardView.newGame()) {
				newGame();
			}
		} else if (source == boardView.about) {
			boardView.showAbout();
		} else if (source == boardView.forfeit) {
			boardView.forfeitMessage(currentPlayer);
			boolean ng = boardView.playAgain(currentPlayer);
			if (ng) {
				newGame();
			}
		} else if (source == boardView.btnForfeit) {
			boardView.forfeitMessage(currentPlayer);
			boolean ng = boardView.playAgain(currentPlayer);
			if (ng) {
				newGame();
			}
		} else if (source == boardView.exit) {
			boardView.exit();
		} else {
			for (int i = 0; i < 19; i++) {
				for (int j = 0; j < 19; j++) {
					if (boardView.goButtons[i][j] == source) {
						boardView.setPiece(currentPlayer, boardView.goButtons[i][j], i, j);
						model.setOriginalBoardVal(i, j, currentPlayer.getTurnVal());
						if (model.checkWin(i, j, currentPlayer.getTurnVal())) {
							boolean ng = boardView.playAgain(currentPlayer);
							if (ng) {
								newGame();
							}
						} else {
							currentPlayer = model.changePlayer(currentPlayer);
							if (settingsView.rbnTimerOn.isSelected() && settingsView.rbnTimerOn.isEnabled()) {
								boardView.resetTime();
							}
							move();
						}
					}
				}
			}
		}
	}

	private void newGame() {
		Player p1 = model.getFirstPlayer();
		Player p2 = model.getSecondPlayer();
		
		model = null;
		model = new Model();
		
		model.savePlayer(p1);
		model.savePlayer(p2);
		
		playerView.addActionController(this);

		boardView.addActionController(this);
		currentPlayer.getTurnVal();

	}

	public void savePlayer(String name, int turn) {
		Player player = new Player(name, turn, false);
		model.savePlayer(player);
	}

	private void saveComputer(int turn) {
		Computer cmp = new Computer("Computer", turn, true);
		model.savePlayer(cmp);
	}

	private void move() {
		model.setOriginalBoardState();
		if (currentPlayer.getIsComputer()) {
			int moveX;
			int moveY;
			Computer tmp = (Computer) currentPlayer;
			boardView.disableButtons();
			currentPlayer.move(model.getCheckerBoard());
			moveX = tmp.getMoveX();
			moveY = tmp.getMoveY();
			model.setOriginalBoardState();
			boardView.computerClick(moveX, moveY);
		} else {
			boardView.enableButtons(model.getCheckerBoard());
			currentPlayer.move(model.getCheckerBoard());
		}

	}

	private void createBoard(boolean timerOn, int time) {
		currentPlayer = model.getFirstPlayer();
		boardView = new BoardView(model.getFirstPlayer(), model.getSecondPlayer(), timerOn, time);
		boardView.addActionController(this);
	}

	private void launchSettings() {
		settingsView = new SettingsView();
		settingsView.addActionController(this);
	}
}
