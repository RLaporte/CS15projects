package Pacman;

import gfx.Ellipse;

import java.awt.Color;

/*
 * PacMan knows how to move around, which is why its constructor needs to be passed in a Slot [] [];
 * PacMan has enhanced turning - If the user presses a directional key and Pacman is NOT able to 
 * immediately move in that direction, he will keep going and turn in that direction as soon as he can
 */

public class PacMan extends Ellipse {
	
private Direction _desiredDirection; 
private Direction _currentDirection; 
private Slot [] [] Slots;
private boolean poweredUp;
private int _movementCounter;

	public PacMan(GamePanel panel, Slot [] [] slotCollection) {
		super(panel);
		Slots = slotCollection;
		
		this.setSize(Constants.SLOT_SIZE, Constants.SLOT_SIZE);
		this.setBorderWidth(0);
		this.setColor(new Color(255,255,0)); 
		this.setVisible(true);
		this.setWrapping(false); 
		this.setLocation ((double)11*Constants.SLOT_SIZE,(double)17*Constants.SLOT_SIZE);
		poweredUp=false; 
		_movementCounter =0 ; 
		
		_desiredDirection = Direction.RIGHT;
		_currentDirection = Direction.RIGHT; 
		
		double X = this.getX();
		double Y = this.getY();
		
	}
	
	public void setDesiredDirection (Direction dir) {
		_desiredDirection = dir; 
	}
	
	public int  getGridX (){
		return ((int)this.getX()/Constants.SLOT_SIZE);
	}
	
	public int  getGridY (){
		return ((int)this.getY()/Constants.SLOT_SIZE);
	}
	
	public boolean getPowerStatus (){
		return poweredUp; 
	}
	
	public void setPowerStatus(boolean bool){
		poweredUp = bool;
	}
	
	public void resetMovementCounter (){
		_movementCounter =0 ;
	}
	
	public void move(){
		if (_movementCounter ==0 ){
			this.findNewDirection();
		}
		
		if (_currentDirection != null){
			switch (_currentDirection){
				case UP: 
					this.setLocation(this.getX(), this.getY()-(Constants.SLOT_SIZE/10));
					break;
				case DOWN: 
					this.setLocation(this.getX(), this.getY()+(Constants.SLOT_SIZE/10));
					break;
				case RIGHT: 
					this.setLocation(this.getX()+(Constants.SLOT_SIZE/10), this.getY());
					break;
				case LEFT: 
					this.setLocation(this.getX()-(Constants.SLOT_SIZE/10), this.getY());
					break;
			}
		}
		_movementCounter++; 
		
		if (_movementCounter==10 ){
			_movementCounter =0;
		}
		
	}
	
	
	private void findNewDirection (){ 
		
		int GridX = this.getGridX();
		int GridY = this.getGridY();
		
		switch (_desiredDirection){
			case UP: 
				if (Slots [GridY-1] [GridX].canBeMovedInto()){
					_currentDirection = _desiredDirection;
				}
				break; 
			case DOWN: 
				if (Slots [GridY+1] [GridX].canBeMovedInto()){
					_currentDirection = _desiredDirection;
				}
				break; 
			case RIGHT: 
				if (GridX==22 && GridY== 11){
					_currentDirection = _desiredDirection;
					this.setLocation(0, 11*Constants.SLOT_SIZE);
				}
				else if (Slots [GridY] [GridX+1].canBeMovedInto()){
					_currentDirection = _desiredDirection;
				}
				break; 
			case LEFT: 
				if (GridX==0 && GridY == 11){
					_currentDirection = _desiredDirection;
					this.setLocation(22*Constants.SLOT_SIZE, 11*Constants.SLOT_SIZE);
				}
				else if (Slots [GridY] [GridX-1].canBeMovedInto()){
					_currentDirection = _desiredDirection;
				}
				break;	
		}
	
		if (_currentDirection != null){
			switch (_currentDirection){
					case UP: 
						if (Slots [GridY-1] [GridX].canBeMovedInto()==false){
							_currentDirection = null; 
						}
						break; 
					case DOWN: 
					if (Slots [GridY+1] [GridX].canBeMovedInto()==false){
							_currentDirection = null; 
						}
						break; 
					case RIGHT: 
						if (GridX==22 && GridY== 11){
							this.setLocation(0, 11*Constants.SLOT_SIZE);
						}
						else if (Slots [GridY] [GridX+1].canBeMovedInto()==false){
							_currentDirection = null; 
						}
						break; 
					case LEFT:
						if (GridX==0 && GridY == 11){
							this.setLocation(22*Constants.SLOT_SIZE, 11*Constants.SLOT_SIZE);
						}
						else if (Slots [GridY] [GridX-1].canBeMovedInto()==false){
							_currentDirection = null; 
						}
						break; 
			}
		}
			
	}
	
		
}
	

