package Pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/*
 * General Timer for the game. Fires Action Performed every 50 ms ( I put in fluid graphics ); 
 * _behaviorCounter serves as a counter which helps regulate what behanior mode the Ghosts are in. 
 * It alternates between 20 seconds in Target mode ( ie. we follow a specific goal related to PacMan's position ) 
 * and 10 seconds in Scatter; 
 */
public class MyTimer extends Timer {

	private GamePanel _myGamePanel; 

	public MyTimer(GamePanel panel) {
		super(Constants.TIMESTEP/10, null);
		this.addActionListener (new MyMoveListener());
		_myGamePanel = panel; 
	}
	
	private class MyMoveListener implements ActionListener {
		
		private int behaviorCounter; 
		
		public void actionPerformed(ActionEvent e) {
			behaviorCounter++;
			_myGamePanel.moveAndRepaint();
			if (behaviorCounter == 400){
				_myGamePanel.setGhostBehavior(GhostBehavior.SCATTER);
			}
			if (behaviorCounter== 600){
				_myGamePanel.setGhostBehavior(GhostBehavior.TARGETED);
				behaviorCounter = 0; 
			}
		}
	}

}
