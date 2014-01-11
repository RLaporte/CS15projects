package Pacman;

import gfx.Rectangle;


import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;

/*
 * This is my Ghost class - it knows how to move, so it needs to know about the Board that it is in. (thus the reference to Board)
 * It also needs a reference to GamePanel because GamePanel graphically contains the Ghosts.
 */
public class Ghost extends Rectangle {

	private int targetedGoalX; 
	private int targetedGoalY; 
	private int scatterGoalX; 
	private int scatterGoalY; 	
	private Board _myBoard;
	private Direction _currentDirection;
	private Boolean canMove; 
	private Point DeathLocation; 
	private Color _originalColor;
	private GhostBehavior _behavior;
	private GhostBehavior _normalBehavior;
	private Boolean _switchingBehavior;
	private int _movementCounter;
	private int _penCounter;

	public Ghost(GamePanel container, Color col, Board aBoard) {
		super(container);
		this.setColor(col); 
		this.setSize(Constants.SLOT_SIZE, Constants.SLOT_SIZE); 
		this.setBorderWidth(0);
		this.setVisible(true);
		_originalColor = col; 
		_behavior = GhostBehavior.TARGETED;
		_normalBehavior = GhostBehavior.TARGETED;
		_switchingBehavior = false;
		_currentDirection = Direction.RIGHT;
		_penCounter = 0; 
		
		targetedGoalX = 10; 
		targetedGoalY = 10; 
		scatterGoalX = 10; 
		scatterGoalY = 10; 
		
		
		_myBoard = aBoard;

	}
	
	public int  getGridX (){
		return ((int)this.getX()/Constants.SLOT_SIZE);
	}
	
	public int  getGridY (){
		return ((int)this.getY()/Constants.SLOT_SIZE);
	}
	
	public Color getOriginalColor () { 
		return _originalColor; 
	}
	
	public void setTargetedGoal (int GridX, int GridY) {
		targetedGoalX = GridX; 
		targetedGoalY = GridY;
	}
	
	public void setScatterGoal (int GridX, int GridY) {
		scatterGoalX = GridX; 
		scatterGoalY = GridY;
	}
	
	public void setDeathLocation(Point pt){
		DeathLocation = pt; 
	}	
	
	public Point getDeathLocation(){
		return DeathLocation; 
	}
	
	private void findNewDirection() {
	
		if (canMove){
			
			
			
			if (_switchingBehavior){
				_currentDirection = _currentDirection.getOpposite();
				_switchingBehavior = false;
			}
			else {
				
				int currentGridX = this.getGridX();
				int currentGridY = this.getGridY();
				
			 Slot [] [] slotCollection = _myBoard.getSlotCollection();
			 
			 int GoalX = 0; 
			 int GoalY = 0;
			 
			 if (_behavior==GhostBehavior.TARGETED){
				 GoalX = targetedGoalX; 
				 GoalY = targetedGoalY; 
			 }
			 
			 if (_behavior==GhostBehavior.SCATTER){
				 GoalX = scatterGoalX; 
				 GoalY = scatterGoalY;
			 }
			 
			 switch (_behavior) {
		 
				case TARGETED: case SCATTER:
			
					LinkedList aList = new LinkedList();
					Direction [] [] directionArray = new  Direction [23] [23];
					double ClosestDistanceIndicator = 1000000000000.0; 
					Point ClosestPoint = null;  
				
					if (slotCollection [currentGridY+1][currentGridX].canBeMovedInto()
							&& _currentDirection != Direction.UP){
						aList.addLast(new Point(currentGridX,currentGridY+1)); 
						directionArray [currentGridY+1][currentGridX] = Direction.DOWN;
					}

			
					if (slotCollection [currentGridY-1][currentGridX].canBeMovedInto()
							&& _currentDirection != Direction.DOWN){
							aList.addLast(new Point(currentGridX,currentGridY-1)); 
							directionArray [currentGridY-1][currentGridX] = Direction.UP;
					}
		
					if (currentGridX == 0 && currentGridY == 11 && _currentDirection!= Direction.RIGHT){
						aList.addLast(new Point (22,11)); 
						directionArray [11][22] = Direction.LEFT;
					}
					else if (slotCollection [currentGridY][currentGridX-1].canBeMovedInto() &&_currentDirection!=Direction.RIGHT){
						aList.addLast(new Point(currentGridX-1,currentGridY));
						directionArray [currentGridY][currentGridX-1] = Direction.LEFT;
					}
			
					
					if (currentGridX == 22 && currentGridY == 11 && _currentDirection!=Direction.LEFT){
						aList.addLast(new Point (0,11)); 
						directionArray [11][0] = Direction.RIGHT;
					}
					else if (slotCollection [currentGridY][currentGridX+1].canBeMovedInto() && _currentDirection!=Direction.LEFT){
							aList.addLast(new Point(currentGridX+1,currentGridY));
							directionArray [currentGridY][currentGridX+1] = Direction.RIGHT;
					}
				
				
					
					while (aList.isEmpty()==false){
					
					
						Point CurrentPoint = (Point) aList.removeFirst();
						double CurrentDistanceIndicator = Math.pow(((double)GoalX)-CurrentPoint.getX(),2.0)+Math.pow(((double)GoalY)-CurrentPoint.getY(),2.0);
			
					
						if (CurrentDistanceIndicator < ClosestDistanceIndicator){
							ClosestDistanceIndicator = CurrentDistanceIndicator;
							ClosestPoint = CurrentPoint;
						
						}
				
						currentGridX =(int) CurrentPoint.getX();
						currentGridY =(int) CurrentPoint.getY();
						
						if (slotCollection [currentGridY+1][currentGridX].canBeMovedInto()
								&& directionArray [currentGridY+1][currentGridX] == null
								&& (currentGridY+1 == this.getGridY() && currentGridX == this.getGridX()) == false){
							aList.addLast(new Point(currentGridX,currentGridY+1)); 
							directionArray [currentGridY+1][currentGridX] = directionArray [currentGridY][currentGridX];
						}
					
						if (slotCollection [currentGridY-1][currentGridX].canBeMovedInto()
								&& directionArray [currentGridY-1][currentGridX] == null
								&& (currentGridY-1 == this.getGridY() && currentGridX == this.getGridX()) == false){
							aList.addLast(new Point(currentGridX,currentGridY-1)); 
							directionArray [currentGridY-1][currentGridX] = directionArray [currentGridY][currentGridX];
						}
					
						if (currentGridX == 0 && currentGridY == 11 && directionArray [11][22] == null){
							aList.addLast(new Point (22,11)); 
							directionArray [11][22] = directionArray [11][0];
						}
						else if ((currentGridX == 0 && currentGridY == 11) ==false){
							if (slotCollection [currentGridY][currentGridX-1].canBeMovedInto()
									&& directionArray [currentGridY][currentGridX-1] == null
									&& (currentGridY == this.getGridY() && currentGridX-1 == this.getGridX()) == false){
								aList.addLast(new Point(currentGridX-1,currentGridY));
								directionArray [currentGridY][currentGridX-1] = directionArray [currentGridY][currentGridX];
							}
						}
					
						if (currentGridX == 22 && currentGridY == 11 && directionArray [11][0] == null){
							aList.addLast(new Point (0,11)); 
							directionArray [11][0] = directionArray [11][22];
						}	
						else if ((currentGridX == 22 && currentGridY ==11) == false) {
							if (slotCollection [currentGridY][currentGridX+1].canBeMovedInto()
									&& directionArray [currentGridY][currentGridX+1] == null
									&& (currentGridY == this.getGridY() && currentGridX+1 == this.getGridX()) == false){
								aList.addLast(new Point(currentGridX+1,currentGridY));
								directionArray [currentGridY][currentGridX+1] = directionArray [currentGridY][currentGridX];
							}
						} 

					} 
					
					
				
					_currentDirection = directionArray[(int)ClosestPoint.getY()][(int)ClosestPoint.getX()];
					
					break; 
				
					
				 case SCARED: 
					
					if ((this.getGridX()==0 && this.getGridY() == 11)==false 
						&& (this.getGridX()==22 && this.getGridY() == 11)==false){
						 
						Direction _newDirection = _currentDirection.getDirection();
						boolean NewDirectionFound = false;
						
						do { 
							switch ((int) (Math.random()*4)){
								case 0: 
									if (slotCollection [currentGridY-1][currentGridX].canBeMovedInto()){
										_newDirection = Direction.UP;
										NewDirectionFound=true;
									}
									break; 
								case 1: 
									if (slotCollection [currentGridY+1][currentGridX].canBeMovedInto()){
										_newDirection = Direction.DOWN;
										NewDirectionFound=true;
									}
									break; 
								case 2: 
									if (slotCollection [currentGridY][currentGridX+1].canBeMovedInto()){
										_newDirection = Direction.RIGHT;
										NewDirectionFound=true;
									}
									break;  
								case 3: 
									if (slotCollection [currentGridY][currentGridX-1].canBeMovedInto()){
										_newDirection = Direction.LEFT;
										NewDirectionFound=true;
									}
									break; 
							}
						} while (_newDirection == _currentDirection.getOpposite() || NewDirectionFound == false);
		
						_currentDirection = _newDirection.getDirection(); 	
					}
				}  
			}
		}
	}				
		
	
	public void move () { 
		
		if (canMove){
			if (_movementCounter ==0){
				this.findNewDirection();
			}
			switch (_currentDirection){
				case UP: 
					this.setLocation(this.getX(),this.getY()-(Constants.SLOT_SIZE/10)); 
					break; 
				case DOWN: 
					this.setLocation(this.getX(),this.getY()+(Constants.SLOT_SIZE/10));
					break; 
				case LEFT:
					if (this.getX()==0 && this.getGridY() == 11){
						this.setLocation(22*Constants.SLOT_SIZE, 11*Constants.SLOT_SIZE);
						System.out.println ("hit");
					}	 
					this.setLocation(this.getX()-(Constants.SLOT_SIZE/10), this.getY()); 
					
					break; 
				case RIGHT: 
					if (this.getX()==22*Constants.SLOT_SIZE && this.getGridY()== 11){
						this.setLocation(0, 11*Constants.SLOT_SIZE);
					}	
					this.setLocation(this.getX()+(Constants.SLOT_SIZE/10), this.getY()); 	
					break; 
			}
			_movementCounter++;
			if(_movementCounter ==10 ){
				_movementCounter = 0;
			}
			
		}
	}
	
	public void setCanMove (Boolean bool){
		canMove = bool;
	}
	
	public void setBehavior (GhostBehavior behav){
		
		if (_behavior != GhostBehavior.SCARED){
			if (behav != _behavior){
				_switchingBehavior = true;
			}
			_behavior = behav;
		} 
		if (behav != GhostBehavior.SCARED){
			_normalBehavior = behav;
		}
	}
	
	/* I don't have a pen Class - In order to show that the Ghost is sent to the Pen, all I do is send the Ghost
	 * to a specific Location and stop it from moving. I use a timer, PenTimer, which decides when the Ghost should be released, 
	 * The point of _penCounter is for cases like: when PacMan dies and everyone goes back to their original positions, if a Ghost was already in the pen, 
	 * it would then be released when the first timer fires, and not when it should be released ( in this case, when the second timer fires )
	 * The counter keeps track of how many timers are PenTimers running for each ghost and avoids the issue
	 * 
	 */
	
	public void goForATimeOut (int milliseconds, int GridX, int GridY){ 
		_penCounter++;
		this.setLocation (GridX*Constants.SLOT_SIZE, GridY*Constants.SLOT_SIZE); 
		canMove = false; 
		PenTimer timer = new PenTimer (milliseconds, this);
		timer.start();
	}
	
	public void freeFromPen(){ 
		_penCounter--;
		if (_penCounter == 0 ){
			this.setLocation(11*Constants.SLOT_SIZE, 8*Constants.SLOT_SIZE);
			this.resetMovementCounter(); 
			this.setCanMove(true);
			this.setDirection(Direction.RIGHT);
		}
	}
	
	public void endScaredBehavior () {
		Slot [] [] slotCollection = _myBoard.getSlotCollection();
		if (slotCollection [this.getGridY()] [this.getGridX()].getType() != 5){
			_switchingBehavior = true; 
		}
		_behavior = _normalBehavior;
	}
	
	public void setDirection (Direction dir) { 
		_currentDirection = dir;
	}
	
	public void resetMovementCounter(){
		_movementCounter =0; 
	}

}
