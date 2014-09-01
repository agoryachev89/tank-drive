package tank;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter {
	public boolean leftPressed = false;
	public boolean rightPressed = false;
	public boolean upPressed = false;
	public boolean downPressed = false;
	public boolean spacePressed = false;
	
	public KeyInputHandler() {

	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.leftPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.rightPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.upPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.downPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.spacePressed = true;
		}
	} 
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.leftPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.rightPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.upPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.downPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.spacePressed = false;
		}
	}
}
