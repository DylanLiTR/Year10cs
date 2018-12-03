package gameone;

import javax.swing.JFrame;

public class MyScreen extends JFrame {

/**
 * Basic window, inherits from JFrame
 * @author dylan.li
 * @since October 25, 2018
 */
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *  MyScreen default constructor
	 *  @param none, default constructor
	 */
	
	public MyScreen() {
		this.setSize(1440,900); // equivalent to MyCanvas size
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // exits game when window is closed
		this.setVisible(true); // makes window contents visible
	}

	public static void main(String[] args) {
		MyScreen screen = new MyScreen(); // creates the window
		MyCanvas canvas = new MyCanvas(); // creates the canvas
		screen.getContentPane().add(canvas); // puts canvas on screen
	}
}