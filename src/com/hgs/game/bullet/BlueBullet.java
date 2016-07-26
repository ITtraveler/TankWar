package com.hgs.game.bullet;

import java.awt.Image;

import com.hgs.game.Point;
import com.hgs.game.resource.Direction;
import com.hgs.game.resource.R;
import com.hgs.game.util.Images;
/**
 * 蓝色子弹主要用于组装成BigBullet，不过速度相比Bullet要慢些
 * @author student
 *
 */
public class BlueBullet extends Bullet{
	private Image bulletImg;
	private int bulletSpeed = 8;
	private int bulletSkewSpeed = 7;
	{
		bulletImg = Images.getImage(R.Bullet.BULLET_1);
	}
	
	public BlueBullet(Point initLocation, Direction direction) {
		super(initLocation, direction);
		setBulletImage(bulletImg);
		setSkewSpeed(bulletSkewSpeed);
		setSpeed(bulletSpeed);
	}
}
