import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerNameView{
	
	JFrame mainFrame;
	JLabel lblTitle;
	JLabel lblName;
	private JTextField name;
	JButton btnOK;
	JPanel pnlName;
	
	PlayerNameView(){
		createScreen();
	}
	
	private void createScreen(){
		JPanel borderOfBorder = new JPanel();
		mainFrame = new JFrame();
		pnlName = new JPanel();
		
		mainFrame.setSize(new Dimension(500, 500));
		
		name = new JTextField();
		btnOK = new JButton("OK");
		
		lblTitle = new JLabel("Welcome to Fantastic Five In A Row!");
		lblName = new JLabel("Please Enter Your Name: ");
		
		pnlName.setLayout(new GridLayout(0, 2));
		pnlName.add(lblName);
		pnlName.add(name);
		
		mainFrame.setTitle("Player Information");
		
		borderOfBorder.setLayout(new BorderLayout());
		borderOfBorder.add(lblTitle, BorderLayout.NORTH);
		borderOfBorder.add(pnlName, BorderLayout.CENTER);
	
		
		mainFrame.setLayout(new BorderLayout());
		
		mainFrame.add(borderOfBorder, BorderLayout.NORTH);
		mainFrame.add(btnOK, BorderLayout.CENTER);
		mainFrame.add(Box.createGlue(), BorderLayout.WEST);
		mainFrame.add(Box.createGlue(), BorderLayout.EAST);
		
		mainFrame.getRootPane().setDefaultButton(btnOK);
		mainFrame.setLocationRelativeTo(null);
		
		mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	public void addActionController(ActionControl controller){
		btnOK.addActionListener(controller);
	}
	
	public String getName(){
		return name.getText();
	}
	
	public void close(){
		mainFrame.dispose();
	}
	
}
