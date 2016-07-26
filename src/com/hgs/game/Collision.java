package com.hgs.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Åö×²
 * @author Administrator
 *
 */
public interface Collision{
	
	Rectangle getRect();
	
	boolean checkCollision(Graphics2D g2d);
}
