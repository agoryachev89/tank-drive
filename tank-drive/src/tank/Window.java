package tank;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Window(Game game) {		
		setSize(902, 475);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(game);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
