package com.hgs.game.wall;

import java.awt.Graphics2D;
import java.awt.Image;

import com.hgs.game.Point;
import com.hgs.game.resource.R;
import com.hgs.game.util.Images;
/**
 * Ìú¿é
 * @author hgs
 *
 */
public class Iron extends BaseWall{
	private int iX;
	private int iY;
	private final int I_WIDTH = 40,I_HEIGHT= 40;
	public Iron() {
		super();
		setWallWidthAndHeight( I_WIDTH, I_HEIGHT);
		canPass(false);
	}

	public Iron(Point wLocation) {
		super(wLocation);
		setWallWidthAndHeight(I_WIDTH, I_HEIGHT);
		canPass(false);
	}

	@Override
	public void draw(Graphics2D g) {
		iX = getWallLocation().getX();
		iY = getWallLocation().getY();
		g.drawImage(Images.iron, iX, iY,I_WIDTH,I_HEIGHT,null);
	}
}
