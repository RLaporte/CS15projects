package Pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/* 
 * Special timer class for when a ghost is sent to a Pen. Instantiated in Ghosts.
 */

public class PenTimer extends Timer {
	
	private Ghost _myGhost;
	private int time;
	private int counter;

	public PenTimer (int timestep, Ghost aGhost) {
		super(1, null);
		this.addActionListener (new MyMoveListener());
		_myGhost = aGhost;
		time = timestep; 
		
	}
		
	private class MyMoveListener implements ActionListener {
			
		public void actionPerformed(ActionEvent e) {
			if (counter == time){
				_myGhost.freeFromPen();
			}
			counter++; 
		}
	}
	

}
