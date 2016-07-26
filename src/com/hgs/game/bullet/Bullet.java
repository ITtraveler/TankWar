package com.hgs.game.bullet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.hgs.game.Collision;
import com.hgs.game.Location;
import com.hgs.game.Point;
import com.hgs.game.Speed;
import com.hgs.game.resource.Direction;
import com.hgs.game.resource.R;
import com.hgs.game.util.ControlStudio;
import com.hgs.game.util.Images;

public class Bullet implements Location, Speed, Collision {
	private int bulletSpeed = 10;
	private int bulletSkewSpeed = 8;
	private Point curLocation;
	// private Point initLocation = new Point(100, 100);// 初始位置
	private final int BULLET_WIDTH = 10;
	private final int BULLET_HEIGHT = 10;
	private Direction curDirection = Direction.U;
	private boolean isLive = true;// 存在性
	protected int power = 100;// 伤害值
	private Image bulletImg;
	{
		bulletImg = Images.getImage(R.Bullet.BULLET_0);
	}
	
	public Bullet() {
	}

	public Bullet(Point initLocation, Direction direction) {

		curLocation = initLocation;
		curDirection = direction;
	}

	public int getPower() {// 得到子弹伤害值
		return power;
	}

	public void setCurDirection(Direction curDirection) {
		this.curDirection = curDirection;
	}
	
	public Point getLocation(){
		return curLocation;
	}

	@Override
	public void setSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	@Override
	public void setSkewSpeed(int bulletSkewSpeed) {
		this.bulletSkewSpeed = bulletSkewSpeed;
	}

	@Override
	public int getSpeed() {
		return bulletSpeed;
	}

	@Override
	public int getSkewSpeed() {
		return bulletSkewSpeed;
	}

	public void initLocation(Point point) {
		// initLocation = point;
		// curLocation = point;
	}

	@Override
	public void changeLocation(Point point) {
		curLocation = point;
	}

	@Override
	public void changeLocationX(int x) {
		curLocation.setX(x);
	}

	@Override
	public void changeLocationY(int y) {
		curLocation.setY(y);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(curLocation.getX(), curLocation.getY(), 2, 2);
	}

	@Override
	public boolean checkCollision(Graphics2D g2d) {

		return false;
	}

	public void setLiveToFalse() {
		isLive = false;
	}

	public void draw(Graphics g) {
		int x = curLocation.getX();
		int y = curLocation.getY();
		g.setColor(Color.BLUE);
		// g.fillRect(x, y, BULLET_WIDTH, BULLET_HEIGHT);
		// g.drawString("fire", x, y);
		g.drawImage(bulletImg, x, y, BULLET_WIDTH, BULLET_HEIGHT, null);
		move();
		//chackEdge();
	}

	
//	public void chackEdge(){
//		int width = 895;//8
//		int height = 660;//6	
//		int x = curLocation.getX();
//		int y = curLocation.getY();
//		if(x < 0 || y < 0|| x > width || y > height){
//			ControlStudio.removeEnemyBullets(this);
//			ControlStudio.removeMyBullets(this);
//		}
//	}
//	
	/**
	 * 子弹移动处理
	 */
	public void move() {

		switch (curDirection) {
		case U:
			curLocation.setY(curLocation.getY() - bulletSpeed);
			break;
		case D:
			curLocation.setY(curLocation.getY() + bulletSpeed);
			break;
		case L:
			curLocation.setX(curLocation.getX() - bulletSpeed);
			break;
		case R:
			curLocation.setX(curLocation.getX() + bulletSpeed);
			break;
		case LU:
			curLocation.setX(curLocation.getX() - bulletSkewSpeed);
			curLocation.setY(curLocation.getY() - bulletSkewSpeed);
			break;
		case LD:
			curLocation.setX(curLocation.getX() - bulletSkewSpeed);
			curLocation.setY(curLocation.getY() + bulletSkewSpeed);
			break;
		case RU:
			curLocation.setX(curLocation.getX() + bulletSkewSpeed);
			curLocation.setY(curLocation.getY() - bulletSkewSpeed);
			break;
		case RD:
			curLocation.setX(curLocation.getX() + bulletSkewSpeed);
			curLocation.setY(curLocation.getY() + bulletSkewSpeed);
			break;
		default:
			break;
		}
	}

	/**
	 * 改變子彈外觀
	 */
	public void setBulletImage(Image image) {
		bulletImg = image;
	}
}
