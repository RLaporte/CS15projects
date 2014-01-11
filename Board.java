package Pacman;

import java.awt.Graphics2D;

import cs015.fnl.PacmanSupport.SupportMap;

/* The board class holds a Slot [] [] ( a two dimensional Slot Array ), which represents the playing field. 
 * Changes of the Slot [] [] is how the board changes ( ie. how energizers/pellets are eaten );
 */

public class Board {
	
	private int [] [] Map; 
	private Slot [] [] SlotCollection; 
	private GamePanel _myGamePanel;

	public Board(GamePanel panel) {
		_myGamePanel = panel;
		
		Map = new int [23] [23];
		SupportMap mySupportMap = new SupportMap(); 
		Map = mySupportMap.getMap();
		SlotCollection = new Slot [23] [23];
		
		
		for (int i=0; i<23; i++) {
			for (int j=0; j<23; j++){ 
				SlotCollection [i] [j] = new Slot (Map [i][j], _myGamePanel);
				SlotCollection [i] [j].setLocation(j,i); 
			}
		}
	}
	
	public void paintMap(Graphics2D brush){
		for (int i=0; i<23; i++) {
			for (int j=0; j<23; j++){ 
				SlotCollection [i] [j].paint(brush);
			}
		}	
	}
	
	public Slot [] [] getSlotCollection (){
		return SlotCollection; 
	}
	
	public void regenerateBoard() { 
		for (int i=0; i<23; i++) {
			for (int j=0; j<23; j++){ 
				SlotCollection [i] [j] = new Slot (Map [i][j], _myGamePanel);
				SlotCollection [i] [j].setLocation(j,i); 
			}
		}
	}
	
}

	
	
	

