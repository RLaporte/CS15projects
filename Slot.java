package Pacman;

import gfx.Ellipse;
import gfx.Rectangle;
import gfx.Shape;

import java.awt.Color;
import java.awt.Graphics2D;

public class Slot {
	
private int _slotType;
private Shape _slotShape;
private GamePanel _myGamePanel;
private Direction _SlotDirection;

/*
 * According to the number passed into the constructor, the Slot is going to represent a different 
 * section type of the board... this allows us to use the support code easily. 
 */

	public Slot(int indicator, GamePanel panel) {
		_myGamePanel = panel;
		_slotType = indicator;	
		
		switch (_slotType){
			case 0:
				_slotShape = new Rectangle (_myGamePanel); 
				_slotShape.setSize(Constants.SLOT_SIZE, Constants.SLOT_SIZE);
				_slotShape.setColor(new Color(0,0,204)); 
				_slotShape.setVisible(true); 
				_slotShape.setBorderWidth(0);
				break;
			case 1: 
				_slotShape = new Rectangle (_myGamePanel);
				_slotShape.setVisible(false); 
				_slotShape.setBorderWidth(0);
				break;
			case 2: 
				_slotShape = new Ellipse (_myGamePanel);
				_slotShape.setColor(new Color(255,255,255)); 
				_slotShape.setVisible(true);
				_slotShape.setSize(Constants.PELLET_SIZE, Constants.PELLET_SIZE);
				_slotShape.setBorderWidth(0);
				break; 
			case 3: 
				_slotShape = new Ellipse (_myGamePanel);
				_slotShape.setColor(new Color(255,255,255)); 
				_slotShape.setVisible(true);
				_slotShape.setSize(Constants.ENERGIZER_SIZE, Constants.ENERGIZER_SIZE);
				_slotShape.setBorderWidth(0);
				break;
			case 4: 
				_slotShape = new Rectangle (_myGamePanel);
				_slotShape.setVisible(false); 
				_slotShape.setBorderWidth(0);
				break;
			case 5: 
				_slotShape = new Rectangle (_myGamePanel);
				_slotShape.setVisible(false); 
				_slotShape.setBorderWidth(0);
				break;	
		}
	}
	
	public void  setLocation(int GridX, int GridY){
		switch (_slotType){
			case 0: case 1: case 4: case 5: 
				_slotShape.setLocation(GridX*Constants.SLOT_SIZE, GridY*Constants.SLOT_SIZE); 
				break;
			case 2: 
				_slotShape.setLocation((GridX+0.5)*Constants.SLOT_SIZE - Constants.PELLET_SIZE/2, (GridY+0.5)*Constants.SLOT_SIZE - Constants.PELLET_SIZE/2);
				break; 
			case 3: 
				_slotShape.setLocation((GridX+0.5)*Constants.SLOT_SIZE - Constants.ENERGIZER_SIZE/2, (GridY+0.5)*Constants.SLOT_SIZE - Constants.ENERGIZER_SIZE/2);
				break;
		}
	}
	
	public void paint (Graphics2D brush){
		_slotShape.paint(brush);
	}
	
	public int getType() {
		return _slotType; 
	}
	
	public void setDirection(Direction dir){
		_SlotDirection = dir;
	}
	
	public Direction getDirection () {
		return _SlotDirection; 
	}
	
	public boolean canBeMovedInto (){
		if (_slotType == 1 ||_slotType == 2 || _slotType == 3 || _slotType == 4 ){
			return true; 
		}
		else {
			return false;
		}
	}

}
