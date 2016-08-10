import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class RenjuSplashScreen extends JWindow {

	private int duration;

	RenjuSplashScreen(int _duration) {
		duration = _duration;
	}

	public void showSplash() {
		JPanel content = (JPanel)getContentPane();
		content.setBackground(Color.white);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 500;
		int height = 500;
		int x = (screen.width-width) / 2;
		int y = (screen.height-height) / 2;

		setBounds(x, y, width, height);

		JLabel pic = new JLabel(new ImageIcon("src/img/Renju.jpg"));
		JLabel title = new JLabel("Fantastic Five In A Row");
		title.setHorizontalAlignment(JLabel.CENTER);
		JLabel caption = new JLabel("A game also known as Renju");
		caption.setHorizontalAlignment(JLabel.CENTER);
		
		content.setLayout(new BorderLayout());
		content.add(pic, BorderLayout.CENTER);
		content.add(title, BorderLayout.NORTH);
		content.add(caption, BorderLayout.SOUTH);
		//pack();
		setVisible(true);

		try {
			Thread.sleep(duration);
		} catch (Exception e) {
		}

		setVisible(false);
		// this.dispose();
	}

}
