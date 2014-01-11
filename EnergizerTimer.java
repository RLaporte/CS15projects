package Pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/*
 *  Energizer timer, starts when PacMan eats an Energizer.
 *  Is instantiated by a Referee.
 */

public class EnergizerTimer extends Timer {
	
	private Referee _myRef;
	int counter; 

	public EnergizerTimer(Referee ref) {
		super(1, null);
		this.addActionListener (new MyMoveListener());
		_myRef = ref; 
		counter = 0;
	}
	
	private class MyMoveListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			counter ++;
			if (counter == 15000){
				_myRef.endEnergizerPower(); 
			}
		}
	}
	
	/* 
	 * Computational lag can be seen here - when I run the code from the sunlab, the energizer powerup 
	 * lasts more like 16 sec than 15. 
	 */

}
