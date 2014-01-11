package Pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

/*
 * This is the class in which the game takes place. 
 * It contains KeyListeners which serve to set PacMan's desired movement direction according to user input 
 * 
 */
public class GamePanel extends JPanel {
	
private MainPanel _myMainPanel;
private Board _myBoard;
private PacMan _myPacMan; 
private Ghost _pinkGhost;
private Ghost _redGhost;
private Ghost _orangeGhost; 
private Ghost _cyanGhost;
private Referee _myReferee; 
private Ghost [] GhostList;
private Boolean gameOver; 
private int counter;

	public GamePanel(MainPanel aMainPanel) {
		super (); 
		_myMainPanel = aMainPanel;
		_myBoard = new Board(this);
		_myPacMan = new PacMan (this,_myBoard.getSlotCollection());
		gameOver = false;
		counter = 0;
		
		GhostList = new Ghost [4];
		_pinkGhost = new Ghost(this, new Color (255,52,255), _myBoard); 
		_pinkGhost.setDeathLocation(new Point (11,11)); 
		GhostList [0] = _pinkGhost;
		_cyanGhost = new Ghost(this, new Color (0,255,255), _myBoard); 
		_cyanGhost.setDeathLocation(new Point (10,10));
		GhostList [1] = _cyanGhost;
		_orangeGhost = new Ghost(this, new Color (255,128,0), _myBoard);
		_orangeGhost.setDeathLocation(new Point (11,10));
		GhostList [2] = _orangeGhost;
		_redGhost = new Ghost(this, new Color (255,0,0), _myBoard); 
		_redGhost.setDeathLocation(new Point (12,10));
		GhostList [3] = _redGhost;
		_myReferee = new Referee(_myBoard, _myPacMan, this, GhostList, _myMainPanel); 
		
		
		Dimension Size = new Dimension(Constants.GAMEPANEL_SIZE, Constants.GAMEPANEL_SIZE);
	    this.setPreferredSize(Size);
	    this.setSize(Size);
	    this.setBackground(new Color (0,0,0));
		
		this.setFocusable(true);
		
		this.addKeyListener(new MoveListener ()); 
		MyTimer timer = new MyTimer (this);
		timer.start();
		
		this.repaint();
	}
	
	private class MoveListener implements java.awt.event.KeyListener {
	    
	     public void keyPressed(KeyEvent e) {
	           int keyPressed = e.getKeyCode();
	           if (keyPressed == KeyEvent.VK_LEFT) {
	        	   _myPacMan.setDesiredDirection (Direction.LEFT);
	           }
	           if (keyPressed == KeyEvent.VK_RIGHT) {
	        	   _myPacMan.setDesiredDirection (Direction.RIGHT);
	           }
	           if (keyPressed == KeyEvent.VK_UP) {
	        	   _myPacMan.setDesiredDirection (Direction.UP);
	           }
	           if (keyPressed == KeyEvent.VK_DOWN) {
	        	   _myPacMan.setDesiredDirection (Direction.DOWN);
	           } 
	       }
	     
	     public void keyReleased(KeyEvent e) {}
	     public void keyTyped (KeyEvent e) {}
	}
	
	
	public void paintComponent(Graphics g){ 
		super.paintComponent(g); 
		Graphics2D brush = (Graphics2D) g;
		_myBoard.paintMap(brush);
		for (Ghost gh: GhostList){
			gh.paint(brush);
		}
		_myPacMan.paint(brush);
	}
	/* 
	 * The following method is called by an instance of MyTimer, and delegates out various tasks.
	 * It will be called every tenth of a CONSTANTS.TIMESTEP 
	 * Since I've set up fluid graphics, certain things have changed with my game: 
	 *      1. PacMan can collide with ghosts when he's not at a location which fits nicely
	 *      	 into a slot of the 23 x23 grid. Thus, at every time he and the Ghosts move, we must check
	 *      	for collisions with the ghosts. It is no longer necessary to check twice (once after PacMan moves 
	 *      	and once after the ghosts move) because PacMan no longer makes jumps that are as wide or wider than 
	 *      	the Ghosts' size. ( Note: the way the current constants are set up, Pacman only moves 3 pixels at a time )
	 *      2. I've added a counter within this method, because there's no real point in resetting the ghosts TARGET mode goals 
	 *      	every 50 ms. Indeed, we only need to do this when the ghosts are conveniently lined up on a SLOT of the 23 x 23 grid. 
	 *      	Thus, setGhostGoals() is only called once every 10 times moveAndRepaint() is called; 
	 *      3. In theory, I should be able to do the same thing with the .checkForBoardCollisions() and .checkifLevelisDone() methods. 
	 *      	( ie. calling them only once every ten time moveAndRepaint() is called ) 
	 *      	HOWEVER - because of the fact that I'm using a Brown University server and not a supercomputer, my methods aren't being 
	 *      	executed instantaneously. The delay ( although not necessarily created within this method, but created elsewhere in my code)
	 *      	puts a lot of things out of Sync,  which means that if I call checkForBoardCollisions() and checkIfLevelIsDone()
	 *      	only once every TimeStep, certain pellets won't be eaten when they should be. ( most notably those in L -shaped corners ) 
	 *          Calling checkForBoardCollisions() and checkIfLevelIsDone() every time  moveAndRepaint() solves the issue nicely.
	 * 
	 */
	
	public void moveAndRepaint (){ 
		if (gameOver==false){
			
			if (counter==0){
				_myReferee.setGhostGoals();
			}
			_myReferee.checkForBoardCollisions();
			_myReferee.checkIfLevelIsDone();
			_myPacMan.move();
			_myReferee.checkForGhostCollisions();
			
			for (Ghost gh: GhostList){
				gh.move();
			}
			
			this.repaint();
			
			counter ++; 
			if (counter ==10){
				counter =0; 
			}
		}
	}
		
	public void setGameOver (boolean bool){
		gameOver = bool;
	}
	
	public void setGhostBehavior (GhostBehavior behavior){
		for (Ghost gh: GhostList){
			gh.setBehavior(behavior);
		}
	}
	
	public void resetCounter(){
		counter =0;
	}
	
}
