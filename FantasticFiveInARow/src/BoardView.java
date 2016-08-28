import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardView extends JFrame{

	//JFrame mainFrame;
	JButton[][] goButtons;
	JLabel lblTitle;
	JLabel lblTurnIndicator;
	JLabel lblOpponentLastMove;
	JPanel boardInstance;
	JPanel timerAndTurn;
	JPanel southPanel;
	//2player system
	Player p1;
	Player p2;
	//menu objects
	JMenuBar menuBar;
	JMenu mnuFile;
	JMenu mnuAbout;
	//File menu options
	JMenuItem newGame;
	JMenuItem forfeit;
	JMenuItem exit;
	//About menu options
	JMenuItem rules;
	JMenuItem about;
	//Views
	AboutView aboutView;
	RulesView rulesView;
	//Buttons
	JButton btnForfeit;
	
	long timer;
	boolean timerOn;
	CountDownTimer cdtTimer;
	
	int currentTurn;
	
	public BoardView(Player _p1, Player _p2, boolean _timerOn, long _time){
		super();
		p1 = _p1;
		p2 = _p2;
		timerOn = _timerOn;
		timer = _time;
		createScreen();
	}
	
	private void createScreen(){
	    getContentPane();
	    
		boardInstance = new JPanel();
		timerAndTurn = new JPanel();
		southPanel = new JPanel();
		
		lblTitle = new JLabel("Fantastic Five In A Row");
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		
		lblTurnIndicator = new JLabel(String.format("%s's Turn", p1.getName()));
		lblTurnIndicator.setHorizontalAlignment(JLabel.CENTER);
		
		lblOpponentLastMove = new JLabel();
		lblOpponentLastMove.setHorizontalAlignment(JLabel.CENTER);
		goButtons = new JButton[19][19];
		
		boardInstance = board();
		
		
		btnForfeit = new JButton("Forfeit");
		
		timerAndTurn.setLayout(new GridLayout(0, 1));
		
		if(timerOn){
			cdtTimer = new CountDownTimer(timer, this);
			timerAndTurn.add(cdtTimer);
		}
		timerAndTurn.add(lblTurnIndicator);
		
		southPanel.setLayout(new GridLayout(0, 1));
		
		southPanel.add(btnForfeit);
		southPanel.add(lblOpponentLastMove);
		
		setTitle("Fantastic Five In A Row");
		setJMenuBar(menuBar());
		setLayout(new BorderLayout());
				
		add(timerAndTurn, BorderLayout.NORTH);
		add(boardInstance, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private JMenuBar menuBar(){
		
		menuBar = new JMenuBar();
		mnuFile = new JMenu("File");
		mnuAbout = new JMenu("About");
		
		newGame = new JMenuItem("New Game");
		forfeit = new JMenuItem("Forfeit");
		exit = new JMenuItem("Exit");
		
		rules = new JMenuItem("Rules");
		about = new JMenuItem("About");
		
		mnuFile.add(newGame);
		mnuFile.add(forfeit);
		mnuFile.add(exit);
		
		mnuAbout.add(rules);
		mnuAbout.add(about);
		
		menuBar.add(mnuFile);
		menuBar.add(mnuAbout);
		return menuBar;
	}
	
	private JPanel board(){
		JPanel goBoard = new JPanel();
		goBoard.setLayout(new GridLayout(19, 19));

		for(int i = 0; i < goButtons.length; i++){
			for(int j = 0; j < goButtons[i].length; j++){
				goButtons[i][j] = new JButton(" ");
				goButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				goButtons[i][j].setBackground(new Color(210,105,30));;
				goButtons[i][j].setToolTipText(String.format("(%d, %d)", j, i));
				goBoard.add(goButtons[i][j]);
			}
		}
		return goBoard;
	}
	
	public void addActionController(ActionControl controller){
		for(int i = 0; i < goButtons.length; i++){
			for(int j = 0; j < goButtons[i].length; j++){
				goButtons[i][j].addActionListener(controller);
			}
		}
		newGame.addActionListener(controller);
		forfeit.addActionListener(controller);
		exit.addActionListener(controller);
		rules.addActionListener(controller);
		about.addActionListener(controller);
		btnForfeit.addActionListener(controller);
	}
	
	public void setPiece(Player player, JButton button, int col, int row){
		if(player.getTurnVal() == 0){
			button.setIcon(new ImageIcon("src/img/Black_Piece.png"));
			button.setDisabledIcon(new ImageIcon("src/img/Black_Piece.png"));
			lblTurnIndicator.setText(String.format("%s's Turn", p2.getName()));
			button.setToolTipText(button.getToolTipText() + ", black");
		}else{
			button.setIcon(new ImageIcon("src/img/White_Piece.png"));
			button.setDisabledIcon(new ImageIcon("src/img/White_Piece.png"));
			lblTurnIndicator.setText(String.format("%s's Turn", p1.getName()));
			button.setToolTipText(button.getToolTipText() + ", white");
		}
		
		button.setEnabled(false);
		lblOpponentLastMove.setText(String.format("%s placed a stone at (%d, %d)", player.getName(), row, col));
		
		currentTurn = player.getTurnVal();
	}
	
	public void showRules(){
		rulesView = new RulesView();
	}
	
	public void disableButtons(){
		for(int i = 0; i < goButtons.length; i++){
			for(int j = 0; j < goButtons[i].length; j++){
				goButtons[i][j].setEnabled(false);
			}
		}
	}
	
	public void enableButtons(int[][] board){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] == 2){
					goButtons[i][j].setEnabled(true);
				}
			}
		}
	}
	
	public void computerClick(int x, int y){
		goButtons[x][y].setEnabled(true);
		goButtons[x][y].doClick();
	}
	
	public void forfeitMessage(Player currentPlayer){
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to forfeit?", "Forfeit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			if(timerOn){
				cdtTimer.stopTimer();
			}
		}
		
	}
	
	public void showAbout(){
		aboutView = new AboutView();
	}
	
	public void exit(){
		System.exit(0);
	}
	
	public void resetTime(){
		cdtTimer.updateTime();
	}
	
	public void timesUp(){
		Player currentPlayer;
		if(currentTurn == p1.getTurnVal()){
			currentPlayer = p2;
		} else {
			currentPlayer = p1;
		}
		JOptionPane.showMessageDialog(null, String.format("Time is up %s loses!", currentPlayer.getName()), "Time's Up", JOptionPane.INFORMATION_MESSAGE, null);
		
		playAgain(currentPlayer);
	}
	
	public boolean playAgain(Player currentPlayer){
		disableButtons();
		if(JOptionPane.showConfirmDialog(null, String.format("%s WON! Play Again?", currentPlayer.getName()), "Winner!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			setupNewGame();
			return true;
		}
		return false;
	}
	
	private void setupNewGame(){
		getContentPane().removeAll();
		revalidate();
		createScreen();
		revalidate();
		repaint();
	}
	
	public boolean newGame(){
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to play a new game?", "New Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			
			return true;
		}
		return false;
	}
}
