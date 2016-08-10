import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutView {

	private JFrame mainFrame;
	private JLabel lblDeveloper;
	private JLabel lblDevelopedFor;
	private JLabel lblCredits;
	private JButton btnOk;
	
	AboutView(){
		initComponents();
	}
	
	public void initComponents(){
		mainFrame = new JFrame();
		lblDeveloper = new JLabel("Developed by: Eddie Tang (ET354@DREXEL.EDU");
		lblDevelopedFor = new JLabel("Developed for Drexel: CS338 Graphical User Interfaces");
		lblCredits = new JLabel("Credits to Renju Radhakrishnan for the splashscreen image");
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainFrame.dispose();
			}
		});
		
		mainFrame.setLayout(new GridLayout(0,1));
		mainFrame.add(lblDeveloper);
		mainFrame.add(lblDevelopedFor);
		mainFrame.add(lblCredits);
		mainFrame.add(btnOk);
		mainFrame.setTitle("About");
		mainFrame.setLocationRelativeTo(null);
		
		//mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
