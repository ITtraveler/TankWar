package com.hgs.game.wall;

import java.awt.Graphics2D;
import java.awt.Image;

import com.hgs.game.Point;
import com.hgs.game.resource.R;
import com.hgs.game.util.Images;

/**
 * 砖墙，有生命值因此可以配子弹打掉
 * 
 * @author Administrator
 *
 */
public class BrickWall extends BaseWall {
	private int blood = 100;
	private final int width = 20, height = 20;
	private int bX;
	private int bY;
	
	public BrickWall() {
		setBlood(blood);
		setWallWidthAndHeight(width, height);
		setCanRemove(true);//可移除
	}

	public BrickWall(Point wLocation){
		setWallLocation(wLocation);
		setBlood(blood);
		setWallWidthAndHeight(width, height);
		setCanRemove(true);//可移除
	}
	@Override
	public void draw(Graphics2D g) {
		bX = getWallLocation().getX();
		bY = getWallLocation().getY();
		g.drawImage(Images.brickWall, bX, bY, width, height,
				null);
	}
	
}
