package com.hgs.game;

/**
 * 速度
 * 
 * @author Administrator
 *
 */
public interface Speed {

	void setSpeed(int speed);// 直行速度

	void setSkewSpeed(int skewSpeed);// 斜行速度

	int getSpeed();

	int getSkewSpeed();
}
