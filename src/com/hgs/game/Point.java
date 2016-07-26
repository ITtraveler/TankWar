package com.hgs.game;

/**
 * 坐标点类
 * 
 * @author Administrator
 *
 */
public class Point {
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	private int x;//x坐标
	private int y;//y坐标

	public Point() {

	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
