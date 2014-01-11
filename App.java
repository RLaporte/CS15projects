package Pacman;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

/* 
 * I instantiate my top level object here. 
 * 
 * My design for Pacman is non-standard (I had a few of my own ideas - like not making a pen class), but
 * I've tried to explain everything in my header comments. 
 * 
 * The extra credit that I've done : 
 * 	   Fluid graphics 
 *     Better turning for Pacman 
 *     Ghosts do a 180 when they change modes 
 *     Game continues after winning (levels)
 *     
 * 
 * @ rl10
 */
public class App extends JFrame {

	public App() throws HeadlessException {
		super ("PacMan"); 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new MainPanel());
		this.pack();
		this.setVisible(true);
	
	}

	public static void main(String[] args) {
		new App ();

	}

}
