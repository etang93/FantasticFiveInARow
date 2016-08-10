import java.awt.BorderLayout;
import java.awt.GridLayout;

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
		mainFrame = new JFrame();
		pnlName = new JPanel();
		
		name = new JTextField();
		btnOK = new JButton("OK");
		
		lblTitle = new JLabel("Welcome to Fantastic Five In A Row!");
		lblName = new JLabel("Please Enter Your Name: ");
		
		pnlName.setLayout(new GridLayout(0, 2));
		pnlName.add(lblName);
		pnlName.add(name);
		
		mainFrame.setTitle("Player Information");
		
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(lblTitle, BorderLayout.NORTH);
		mainFrame.add(pnlName, BorderLayout.CENTER);
		mainFrame.add(btnOK, BorderLayout.SOUTH);
		
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
