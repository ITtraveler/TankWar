package com.hgs.game.wall;

import java.awt.Graphics2D;
import java.awt.Image;

import com.hgs.game.Point;
import com.hgs.game.resource.R;
import com.hgs.game.util.Images;
/**
 * 草地
 * @author hgs
 *
 */
public class Grassland extends BaseWall {
	private int gX;
	private int gY;
	private final int gWidth = 40,gHeight = 40;
	
	public Grassland() {
		canPass(true);// 设置可以通过
		setWallWidthAndHeight(gWidth, gWidth);
	}

	public Grassland(Point wLocation) {
		super(wLocation);
		canPass(true);// 设置可以通过
		setWallWidthAndHeight(gWidth, gWidth);
	}

	@Override
	public void draw(Graphics2D g) {
		gX = getWallLocation().getX();
		gY = getWallLocation().getY();
		g.drawImage(Images.grassland, gX, gY,gWidth,gHeight,null);
	}
}
