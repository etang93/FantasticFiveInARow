import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;

public class SettingsView{

	JFrame mainFrame;
	JPanel pnlSettings;
	
	private JLabel lblGameType;
	private JLabel lblTimer;
	private JLabel lblTime;
	private JLabel lblMinutes;
	
	private ButtonGroup btngrpPlayers;
	public JRadioButton rbn2Players;
	public JRadioButton rbnComputer;
	
	private ButtonGroup btngrpTimer;
	public JRadioButton rbnTimerOn;
	public JRadioButton rbnTimerOff;
	
	public SpinnerNumberModel snm;
	public JSpinner spinTime;
	
	public JButton btnOk;
	
	SettingsView(){
		createScreen();
	}
	
	private void createScreen(){
		mainFrame = new JFrame();
		
		pnlSettings = new JPanel();
		
		lblGameType = new JLabel("Game Type: ");
		lblTimer = new JLabel("Timer: ");
		lblTime = new JLabel("Time: ");
		lblMinutes = new JLabel("Minutes");
		
		btngrpPlayers = new ButtonGroup();
		rbn2Players = new JRadioButton("2 Players");
		rbnComputer = new JRadioButton("Computer");
		
		rbnComputer.setSelected(true);
		
		btngrpPlayers.add(rbn2Players);
		btngrpPlayers.add(rbnComputer);
		
		btngrpTimer = new ButtonGroup();
		rbnTimerOn = new JRadioButton("On");
		rbnTimerOff = new JRadioButton("Off");
		
		rbnTimerOn.setSelected(true);
		
		btngrpTimer.add(rbnTimerOn);
		btngrpTimer.add(rbnTimerOff);
		
		snm = new SpinnerNumberModel(5, 1, 9, 1);
		spinTime = new JSpinner(snm);
		spinTime.setEditor(new JSpinner.DefaultEditor(spinTime));
		
		btnOk = new JButton("OK");
		
		if(rbnComputer.isSelected()){
			rbnTimerOn.setEnabled(false);
			rbnTimerOff.setEnabled(false);
			spinTime.setEnabled(false);
		}
		
		pnlSettings.setLayout(new GridLayout(0, 3));
		pnlSettings.add(lblGameType);
		pnlSettings.add(rbnComputer);
		pnlSettings.add(rbn2Players);
		pnlSettings.add(lblTimer);
		pnlSettings.add(rbnTimerOn);
		pnlSettings.add(rbnTimerOff);
		pnlSettings.add(lblTime);
		pnlSettings.add(spinTime);
		pnlSettings.add(lblMinutes);
		
		mainFrame.setTitle("Settings");
		
		mainFrame.setLayout(new BorderLayout());
		
		mainFrame.add(pnlSettings, BorderLayout.CENTER);
		mainFrame.add(btnOk, BorderLayout.SOUTH);
		
		mainFrame.getRootPane().setDefaultButton(btnOk);
		mainFrame.setLocationRelativeTo(null);
		
		mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	public void addActionController(ActionControl controller){
		btnOk.addActionListener(controller);
		rbnTimerOn.addActionListener(controller);
		rbnTimerOff.addActionListener(controller);
		rbn2Players.addActionListener(controller);;
		rbnComputer.addActionListener(controller);
	}
	
	public void addKeyController(KeyControl controller){
		rbnTimerOn.addKeyListener(controller);
		rbnTimerOff.addKeyListener(controller);
	}
	
	public void close(){
		mainFrame.dispose();
	}
	
	public int getTime(){
		return (int) spinTime.getValue();
	}
}
