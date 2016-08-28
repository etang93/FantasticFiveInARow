import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

public class CountDownTimer extends JPanel {

	private Timer timer;
	private long startTime = -1;
	private long duration = 1000 * 60;
	private JLabel label;
	private long clockTime;
	private BoardView boardView;
	private boolean played;
	private boolean stopper; 
	
	CountDownTimer(long minutes, BoardView _boardView) {
		super();
		duration = minutes * 60 * 1000;
		boardView = _boardView;
		label = new JLabel();
		played = false;
		stopper = false;
		setupTimer();
	}

	private void setupTimer() {

		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (startTime < 0) {
					startTime = System.currentTimeMillis();
				}
				long now = System.currentTimeMillis();
				clockTime = now - startTime;
				if (clockTime >= duration && !stopper) {
					clockTime = duration;
					if (!played) {
						played = true;
						boardView.timesUp();
					}
					timer.stop();
				}
				SimpleDateFormat df = new SimpleDateFormat("mm:ss:SSS");
				label.setText(df.format(duration - clockTime));
			}
		});

		timer.setInitialDelay(0);

		if (!timer.isRunning()) {
			startTime = -1;
			timer.start();
		}

		add(label);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(50, 50);
	}

	public void updateTime() {
		setupTimer();
	}
	
	public void stopTimer(){
		duration = 0;
		played = true;
		setupTimer();
	}

}
