package com.hgs.game;

/**
 * ÑªÁ¿
 * @author Administrator
 *
 */
public interface Blood {
	
	int getBlood();
	
	void decreaseBlood(int hurtValue);
	
	void increaseBlood(int treatValue);
	
	boolean isLive();
	
	void setLive(boolean live);
}
