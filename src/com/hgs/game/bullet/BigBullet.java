package com.hgs.game.bullet;

import java.util.ArrayList;
import java.util.List;

import com.hgs.game.Point;
import com.hgs.game.resource.Direction;

/**
 * 多发子弹
 * 
 * @author student
 *
 */
public class BigBullet {
	private int bulletNum = 3;
	private Point curLocation;
	private final int BULLET_DIS = 10;
	private Direction curDirection;
	public static final int DEC_SCORE = 25;//每发射一个此子彈所需减少的分数
	private int x, y;

	public void setBulletNum(int bulletNum) {
		this.bulletNum = bulletNum;
	}

	public BigBullet(Point initLocation, Direction direction) {
		x = initLocation.getX();
		y = initLocation.getY();
		curDirection = direction;
		
	}

	/**
	 * x + 子弹
	 * @return
	 */
	public List<Bullet> fire() {
		List<Bullet> b = new ArrayList<Bullet>();
		for (int i = 0; i < bulletNum; i++) {
				b.add(new BlueBullet(new Point(x + i * BULLET_DIS, y + i * BULLET_DIS), curDirection));
				b.add(new BlueBullet(new Point(x - i * BULLET_DIS, y + i * BULLET_DIS), curDirection));
		}
		for (int i = 0; i < bulletNum; i++) {
			b.add(new BlueBullet(new Point(x + i * BULLET_DIS, y - i * BULLET_DIS), curDirection));
			b.add(new BlueBullet(new Point(x - i * BULLET_DIS, y - i * BULLET_DIS), curDirection));
	}
		return b;
	}
}
