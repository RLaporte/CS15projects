package Pacman;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/* 
 * This Class is the top level object. 
 * It contains labels which show the score, as well as the number of lives. 
 * 
 */

public class MainPanel extends JPanel {

private JPanel InformationPanel;
private JLabel _ScoreLabel; 
private JLabel _LifeLabel; 
private int _score; 
private int _livesCounter;
private GamePanel _panel;

	public MainPanel() {
		super(new BorderLayout ());
		_panel = new GamePanel(this);
		this.add(_panel, BorderLayout.CENTER);
		
		_score = 0; 
		_livesCounter = 3;
		_ScoreLabel = new JLabel ("Your Score is " + _score); 
		_LifeLabel = new JLabel ("You have "+_livesCounter+" lives left");
		
		JPanel InfoPanel = new JPanel (new GridLayout (1,2));
		InfoPanel.add(_ScoreLabel, BorderLayout.WEST); 
		InfoPanel.add(_LifeLabel, BorderLayout.EAST);
		
		this.add(InfoPanel, BorderLayout.SOUTH);

	}
	
	public void incrementScore (int incrementation){
		_score = _score + incrementation; 
		_ScoreLabel.setText("Your score is " + _score);
	}
	
	public void decrementLivesAndCheckIfAnyAreLeft(){ 
		_livesCounter--; 
		_LifeLabel.setText("You have "+_livesCounter+" lives left");
		if (_livesCounter <= 0 ){
			_LifeLabel.setText ("GAME OVER"); 
			_panel.setGameOver(true);
		}
		
	}

}
