package com.hgs.game;

import java.awt.event.KeyEvent;
/**
 * ����
 * @author Administrator
 *
 */
public interface Control {
	
	void keyPressed(KeyEvent keyEvent);
	
	void keyReleased(KeyEvent keyEvent);
	
	void move();
}
