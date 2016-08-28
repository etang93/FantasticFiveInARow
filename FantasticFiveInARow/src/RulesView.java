import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RulesView extends JFrame{
	
	JPanel mainPanel;
	JButton btnClose;
	JLabel lblRules;
	JLabel lblTitle;
	
	RulesView(){
		super();
		
		initComponents();
	}
	
	private void initComponents()
	{
		final String rules = "The goal of the game is to get 5 of your pieces lined up in a row. You can do so vertically, horizontally or diagonally. You cannot place a stone on top of another stone. That is all the rules enjoy!";
		final String html1 = "<html><body style='width: ";
        final String html2 = "px'>";
        
		mainPanel = new JPanel();
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		lblTitle = new JLabel("Rules : ");
		lblRules = new JLabel(html1 + "200" + html2 + rules);
		
		mainPanel.setLayout(new GridLayout(0,1));
		mainPanel.add(lblTitle);
		mainPanel.add(lblRules);
		mainPanel.add(btnClose);
		
		setTitle("Rules");
		setLayout(new GridLayout(1,0));
		add(mainPanel);
		
		getRootPane().setDefaultButton(btnClose);
		setLocationRelativeTo(null);
		
		pack();
		setVisible(true);
	}

}
