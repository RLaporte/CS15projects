package Pacman;

import java.awt.Color;

/*
 * This is the brains of the game - he manages almost everything ( hence the large number of references in his
 * constructor. It manages all the intersections ( for which I don't use a data structures / delegation - I prefer
 * having everything in one place). It manages score updating as well as checking is the game isn't over, or if a new 
 * level is needed, and it also updates the Ghost's Targeted mode targets. 
 */

public class Referee {
	
	private Board _myBoard; 
	private PacMan _myPacMan; 
	private Slot [] [] SlotCollection;
	private GamePanel _myGamePanel;
	private Ghost [] GhostList;
	private MainPanel _myMainPanel;
	private int numberOfEnergizerTimersRunning;
	 

	public Referee(Board aBoard, PacMan aMan, GamePanel aGamePanel, Ghost [] ghosts, MainPanel aMainPanel ) {
		_myBoard = aBoard; 
		_myPacMan = aMan;
		_myGamePanel = aGamePanel;
		_myMainPanel = aMainPanel;
		GhostList = ghosts;
		SlotCollection = _myBoard.getSlotCollection();
		numberOfEnergizerTimersRunning =0;
		
		GhostList[0].goForATimeOut(0,11,8);
		GhostList[1].goForATimeOut(2000, 10, 10);
		GhostList[2].goForATimeOut(6000, 11, 10);
		GhostList[3].goForATimeOut(8000, 12, 10);
		
		GhostList[0].setScatterGoal(0,0);
		GhostList[1].setScatterGoal(22,0); 
		GhostList[2].setScatterGoal(0,22);
		GhostList[3].setScatterGoal(22,22);
		
	}
	
	public void checkForBoardCollisions(){
		int PacManGridX = _myPacMan.getGridX(); 
		int PacManGridY = _myPacMan.getGridY();
		int TypeOfPacManSlot = SlotCollection [PacManGridY] [PacManGridX].getType(); 
		if (TypeOfPacManSlot == 2){
			SlotCollection [PacManGridY] [PacManGridX] = new Slot (1, _myGamePanel);
			_myMainPanel.incrementScore(10); 
		}
		if (TypeOfPacManSlot == 3){
			for (Ghost gh: GhostList){
					gh.setColor(new Color(153,204,255));
					gh.setBehavior(GhostBehavior.SCARED);
				}
			_myPacMan.setPowerStatus(true);
			numberOfEnergizerTimersRunning++;
			_myMainPanel.incrementScore(10);
			
			EnergizerTimer PowerTimer = new EnergizerTimer (this); 
			PowerTimer.start();
			SlotCollection [PacManGridY] [PacManGridX] = new Slot (1, _myGamePanel);
		}
				
	}
	
	public void checkForGhostCollisions(){
		for (Ghost gh: GhostList){
			if (gh.intersects(_myPacMan.getX(), _myPacMan.getY(), _myPacMan.getWidth(), _myPacMan.getHeight())){
				if (_myPacMan.getPowerStatus()){
					gh.goForATimeOut(3000,(int) gh.getDeathLocation().getX(), (int) gh.getDeathLocation().getY());
					gh.resetMovementCounter();
					gh.setDirection(Direction.RIGHT);
					_myMainPanel.incrementScore(200);
				}
				else {
					GhostList[0].goForATimeOut(0,11,8);
					GhostList[1].goForATimeOut(2000, 10, 10);
					GhostList[2].goForATimeOut(4000, 11, 10);
					GhostList[3].goForATimeOut(6000, 12, 10);
					
					_myPacMan.setLocation (11*Constants.SLOT_SIZE,17*Constants.SLOT_SIZE);
					_myMainPanel.decrementLivesAndCheckIfAnyAreLeft(); 
					
					_myPacMan.resetMovementCounter();
					for(Ghost gho: GhostList){
						gho.resetMovementCounter();
						gho.setDirection(Direction.RIGHT);
					}
					_myGamePanel.resetCounter();
				}
			}
		}
	}

	public void endEnergizerPower () {
		
		numberOfEnergizerTimersRunning--;
		if (numberOfEnergizerTimersRunning==0){
			_myPacMan.setPowerStatus(false);
			for (Ghost gh: GhostList){
				gh.setColor(gh.getOriginalColor());
				gh.endScaredBehavior();
			}
		}
	}
	
	public void setGhostGoals(){
		int manGridX = (int) _myPacMan.getGridX();
		int manGridY = (int) _myPacMan.getGridY();

		if (manGridX-1 > 0){
			GhostList[0].setTargetedGoal (manGridX-1,manGridY+1);
		}
		else {
			GhostList[0].setTargetedGoal (manGridX-1,manGridY);
		}
		GhostList[1].setTargetedGoal (manGridX,manGridY-1); 
		GhostList[2].setTargetedGoal (manGridX, manGridY); 
		if (manGridX+2 <= 22){
			GhostList[3].setTargetedGoal(manGridX+2, manGridY+1);
		}
		else {
			GhostList[3].setTargetedGoal(manGridX+1, manGridY+1);
		}
	}
	
	public void checkIfLevelIsDone(){
	    boolean levelIsDone = true; 
	    for (int i=0; i<23; i++){
	    	for (int j=0; j<23; j++){
	    		if (SlotCollection [i] [j].getType() == 2) {
	    			levelIsDone = false;
	    		}
	    	}
	    }
	    if (levelIsDone){
	    	GhostList[0].goForATimeOut(0,11,8);
			GhostList[1].goForATimeOut((int)(Constants.TIMESTEP*4), 10, 10);
			GhostList[2].goForATimeOut(Constants.TIMESTEP*6, 11, 10);
			GhostList[3].goForATimeOut(Constants.TIMESTEP*8, 12, 10);
			_myPacMan.setLocation (11*Constants.SLOT_SIZE,17*Constants.SLOT_SIZE);
			_myBoard.regenerateBoard();
			
			_myPacMan.resetMovementCounter();
			for(Ghost gh: GhostList){
				gh.resetMovementCounter();
				gh.setDirection(Direction.RIGHT);
			}
			_myGamePanel.resetCounter();
			this.endEnergizerPower(); 
	    }
	}
	
}
