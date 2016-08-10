import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

public class KeyControl implements KeyListener{
	SettingsView settingsView;
	
	KeyControl(SettingsView _settingsView){
		
		settingsView = _settingsView;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		final JComponent source = (JComponent)e.getSource();
		
	}
	
	
}
