package Pacman;

/*
 * Simple enum class for directions.
 */
public enum Direction {
	UP, DOWN, RIGHT, LEFT; 


	public Direction getOpposite() {
		switch(this) {
			case UP:
				return DOWN;
			case DOWN: 
				return UP;
			case RIGHT: 
				return LEFT; 
			case LEFT: 
				return RIGHT; 
			default: 
				return null;
		} 
	}	
	
	public Direction getDirection () {
		return this.getOpposite().getOpposite();
	}
	
	
}
