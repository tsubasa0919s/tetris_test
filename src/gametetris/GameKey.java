package gametetris;

import java.awt.event.*;

public class GameKey implements KeyListener {

	public boolean	UP;
	public boolean	DOWN;
	public boolean	LEFT;
	public boolean	RIGHT;
	public boolean	TURN_L;
	public boolean	TURN_R;
	public boolean	START;
		
	private GameTimer	timerDown;
	private GameTimer	timerLeft;
	private GameTimer	timerRight;
	
	GameKey(){
		timerDown	= new GameTimer();
		timerLeft	= new GameTimer();
		timerRight	= new GameTimer();
		timerDown.start();
		timerLeft.start();
		timerRight.start();
	}
	
	public boolean get_moveDown(){
		return DOWN;
	}
	
	public boolean get_moveUP(){
		return UP;
	}
	
	public boolean get_moveLeft(){
		return LEFT;
	}
	
	public boolean get_moveRight(){
		return RIGHT;
	}
	
	public boolean get_turnLeft(){
		return TURN_L;
	}
	
	public boolean get_turnRight(){
		return TURN_R;
	}
	
	public boolean get_Start(){
		return START;
	}
	

	public void keyTyped(KeyEvent e){
	}
	
	public void keyPressed(KeyEvent e){
		int key_code;
		key_code = e.getKeyCode();
		
		if(key_code == KeyEvent.VK_DOWN){
			if(!DOWN){
				DOWN = true;
				timerDown.timerStart();
			}
			return;
		}
		if(key_code == KeyEvent.VK_UP){
			UP = true;
			return;
		}
		if(key_code == KeyEvent.VK_LEFT){
			if(!LEFT){
				LEFT = true;
				timerLeft.timerStart();
			}
			return;
		}
		if(key_code == KeyEvent.VK_RIGHT){
			if(!RIGHT){
				RIGHT = true;
				timerRight.timerStart();
			}
			return;
		}
		if(key_code == KeyEvent.VK_Z){
			TURN_L = true;
			return;
		}
		if(key_code == KeyEvent.VK_X){
			TURN_R = true;
			return;
		}
		if(key_code == KeyEvent.VK_SPACE){
			START = true;
			return;
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key_code;
		key_code = e.getKeyCode();
		
		if(key_code == KeyEvent.VK_DOWN){
			DOWN = false;
			timerDown.timerReset();
			timerDown.timerStop();
			return;
		}
		if(key_code == KeyEvent.VK_UP){
			UP = false;
			return;
		}
		if(key_code == KeyEvent.VK_LEFT){
			LEFT = false;
			timerLeft.timerReset();
			timerLeft.timerStop();
			return;
		}
		if(key_code == KeyEvent.VK_RIGHT){
			RIGHT = false;
			timerRight.timerReset();
			timerRight.timerStop();
			return;
		}
		if(key_code == KeyEvent.VK_Z){
			TURN_L = false;
			return;
		}
		if(key_code == KeyEvent.VK_X){
			TURN_R = false;
			return;
		}
		if(key_code == KeyEvent.VK_SPACE){
			START = false;
			return;
		}
	}
}
